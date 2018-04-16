package com.modest.jms.activemq.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

public abstract class BaseMessageListener implements MessageListener {

	protected Logger logger = LoggerFactory.getLogger(BaseMessageListener.class);
	
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			try {
				TextMessage textMessage = (TextMessage) message;
				String text = textMessage.getText();
				doExecute(text);
			} catch (Exception e) {
				logger.error("jms listener text msg error:",e);
			}
		}else {
			try {
				doExecute(message);
			} catch (Exception e) {
				logger.error("jms listener msg error:",e);
			}
		}

	}

	/**
	 * 处理消息
	 * 
	 * @param message
	 * @throws MessageException
	 * @throws Exception
	 */
	public abstract void doExecute(String message) throws Exception;
	
	/**
	 * 处理消息，非文本
	 * @param message
	 * @throws Exception
	 */
	public abstract void doExecute(Message message) throws Exception;

}
