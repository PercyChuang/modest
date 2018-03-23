package com.modest.redis.factory;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class JedisClusterFactory implements FactoryBean<JedisCluster>, InitializingBean {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private JedisCluster jedisCluster;
	private Integer timeout;
	private Integer maxRedirections;
	private GenericObjectPoolConfig genericObjectPoolConfig;
	
	private String addressList;
	
	public String getAddressList() {
		return addressList;
	}

	public void setAddressList(String addressList) {
		this.addressList = addressList;
	}
	@Override
	public JedisCluster getObject() throws Exception {
		return jedisCluster;
	}

	@Override
	public Class<? extends JedisCluster> getObjectType() {
		return (this.jedisCluster != null ? this.jedisCluster.getClass() : JedisCluster.class);
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	private Set<HostAndPort> parseHostAndPort() throws Exception {
		try {
			Set<HostAndPort> haps = new HashSet<HostAndPort>();
			if(StringUtils.isNotEmpty(addressList)){
				String[] hostAndPorts =  addressList.split(",");
				HostAndPort hap = null;
				if(hostAndPorts.length >= 0){
					for(String hostAndPort : hostAndPorts){
						if(StringUtils.isNotEmpty(hostAndPort)){
							String[] hostPorts = hostAndPort.split(":");
							if(hostPorts.length <= 0){
								logger.error("reids config is null error ");
								continue;
							}
							hap = new HostAndPort(hostPorts[0], Integer.parseInt(hostPorts[1]));
							haps.add(hap);
						}else{
							logger.error("reids config is null error ");
							continue;
						}
					}
				}else{
					logger.error("redis config error : addressList 配置格式错误，{ ip:port , ip:port }");
					throw new Exception("redis config error : addressList 配置格式错误，{ ip:port , ip:port }");
				}
			}else{
				logger.error("redis config error : addressList is null");
				throw new Exception("redis.properties 配置文件addressList不能为空");
			}
			return haps;
		} catch (IllegalArgumentException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("解析 jedis 配置文件失败",ex);
			throw new Exception("解析 jedis 配置文件失败", ex);
		}
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
		jedisCluster = new JedisCluster(this.parseHostAndPort(), timeout, maxRedirections,genericObjectPoolConfig);
		
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setMaxRedirections(int maxRedirections) {
		this.maxRedirections = maxRedirections;
	}

	public void setGenericObjectPoolConfig(GenericObjectPoolConfig genericObjectPoolConfig) {
		this.genericObjectPoolConfig = genericObjectPoolConfig;
	}
	
}
