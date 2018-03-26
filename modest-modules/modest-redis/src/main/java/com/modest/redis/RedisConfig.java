package com.modest.redis;

import org.apache.commons.lang3.StringUtils;

/**
 * @author ZHUANGPUXIANG
 */
public class RedisConfig {
    private String redisIp;
    private int redisPort;
    private int maxActive;
    private int maxIdle;
    private long maxWait;
    private int maxPoolSize;
    
    private String password;
    private String instanceId;
    
    public String getPassword() {
    	if (StringUtils.isBlank(password)) {
    		password = null;
        }
        if(!StringUtils.isBlank(instanceId) && null != password){
        	password = instanceId+":"+password;
        }
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getRedisIp() {
        return redisIp;
    }
    public void setRedisIp(String redisIp) {
        this.redisIp = redisIp;
    }
    public int getRedisPort() {
        return redisPort;
    }
    public void setRedisPort(int redisPort) {
        this.redisPort = redisPort;
    }
    public int getMaxActive() {
        return maxActive;
    }
    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }
    public int getMaxIdle() {
        return maxIdle;
    }
    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }
    public long getMaxWait() {
        return maxWait;
    }
    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }
    public int getMaxPoolSize() {
        return maxPoolSize;
    }
    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }
   
}
