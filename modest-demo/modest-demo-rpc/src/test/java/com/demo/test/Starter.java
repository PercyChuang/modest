package com.demo.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo.pojo.ScheduleJobDO;
import com.demo.service.ScheduleJobService;
import com.modest.core.util.ApplicationContextHolder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class Starter {

	@Autowired
	private ScheduleJobService scheduleJobService;
	
	@Value("${jdbc.write.pool.size.max}")
	private String pro;
	
	private Logger logger = LoggerFactory.getLogger(Starter.class);
	
	@Test
	public void test() {
		logger.info("555555555555555555555555555555");
		System.out.println("得到的配置文件的值 是：----------->"+pro);
		ScheduleJobDO result = scheduleJobService.test();
		System.out.println("是否可以拿到上下文：--->" +ApplicationContextHolder.get());
		System.out.println(result.toString());
	}
	
	public static void main(String[] args) {
		String serverHome = System.getProperty("server.home");
		System.out.println(serverHome);
	}
}
