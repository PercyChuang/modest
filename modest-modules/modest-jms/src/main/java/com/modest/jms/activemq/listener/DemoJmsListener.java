package com.modest.jms.activemq.listener;

import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * listener demo
 */
public class DemoJmsListener extends BaseMessageListener {

	private Logger logger = LoggerFactory.getLogger(DemoJmsListener.class);
	
	@Override
	public void doExecute(String message) throws Exception {
		logger.info("demo listener revice message :{}",message);
	}

	@Override
	public void doExecute(Message message) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
