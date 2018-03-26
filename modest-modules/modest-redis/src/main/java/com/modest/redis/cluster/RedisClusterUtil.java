package com.modest.redis.cluster;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import com.modest.core.util.SerializeUtil;

/**
 <bean id="redisUtil" class="com.modest.redis.cluster.RedisClusterUtil" >
 	<property name="jedisCluster" ref="jedisCluster" />
 </bean>
 */
public class RedisClusterUtil {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private JedisCluster jedisCluster; 
	
	private static final int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = 100;
	
	/**
     * 锁超时时间，防止线程在入锁以后，无限的执行等待
     */
    private int expireMsecs = 60 * 1000;
	/**
     * 锁等待时间，防止线程饥饿
     */
    private int timeoutMsecs = 10 * 1000;
    
    private boolean locked = false;
    /**
     * Lock key path.
     */
    private String lockKey;
	//默认过期时间
	private int expire = 60;
	
	private int loginExpire = 30 * 60;

	public JedisCluster getJedisCluster() {
		return jedisCluster;
	}


	public void setJedisCluster(JedisCluster jedisCluster) {
		this.jedisCluster = jedisCluster;
	}


	public Long setnx(String key, String value) {
		try {
			if(StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
				return jedisCluster.setnx(key, value);
			}
		} catch (Exception e) {
			logger.error("Redis is Exception ", e);
		}
    	return 0l;
	}
	/**
     * Detailed constructor.
     *
     */
    public void setLockPropertis(String lockKey, int timeoutMsecs, int expireMsecs) {
        this.lockKey = lockKey + "_lock";
        this.timeoutMsecs = timeoutMsecs;
        this.expireMsecs = expireMsecs;
    }
    
    /**
     * 获得 lock.
     * 实现思路: 主要是使用了redis 的setnx命令,缓存了锁.
     * reids缓存的key是锁的key,所有的共享, value是锁的到期时间(注意:这里把过期时间放在value了,没有时间上设置其超时时间)
     * 执行过程:
     * 1.通过setnx尝试设置某个key的值,成功(当前没有这个锁)则返回,成功获得锁
     * 2.锁已经存在则获取锁的到期时间,和当前时间比较,超时的话,则设置新的值
     *
     * @return true if lock is acquired, false acquire timeouted
     * @throws InterruptedException in case of thread interruption
     */
    public synchronized boolean acquireLock() {
        int timeout = timeoutMsecs;
        while (timeout >= 0) {
            long expires = System.currentTimeMillis() + expireMsecs + 1;//锁到期时间
            long acquired = jedisCluster.setnx(lockKey, String.valueOf(expires));
            if (acquired == 1) {
                // lock acquired
                locked = true;
                return true;
            }

            String currentValueStr = this.getString(lockKey); //redis里的时间
            if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
                //判断是否为空，不为空的情况下，如果被其他线程设置了值，则第二个条件判断是过不去的
                // lock is expired

                String oldValueStr = jedisCluster.getSet(lockKey, String.valueOf(expires));
                //获取上一个锁到期时间，并设置现在的锁到期时间，
                //只有一个线程才能获取上一个线上的设置时间，因为jedis.getSet是同步的
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                    //防止误删（覆盖，因为key是相同的）了他人的锁——这里达不到效果，这里值会被覆盖，但是因为什么相差了很少的时间，所以可以接受
                    //[分布式的情况下]:如过这个时候，多个线程恰好都到了这里，但是只有一个线程的设置值和当前值相同，他才有权利获取锁
                    // lock acquired
                    locked = true;
                    return true;
                }
            }
            timeout -= DEFAULT_ACQUIRY_RESOLUTION_MILLIS;
            /*
		                延迟100 毫秒,  这里使用随机时间可能会好一点,可以防止饥饿进程的出现,即,当同时到达多个进程,
		                只会有一个进程获得锁,其他的都用同样的频率进行尝试,后面有来了一些进行,也以同样的频率申请锁,这将可能导致前面来的锁得不到满足.
		                使用随机的等待时间可以一定程度上保证公平性
             */
            try {
            	Thread.sleep(DEFAULT_ACQUIRY_RESOLUTION_MILLIS);
	          } catch (InterruptedException ie) {
	              Thread.currentThread().interrupt();
	              return false;
	          }

        }
        return false;
    }


    /**
     * Acqurired lock release.
     */
    public synchronized void unlock() {
        if (locked) {
        	jedisCluster.del(lockKey);
            locked = false;
        }
    }
    
    /**
     * @return lock key
     */
    public String getLockKey() {
        return lockKey;
    }
    
	 
	//释放锁
	public void releaseLock(String lock) {
	    long current = System.currentTimeMillis();       
	    // 避免删除非自己获取得到的锁
	    if (current < Long.valueOf(jedisCluster.get(lock))){
	    	jedisCluster.del(lock); 
	    }
	}
	
    /**
     * 设置过期时间
     */
    public  void expire(String key, int seconds) {
        if (seconds <= 0) {
            return;
        }
        jedisCluster.expire(key, seconds);
    }

    /**
     * 设置默认过期时间
     */
    public  void expire(String key) {
        expire(key, expire);
    }

    public  boolean set(String key,String value){
    	try {
			if(StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value) ){
				jedisCluster.set(key, value);
				jedisCluster.expire(key, loginExpire);
				return true;
			}
		} catch (Exception e) {
			logger.error("Redis is Exception ", e);
		}
    	return false;
    }
    
    public boolean set(String key,String  value,int seconds){
    	try {
			if(StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value) &&  seconds > 0){
				jedisCluster.setex(key, seconds, value);
				return true;
			}
		} catch (Exception e) {
			logger.error("Redis is Exception ", e);
		}
    	return false;
    	
    }
    
    public boolean setObject(String key,Object value){
    	try {
			if(StringUtils.isNotEmpty(key) && value != null){
				jedisCluster.setex(key.getBytes(), loginExpire, SerializeUtil.serialize(value));
				return true;
			}
		} catch (Exception e) {
			logger.error("Redis is Exception ", e);
		}
    	return false;
    }
    
    public boolean setObject(String key,Object value,int survivalTime){
    	try {
			if(StringUtils.isNotEmpty(key) && value != null && survivalTime > 0){
				jedisCluster.setex(key.getBytes(), survivalTime == 0 ? loginExpire:survivalTime, SerializeUtil.serialize(value));
				return true;
			}
		} catch (Exception e) {
			logger.error("Redis is Exception ", e);
		}
    	return false;
    }

    public  boolean set(String key,int value){
    	try {
			if(StringUtils.isNotEmpty(key)){
				set(key, String.valueOf(value));
				return true;
			}
		} catch (Exception e) {
			logger.error("Redis is Exception ", e);
		}
    	return false;
    }

    public  boolean set(String key,long value){
    	try {
			if(StringUtils.isNotEmpty(key)){
				set(key, String.valueOf(value));
				return true;
			}
		} catch (Exception e) {
			logger.error("Redis is Exception ", e);
		}
        return false;
    }

    public  boolean set(String key,float value){
    	try {
			if(StringUtils.isNotEmpty(key)){
				set(key, String.valueOf(value));
				return true;
			}
		} catch (Exception e) {
			logger.error("Redis is Exception ", e);
		}
        return false;
    }

    public  Integer incr(String key){
    	try {
			if(StringUtils.isNotEmpty(key)){
				Long val = jedisCluster.incr(key);
				jedisCluster.expire(key, loginExpire);
				if(val != null){
					return Integer.valueOf(val.toString());
				}
			}
		} catch (Exception e) {
			logger.error("Redis is Exception ", e);
		}
        return null;
       
    }
    
    public  boolean set(String key,double value){
    	try {
			if(StringUtils.isNotEmpty(key)){
				set(key, String.valueOf(value));
				return true;
			}
		} catch (Exception e) {
			logger.error("Redis is Exception ", e);
		}
        return false;
       
    }

    public  Float getFloat(String key){
    	try {
			if(StringUtils.isNotEmpty(key)){
				String val = getString(key);
				if(val != null){
					return Float.valueOf(val);
				}
			}
		} catch (Exception e) {
			logger.error("Redis is Exception ", e);
		}
        return null;
    }

    public  Double getDouble(String key){
    	try {
			if(StringUtils.isNotEmpty(key)){
				String val = getString(key);
				if(val != null){
					return Double.valueOf(val);
				}
			}
		} catch (Exception e) {
			logger.error("Redis is Exception ", e);
		}
        return null;
    }

    public  Long getLong(String key){
    	try {
			if(StringUtils.isNotEmpty(key)){
				String val = getString(key);
				if(val != null){
					return Long.valueOf(val);
				}
			}
		} catch (Exception e) {
			logger.error("Redis is Exception ", e);
		}
        return null;
    }

    public  Integer getInt(String key){
    	try {
			if(StringUtils.isNotEmpty(key)){
				String val = getString(key);
				if(val != null){
					return Integer.valueOf(val);
		        } 
			}
		} catch (Exception e) {
			logger.error("Redis is Exception ", e);
		}
      return null;
    }
    
    public  String getString(String key){
    	try {
			if(StringUtils.isNotEmpty(key)){
		        return jedisCluster.get(key);
			}
		} catch (Exception e) {
			logger.error("Redis is Exception ", e);
		}
       return null;
    }
    
    public void lpush(String key,Object obj){
    	try {
    		jedisCluster.lpush(key.getBytes(), SerializeUtil.serialize(obj));
		} catch (Exception e) {
			logger.error("Redis is Exception ", e);
		}
    }
    
    public List<Object> lrange(String key,int start,int end){
    	try {
    		List<byte[]> bytes=jedisCluster.lrange(key.getBytes(), start, end);
    		List<Object> objects=new ArrayList<Object>();
    		if(null!=bytes && !bytes.isEmpty()){
    			for (byte[] bits : bytes) {
    				objects.add( SerializeUtil.unserialize(bits));
				}
    		}
    		return objects;
		} catch (Exception e) {
			logger.error("Redis is Exception ", e);
		}
    	return null;
    }

    public  Object getObject(String key){
    	try {
			if(StringUtils.isNotEmpty(key)){
				 byte[] bits = jedisCluster.get(key.getBytes());
				 System.out.println("bits:" + bits);
			        if(bits !=null){
			            return  SerializeUtil.unserialize(bits);
			        }
			}
		} catch (Exception e) {
			logger.error("Redis is Exception ", e);
		}
       return null;
    }
    
	public TreeSet<String> keys(String pattern){
		try {
	      TreeSet<String> keys = new TreeSet<String>();
	      Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
	      for(String k : clusterNodes.keySet()){
	          JedisPool jp = clusterNodes.get(k);
	          Jedis connection = jp.getResource();
	          try{
	        	 Set<String> tempKeys = connection.keys(pattern);
	        	 if(tempKeys != null && tempKeys.size() > 0){
	        		 keys.addAll(tempKeys);
	        	 }
	          }catch(Exception e){
	        	  logger.error("Redis is Exception ", e);
	          }finally{
	        	  connection.close();
	          }
	      }
	      return keys;
		}catch (Exception e) {
			logger.error("Redis is Exception ", e);
		}
		return null;
	}
	
	public void del(String key){
		try {
			jedisCluster.del(key); 
		}catch (Exception e) {
			logger.error("Redis is Exception ", e);
		}	
	}
	
	public boolean setnx(String key,String  value,int seconds){
		try {
			Long result=setnx(key, value);
			if(result!=null&& result.intValue()==1){
				jedisCluster.expire(key, seconds);
				return true;
			}
		}catch (Exception e) {
			logger.error("Redis is Exception ", e);
		}
		return false;
	}
}
