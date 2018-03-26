package com.modest.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.modest.redis.RedisUtil;
import com.modest.redis.cluster.RedisClusterUtil;
import com.modest.web.service.ReadScheduleJobService;
import com.modest.web.service.ScheduleJobService;

@RestController
@RequestMapping("/lmw")
public class DemoController {
	
	@Value("${jdbc.write.pool.size.max}")
	private String pro;
	
	@Autowired
	private ScheduleJobService scheduleJobService;
	
	@Autowired
	private ReadScheduleJobService readScheduleJobService;
	
	/**
	 * 集群redis测试
	 */
	//@Autowired
	//private RedisClusterUtil redisClusterUtil;
	
	@Autowired
	private RedisUtil modestRedisUtil;
	
	private Logger logger = LoggerFactory.getLogger(DemoController.class);
	
	@RequestMapping(params = "method=007")
	public Object useMethod(HttpServletRequest request,String pa) {
		String key = "redisClusterKey";
		//redisClusterUtil.set(key, "hello cluster!");
		
		//String value = redisClusterUtil.getString(key);
		
		modestRedisUtil.setVal(key, "你好阿里云redis!");
		
		String value1 = modestRedisUtil.getVal(key);
		
		System.out.println(value1);
		//System.out.println(value);
		
		
		logger.debug("123213");
		logger.info("ccc");
		System.out.println("controller拿不到的，是因为不是一个上下文哦："+pro);
		System.out.println(pa);
		scheduleJobService.test();
		readScheduleJobService.test();
		scheduleJobService.test();
		return "method 007 success";
	}
	
	@RequestMapping(value = "/value")
	public Object useValue(HttpServletRequest request,BB b) {
		logger.debug("123213");
		logger.info("ccc");
		System.out.println(b.getPa());
		return "pa success";
	}
	
}
