package com.modest.redis.test;

import redis.clients.jedis.Jedis;

public class AliyuRedisTest {

	public static void main(String[] args) {
		try {
			String host = "192.168.2.223";// 控制台显示访问地址
			int port = 6379;
			Jedis jedis = new Jedis(host, port);
			// 鉴权信息
			jedis.auth("lmw123456");// password
			String key = "redis";
			String value = "aliyun-redis";
			// select db默认为0
			jedis.select(1);
			// set一个key
			jedis.set(key, value);
			System.out.println("Set Key " + key + " Value: " + value);
			// get 设置进去的key
			String getvalue = jedis.get(key);
			System.out.println("Get Key " + key + " ReturnValue: " + getvalue);
			jedis.quit();
			jedis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
