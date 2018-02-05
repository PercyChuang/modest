package com.demo.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.demo.service.MyselfDubbo;
import com.modest.dubbo.interfaces.IDemoService;

public class RpcTest extends TestBase{
	
	@Autowired
	private IDemoService usename;
	
	@Autowired
	private MyselfDubbo xx;
	
	@Test
	public void test() {
		String back = usename.getMessage("hello");
		System.out.println(back);
		
		System.out.println(xx.sayHello("hello"));
	}
}
