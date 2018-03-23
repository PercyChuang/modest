package com.demo.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.modest.redis.cluster.RedisClusterUtil;

public class RedisClusterTest extends TestBase{

	@Autowired
	private RedisClusterUtil redisUtil;
	
	@Test
	public void test() {
		String key = "redisclustertest";
		redisUtil.set(key, 12312);
		System.out.println(redisUtil.getInt(key));
	}
}
