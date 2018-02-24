package com.modest.dubbo.impl;

import com.modest.dubbo.interfaces.IDemoService;

public class DomoServiceImpl implements IDemoService{

	@Override
	public String getMessage(String message) {
		System.out.println("server receive message:"+message);
		return "server send message:" + "hello!";
	}

}
