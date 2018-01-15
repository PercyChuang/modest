package com.modest.starter.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.AccessControlException;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.modest.starter.server.config.ContainerConfig;

public class StandardServer {
    private static final Logger logger = LoggerFactory.getLogger(StandardServer.class);

    private String containerConfigPath = "classpath:container-spring_config.xml";
    private String containerDefaultConfigPath = "classpath:container-spring_config_default.xml";

    private Thread shutdownHook;
    private ContainerConfig containerConfig;
    private ClassPathXmlApplicationContext appContext;

    public void start() throws Exception {
        initContainer();
        logger.info("The container has started.");
        await();
        stop();
    }

    private void initContainer() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(this.containerConfigPath);
        this.containerConfig = context.getBean(ContainerConfig.class);
        String[] configLocations = { this.containerConfig.getAppContextPath(), containerDefaultConfigPath };
        this.appContext = new ClassPathXmlApplicationContext(configLocations, false, context);
        this.appContext.refresh();
        this.shutdownHook = new ContainerShutdownHook(this.appContext);
        try {
            Runtime.getRuntime().addShutdownHook(this.shutdownHook);
        } catch (Throwable e) {
        }
    }

    private final String shutdown = "SHUTDOWN";

    private void await() {
        ServerSocket awaitSocket = null;
        try {
            awaitSocket = new ServerSocket(this.containerConfig.getSotpListenPort(), 1);
        } catch (IOException e) {
            logger.error("StandardServer.await: create:", e);
            System.exit(1);
        }
        Random random = null;
        for (;;) {
            StringBuilder command = new StringBuilder();
            Socket socket = null;
            InputStream stream = null;
            try {
                try {
                    socket = awaitSocket.accept();
                    socket.setSoTimeout(10 * 1000);
                    stream = socket.getInputStream();
                } catch (SocketTimeoutException ste) {
                    logger.warn(ste.getMessage(), ste);
                    continue;
                } catch (AccessControlException ace) {
                    logger.warn("StandardServer.accept security exception: ", ace);
                    continue;
                } catch (IOException e) {
                    logger.error("StandardServer.await: accept: ", e);
                    break;
                }
                int expected = 128;
                while (expected < shutdown.length()) {
                    if (random == null) {
                        random = new Random();
                    }
                    expected += random.nextInt() % 128;
                }
                while (expected > 0) {
                    int ch = -1;
                    try {
                        ch = stream.read();
                    } catch (IOException e) {
                        logger.warn("StandardServer.await: read: ", e);
                        ch = -1;
                    }
                    if (ch < 32) {
                        break;
                    }
                    command.append((char) ch);
                    expected--;
                }
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                    }
                }
            }
            boolean match = command.toString().equals(shutdown);
            if (match) {
                logger.info("StandardServer.await: valid command '" + command.toString() + "' received");
                break;
            } else {
                logger.warn("StandardServer.await: Invalid command '" + command.toString() + "' received");
            }
        }
        try {
            awaitSocket.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void stop() {
        if (this.shutdownHook != null) {
            try {
                Runtime.getRuntime().removeShutdownHook(this.shutdownHook);
            } catch (Throwable e) {
            }
        }
        if (this.appContext == null) {
            return;
        }
        try {
            logger.info("Began to stop container...");
            this.appContext.stop();
            logger.info("The container has bean stopped.");
        } finally {
            logger.info("Began to close container...");
            this.appContext.close();
            logger.info("The container has bean closed.");
        }
    }

    public void stopServer() {
        Socket socket = null;
        OutputStream out = null;
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(this.containerConfigPath);
        try {
            ContainerConfig config = context.getBean(ContainerConfig.class);
            String hostAddress = InetAddress.getByName("localhost").getHostAddress();
            socket = new Socket(hostAddress, config.getSotpListenPort());
            out = socket.getOutputStream();
            for (int i = 0; i < shutdown.length(); i++) {
                out.write(shutdown.charAt(i));
            }
            out.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
            context.close();
        }
    }

    public void setContainerConfigPath(final String containerConfigPath) {
        this.containerConfigPath = containerConfigPath;
    }

    public void setContainerDefaultConfigPath(final String containerDefaultConfigPath) {
        this.containerDefaultConfigPath = containerDefaultConfigPath;
    }

}