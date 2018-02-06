package com.modest.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.modest.web.pojo.ScheduleJobDO;
import com.modest.web.service.ScheduleJobService;

@RestController
@RequestMapping("/lmw")
public class DemoController {
	
	@Value("${jdbc.write.pool.size.max}")
	private String pro;
	
	@Autowired
	private ScheduleJobService scheduleJobService;
	
	@Autowired
	private ScheduleJobDO bb;
	
	@RequestMapping(params = "method=007")
	public Object useMethod(HttpServletRequest request,String pa) {
		System.out.println(bb);
		System.out.println("controller拿不到的，是因为不是一个上下文哦："+pro);
		System.out.println(pa);
		scheduleJobService.test();
		return "method 007 success";
	}
	
	@RequestMapping(value = "/value")
	public Object useValue(HttpServletRequest request,BB b) {
		System.out.println(b.getPa());
		return "pa success";
	}
	
}
