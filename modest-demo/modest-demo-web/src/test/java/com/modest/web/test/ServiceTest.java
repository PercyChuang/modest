package com.modest.web.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.modest.core.util.ApplicationContextHolder;
import com.modest.web.pojo.ScheduleJobDO;
import com.modest.web.service.ScheduleJobService;


public class ServiceTest extends TestBase{
	
	@Autowired
	private ScheduleJobService scheduleJobService;
	
	@Value("${jdbc.write.pool.size.max}")
	private String pro;
	
	@Test
	public void test() {
		System.out.println("得到的配置文件的值 是：----------->"+pro);
		ScheduleJobDO result = scheduleJobService.test();
		System.out.println("是否可以拿到上下文：--->" +ApplicationContextHolder.get());
		System.out.println(result.toString());
		System.out.println("test能到到，是因为它是默认的上下文："+pro);
	}
	
	public static void main(String[] args) {
		String serverHome = System.getProperty("server.home");
		System.out.println(serverHome);
	}
}
