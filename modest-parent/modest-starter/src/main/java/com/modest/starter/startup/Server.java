package com.modest.starter.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author yangyi
 * @date 2017-02-16
 */
public class Server {
	private static volatile boolean running = true;
	
	
	public static void main(String[] args) throws Exception {
		Logger logger = LoggerFactory.getLogger(Server.class);
		try {
			final AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
			ctx.registerShutdownHook();
			Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                	ctx.close();
                	synchronized (Server.class) {
                        running = false;
                        Server.class.notify();
                    }
                }
            });
			logger.info("Service start success!");
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
	        System.exit(1);
		}
		synchronized (Server.class) {
            while (running) {
                try {
                	Server.class.wait();
                } catch (Throwable e) {
                }
            }
        }
	}
}
