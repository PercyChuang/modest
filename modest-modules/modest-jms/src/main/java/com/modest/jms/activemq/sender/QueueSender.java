package com.modest.jms.activemq.sender;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.alibaba.fastjson.JSON;

public class QueueSender {

	private Logger logger = LoggerFactory.getLogger(QueueSender.class);

	private JmsTemplate jmsTemplate;

	public void send(final String topic, final Object message) {
		if (StringUtils.isBlank(topic) || null == message) {
			logger.error("jms send error! topic or message are null");
			return;
		}
		jmsTemplate.send(topic, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				String msg = JSON.toJSONString(message);
				logger.info("topic:{},send message:{}",topic,msg);
				return session.createTextMessage(msg);
			}
		});
	}

	public void sendMsg(final String topic, final Object obj) {
		try {
			send(topic, obj);
		} catch (Throwable e) {
			logger.error("topic:{},send message error!",topic,e.getMessage());
			logger.info("topic:{},send message error!",topic,e.getMessage());
		}
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
}