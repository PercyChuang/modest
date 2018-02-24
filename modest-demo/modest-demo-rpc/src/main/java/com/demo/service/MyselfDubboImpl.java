package com.demo.service;

import org.springframework.stereotype.Service;

@Service("dubbo.myself")
public class MyselfDubboImpl implements MyselfDubbo {

	@Override
	public String sayHello(String hello) {
		System.out.println(" servr say:"+hello);
		return "hello";
	}

}
