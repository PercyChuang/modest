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
	
	public static void hide() {
		System.out.println("son ");
	}
	
	public  void overri() {
		System.out.println("son overri");
	}
	
	public static void main(String[] args) {
		RpcTest son = new RpcTest();
		son.hide();
		RpcTest.hide();
		son.overri();
		
		TestBase parent = son;
		parent.hide();
		TestBase.hide();
		parent.overri();//静态的跟着类走，不是静态的跟着对象走。
		
	}
	
	@Test
	public void test() {
		String back = usename.getMessage("hello");
		System.out.println(back);
		
		System.out.println(xx.sayHello("hello"));
	}
}
