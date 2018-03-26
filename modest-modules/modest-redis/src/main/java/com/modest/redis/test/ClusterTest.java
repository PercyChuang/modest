package com.modest.redis.test;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.JedisCluster;

import com.modest.redis.cluster.RedisClusterUtil;
import com.modest.redis.factory.JedisClusterFactory;

public class ClusterTest {

	public static void main(String[] args) {
		String ips = "192.168.2.226:7000,192.168.2.226:7001,192.168.2.226:7002,192.168.2.227:7003,192.168.2.227:7004,192.168.2.227:7005";
		
		/*private Integer timeout;
		private Integer maxRedirections;
		private GenericObjectPoolConfig genericObjectPoolConfig;
		
		private String addressList;*/
		JedisClusterFactory clusterFactory = new JedisClusterFactory();
		clusterFactory.setAddressList(ips);
		clusterFactory.setTimeout(600);
		clusterFactory.setMaxRedirections(3000);
		
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		/*<property name="maxWaitMillis" value="-1" />
		<property name="maxTotal" value="5000" />
		<property name="minIdle" value="8" />
		<property name="maxIdle" value="100" />*/
		config.setMaxWaitMillis(-1);
		config.setMaxTotal(5000);
		config.setMinIdle(8);
		config.setMaxIdle(100);
		clusterFactory.setGenericObjectPoolConfig(config);
		//所有变量注入过执行
		try {
			clusterFactory.afterPropertiesSet();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		RedisClusterUtil clusterUtil = new RedisClusterUtil();
		try {
			JedisCluster jedisCluster = clusterFactory.getObject();
			clusterUtil.setJedisCluster(jedisCluster);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		clusterUtil.set("hahaha", "hahaha");
		
		System.out.println(clusterUtil.getString("hahaha"));
	}
}
