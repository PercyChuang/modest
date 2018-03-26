package com.modest.redis.factory;


import org.apache.commons.lang3.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import com.modest.redis.RedisConfig;

/**
 * @author ZHUANGPUXIANG
 */
public class JedisPoolFactory {
	
    private RedisConfig redisConfig;
    
    private static JedisPool pool;
    
    public void init() {
        if(pool != null){
            return;
        }
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(redisConfig.getMaxIdle());
        config.setMaxWaitMillis(redisConfig.getMaxWait());
        config.setMaxTotal(redisConfig.getMaxPoolSize());
        config.setTestOnBorrow(true);
        String passWord=redisConfig.getPassword();
		pool = new JedisPool(
				config, redisConfig.getRedisIp(),
				redisConfig.getRedisPort(),
				Protocol.DEFAULT_TIMEOUT,
				passWord
				);
    }
    
    /**
     * 
     * 获得Jedis
     * @return
     *
     */
    public Jedis getJedis(){
    	Jedis jedis = pool.getResource();
    	if(StringUtils.isNotBlank(redisConfig.getPassword()))
		{
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
           // pool.returnResourceObject(jedis);
        }  
    }

	public RedisConfig getRedisConfig() {
		return redisConfig;
	}

	public void setRedisConfig(RedisConfig redisConfig) {
		this.redisConfig = redisConfig;
	} 
    
}
