package com.modest.starter.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartupWithDubbo {
	private static volatile boolean running = true;

	
	/**
	 * 传入文件名，启动项目
	 * @param fileName
	 * @throws Exception
	 */
	public static void start(String fileName) throws Exception {
		Logger logger = LoggerFactory.getLogger(StartupWithDubbo.class);
		try {
			final AbstractApplicationContext ctx = new ClassPathXmlApplicationContext(fileName);
			ctx.registerShutdownHook();
			Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                	ctx.close();
                	synchronized (StartupWithDubbo.class) {
                        running = false;
                        StartupWithDubbo.class.notify();
                    }
                }
            });
			logger.info("Service start success!");
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
	        System.exit(1);
		}
		synchronized (StartupWithDubbo.class) {
            while (running) {
                try {
                	StartupWithDubbo.class.wait();
                } catch (Throwable e) {
                }
            }
        }
	}
}
