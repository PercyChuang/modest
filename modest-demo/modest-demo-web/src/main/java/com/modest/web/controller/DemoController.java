package com.modest.web.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.modest.jms.activemq.sender.QueueSender;
import com.modest.redis.RedisUtil;
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
	
	@Autowired
	private QueueSender sender;
	
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
		
		
		logger.info("jms 发送信息测试开始 ");
		HashMap<String, Object> jmsParam = new HashMap<String,Object> ();
		jmsParam.put("12", "庄濮向1");
		jmsParam.put("34", "庄濮向2");
		jmsParam.put("56", "庄濮向3");
		jmsParam.put("78", "庄濮向4");
		jmsParam.put("910", "庄濮向5");
		jmsParam.put("1112", "庄濮向6");
		sender.send("demoTopic", "hello");
		sender.send("demoTopic1", "hello");
		sender.send("demoTopic2", "hello");
		sender.send("demoTopic3", "hello");
		
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
