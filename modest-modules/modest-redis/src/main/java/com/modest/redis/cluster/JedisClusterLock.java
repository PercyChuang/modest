package com.modest.redis.cluster;

import java.util.UUID;

import redis.clients.jedis.JedisCluster;

/**
		JedisClusterLock lock = null; // 分布式锁
		int expireMsecs = 60 * 1000; // 锁超时时间，防止线程在入锁以后，无限的执行等待(毫秒)
		int timeoutMsecs = 1000; // 锁等待时间，防止线程饥饿(毫秒)
		
		String businessType = "zyb_full_scale";
		String lockName = fullScaleMessage.getBorrowId()  + businessType;
		lock = new JedisClusterLock(redisUtil.getPropertyJedis(), lockName, // 锁名称
				timeoutMsecs, //
				expireMsecs //
		);
		if (!lock.acquire()) {
			logger.info("当前请求的满标业务正在处理!borrowId:" + fullScaleMessage.getBorrowId() + ",业务类型:" + businessType);
			return ;
		}

 */
public class JedisClusterLock {

    private static final Lock NO_LOCK = new Lock(new UUID(0l,0l), 0l);
    
    private static final int ONE_SECOND = 1000;

    public static final int DEFAULT_EXPIRY_TIME_MILLIS = Integer.getInteger("com.github.jedis.lock.expiry.millis", 60 * ONE_SECOND);
    public static final int DEFAULT_ACQUIRE_TIMEOUT_MILLIS = Integer.getInteger("com.github.jedis.lock.acquiry.millis", 10 * ONE_SECOND);
    public static final int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = Integer.getInteger("com.github.jedis.lock.acquiry.resolution.millis", 100);

    private final JedisCluster jedisCluster;

    private final String lockKeyPath;

    private final int lockExpiryInMillis;
    private final int acquiryTimeoutInMillis;
    private final UUID lockUUID;

    private Lock lock = null;

    protected static class Lock {
        private UUID uuid;
        private long expiryTime;

        protected Lock(UUID uuid, long expiryTimeInMillis) {
            this.uuid = uuid;
            this.expiryTime = expiryTimeInMillis;
        }
        
        protected static Lock fromString(String text) {
            try {
                String[] parts = text.split(":");
                UUID theUUID = UUID.fromString(parts[0]);
                long theTime = Long.parseLong(parts[1]);
                return new Lock(theUUID, theTime);
            } catch (Exception any) {
                return NO_LOCK;
            }
        }
        
        public UUID getUUID() {
            return uuid;
        }

        public long getExpiryTime() {
            return expiryTime;
        }
        
        @Override
        public String toString() {
            return uuid.toString()+":"+expiryTime;  
        }

        boolean isExpired() {
            return getExpiryTime() < System.currentTimeMillis();
        }

        boolean isExpiredOrMine(UUID otherUUID) {
            return this.isExpired() || this.getUUID().equals(otherUUID);
        }
    }
    
    
    /**
     * Detailed constructor with default acquire timeout 10000 msecs and lock
     * expiration of 60000 msecs.
     * 
     * @param jedis
     * @param lockKey
     *            lock key (ex. account:1, ...)
     */
    public JedisClusterLock(JedisCluster jedisCluster, String lockKey) {
        this(jedisCluster, lockKey, DEFAULT_ACQUIRE_TIMEOUT_MILLIS, DEFAULT_EXPIRY_TIME_MILLIS);
    }

    /**
     * Detailed constructor with default lock expiration of 60000 msecs.
     * 
     * @param jedis
     * @param lockKey
     *            lock key (ex. account:1, ...)
     * @param acquireTimeoutMillis
     *            acquire timeout in miliseconds (default: 10000 msecs)
     */
    public JedisClusterLock(JedisCluster jedisCluster, String lockKey, int acquireTimeoutMillis) {
        this(jedisCluster, lockKey, acquireTimeoutMillis, DEFAULT_EXPIRY_TIME_MILLIS);
    }

    /**
     * Detailed constructor.
     * 
     * @param jedis
     * @param lockKey
     *            lock key (ex. account:1, ...)
     * @param acquireTimeoutMillis
     *            acquire timeout in miliseconds (default: 10000 msecs)
     * @param expiryTimeMillis
     *            lock expiration in miliseconds (default: 60000 msecs)
     */
    public JedisClusterLock(JedisCluster jedisCluster, String lockKey, int acquireTimeoutMillis, int expiryTimeMillis) {
        this(jedisCluster, lockKey, acquireTimeoutMillis, expiryTimeMillis, UUID.randomUUID());
    }

    /**
     * Detailed constructor.
     * 
     * @param jedis
     * @param lockKey
     *            lock key (ex. account:1, ...)
     * @param acquireTimeoutMillis
     *            acquire timeout in miliseconds (default: 10000 msecs)
     * @param expiryTimeMillis
     *            lock expiration in miliseconds (default: 60000 msecs)
     * @param uuid
     *            unique identification of this lock
     */
    public JedisClusterLock(JedisCluster jedisCluster, String lockKey, int acquireTimeoutMillis, int expiryTimeMillis, UUID uuid) {
        this.jedisCluster = jedisCluster;
        this.lockKeyPath = lockKey;
        this.acquiryTimeoutInMillis = acquireTimeoutMillis;
        this.lockExpiryInMillis = expiryTimeMillis+1;
        this.lockUUID = uuid;;
    }
    
    /**
     * @return lock uuid
     */
    public UUID getLockUUID() {
        return lockUUID;
    }

    /**
     * @return lock key path
     */
    public String getLockKeyPath() {
        return lockKeyPath;
    }

    /**
     * Acquire lock.
     * 
     * @return true if lock is acquired, false acquire timeouted
     * @throws InterruptedException
     *             in case of thread interruption
     */
    public synchronized boolean acquire() throws InterruptedException {
        return acquire(jedisCluster);
    }

    /**
     * Acquire lock.
     * 
     * @param jedis
     * @return true if lock is acquired, false acquire timeouted
     * @throws InterruptedException
     *             in case of thread interruption
     */
    protected synchronized boolean acquire(JedisCluster jedisCluster) throws InterruptedException {
        int timeout = acquiryTimeoutInMillis;
        while (timeout >= 0) {

            final Lock newLock = asLock(System.currentTimeMillis() + lockExpiryInMillis);
            if (jedisCluster.setnx(lockKeyPath, newLock.toString()) == 1) {
                this.lock = newLock;
                return true;
            }

            final String currentValueStr = jedisCluster.get(lockKeyPath);
            final Lock currentLock = Lock.fromString(currentValueStr);
            if (currentLock.isExpiredOrMine(lockUUID)) {
                String oldValueStr = jedisCluster.getSet(lockKeyPath, newLock.toString());
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                    this.lock = newLock;
                    return true;
                }
            }

            timeout -= DEFAULT_ACQUIRY_RESOLUTION_MILLIS;
            Thread.sleep(DEFAULT_ACQUIRY_RESOLUTION_MILLIS);
        }

        return false;
    }

    /**
     * Renew lock.
     * 
     * @return true if lock is acquired, false otherwise
     * @throws InterruptedException
     *             in case of thread interruption
     */
    public boolean renew() throws InterruptedException {
        final Lock lock = Lock.fromString(jedisCluster.get(lockKeyPath));
        if (!lock.isExpiredOrMine(lockUUID)) {
            return false;
        }

        return acquire(jedisCluster);
    }

    /**
     * Acquired lock release.
     */
    public synchronized void release() {
        release(jedisCluster);
    }

    /**
     * Acquired lock release.
     * @param jedis
     */
    protected synchronized void release(JedisCluster jedisCluster) {
        if (isLocked()) {
            jedisCluster.del(lockKeyPath);
            this.lock = null;
        }
    }

    /**
     * Check if owns the lock
     * @return  true if lock owned
     */
    public synchronized boolean isLocked() {
        return this.lock != null;
    }
    
    /**
     * Returns the expiry time of this lock
     * @return  the expiry time in millis (or null if not locked)
     */
    public synchronized long getLockExpiryTimeInMillis() {
        return this.lock.getExpiryTime();
    }
    
    
    private Lock asLock(long expires) {
        return new Lock(lockUUID, expires);
    }


}
