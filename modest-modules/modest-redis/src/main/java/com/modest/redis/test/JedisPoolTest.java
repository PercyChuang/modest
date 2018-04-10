package com.modest.redis.test;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolTest {

	public static void main(String[] args) {
		
		JedisPoolConfig config = new JedisPoolConfig();
		//最大空闲连接数, 应用自己评估，不要超过ApsaraDB for Redis每个实例最大的连接数
		config.setMaxIdle(200);
		//最大连接数, 应用自己评估，不要超过ApsaraDB for Redis每个实例最大的连接数
		config.setMaxTotal(300);
		config.setTestOnBorrow(false);
		config.setTestOnReturn(false);
		String host = "192.168.2.223";
		String password = "lmw123456";
		JedisPool pool = new JedisPool(config, host, 6379, 3000, password);
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.auth("lmw123456");
			/// ... do stuff here ... for example
			jedis.set("foo", "bar");
			String foobar = jedis.get("foo");
			System.out.println(foobar);
			jedis.zadd("sose", 0, "car");
			jedis.zadd("sose", 0, "bike");
			Set<String> sose = jedis.zrange("sose", 0, -1);
			System.out.println(sose);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		/// ... when closing your application:
		pool.destroy();
	}
}
