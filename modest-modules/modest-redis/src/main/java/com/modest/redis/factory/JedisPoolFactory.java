package com.modest.redis.factory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import com.modest.redis.RedisConfig;

/**
 * @author ZHUANGPUXIANG
 */
public class JedisPoolFactory {

	private Logger logger = LoggerFactory.getLogger(JedisPoolFactory.class);
	
	private RedisConfig redisConfig;

	private static JedisPool pool;

	public synchronized void init() {
		if (pool != null) {
			return;
		}
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(redisConfig.getMaxIdle());
		config.setMaxWaitMillis(redisConfig.getMaxWait());
		config.setMaxTotal(redisConfig.getMaxPoolSize());
		config.setTestOnBorrow(true);
		String passWord = redisConfig.getPassword();
		pool = new JedisPool(config, redisConfig.getRedisIp(),
				redisConfig.getRedisPort(), Protocol.DEFAULT_TIMEOUT, passWord);
		Thread a = new Thread(new Daemon());
		a.start();
	}
	
	private class Daemon implements Runnable{
		@Override
		public void run() {
			monitor();
		}
		private synchronized void monitor(){
			while(true) {
				try {
					Thread.sleep(10000l);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Jedis jedis = null;
				try {
					jedis = getJedis();
					if (null == jedis) {
						logger.error("connection denied or the connection pool is full!");
					}
					jedis.set("monitor", "jedis monitor");
					logger.info(jedis.get("monitor") + ":jedis pool runnning ok... ...");
				} catch (Exception e) {
					logger.error("error:",e);
				}finally {
					returnResource(jedis);
					logger.info("jedis monitor:jedis retrun resource ok... ... ");
				}
			}
		}
	}
	
	
	/**
	 * 
	 * 获得Jedis
	 * 
	 * @return
	 *
	 */
	public Jedis getJedis() {
		Jedis jedis = pool.getResource();
		if (StringUtils.isNotBlank(redisConfig.getPassword())) {
			jedis.auth(redisConfig.getPassword());
		}
		return jedis;
	}

	/**
	 * 返还到连接池
	 * 
	 * @param pool
	 * @param redis
	 */
	public void returnResource(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

	public RedisConfig getRedisConfig() {
		return redisConfig;
	}

	public void setRedisConfig(RedisConfig redisConfig) {
		this.redisConfig = redisConfig;
	}

}
