package com.modest.starter.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

public class ContainerShutdownHook extends Thread {
    private final Logger logger = LoggerFactory.getLogger(ContainerShutdownHook.class);
    private final ConfigurableApplicationContext appContext;

    public ContainerShutdownHook(final ConfigurableApplicationContext appContext) {
        this.appContext = appContext;
    }

    @Override
    public void run() {
        logger.warn("ServerShutdownHook started.");
        if (this.appContext != null && this.appContext.isActive()) {
            try {
                this.appContext.stop();
            } finally {
                this.appContext.close();
            }
        }
        logger.warn("ServerShutdownHook completed.");
    }
}