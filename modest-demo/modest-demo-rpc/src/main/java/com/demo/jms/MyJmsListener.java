package com.demo.jms;

import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modest.jms.activemq.listener.BaseMessageListener;

public class MyJmsListener extends  BaseMessageListener {

	private Logger logger = LoggerFactory.getLogger(MyJmsListener.class);
	
	@Override
	public void doExecute(String message) throws Exception {
		logger.info("MyJmsListener reciven message:{}",message);
	}

	@Override
	public void doExecute(Message message) throws Exception {
		
	}

}
