package com.modest.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * logger use demo
 * @author percy
 */
public class LoggerDemo {
	private static Logger logger = LoggerFactory.getLogger(LoggerDemo.class);
	
	public static void main(String[] args) {
		logger.debug("debug");
		logger.info("info");
		logger.error("error");
		//copy logbace-sample.xml to your classpath and change name to logback.xml
		//default use debug
		
	}
}
