package com.modest.starter.startup;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * 需要在配置文件中配置server.home变量
 * @author edmond chuang
 */
public final class Startup {
    private static final String SERVER_ENV = "server.home";
    private static final String LIB_PATH = "/lib/";
    private static final String SERVER_CLASS = "com.modest.starter.server.StandardServer";
    private static final String START_COMMAND = "start";
    private static final String STOP_COMMAND = "stop";
    private static Startup daemon = null;
    private ClassLoader classLoader;
    private String serverHome;
    private Object server;

    public static void main(final String[] args) throws Exception {
        if (daemon == null) {
            Startup startup = new Startup();
            try {
                startup.init();
            } catch (Throwable t) {
                t.printStackTrace();
                return;
            }
            daemon = startup;
        }
        String command = START_COMMAND;
        if (args.length > 0) {
            command = args[0];
        }
        try {
            if (START_COMMAND.equals(command)) {
                daemon.start();
            } else if (STOP_COMMAND.equals(command)) {
                daemon.stop();
            } else {
                System.out.println("command \"" + command + "\" does not exist.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void init() throws Exception {
        serverHome();
        classLoader();
        Thread.currentThread().setContextClassLoader(this.classLoader);
        Class<?> serverClass = this.classLoader.loadClass(SERVER_CLASS);
        this.server = serverClass.newInstance();
    }

    private void serverHome() throws Exception {
        serverHome = System.getProperty(SERVER_ENV);
        if (serverHome != null && serverHome.trim().length() > 0) {
            System.out.println("server.home has been set : server.home = " + serverHome);
        } else {
            throw new Exception("System property(server.home) is not found!");
        }
    }

    private void classLoader() {
        try {
            this.classLoader = createClassLoader(null);
        } catch (Throwable e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private ClassLoader createClassLoader(final ClassLoader parent) throws Exception {
        StringBuilder libLocation = new StringBuilder(32);
        libLocation.append(serverHome).append(LIB_PATH);
        File directory = new File(libLocation.toString());
        if (!directory.exists() || !directory.isDirectory() || !directory.canRead()) {
            throw new Exception("lib location is not exists or is not a directiory or can't read!");
        }
        File[] files = directory.listFiles();
        List<URL> urls = new ArrayList<URL>(files.length);
        for (File file : files) {
            String name = file.getName();
            if (name.toLowerCase().endsWith(".jar")) {
                if (!file.canRead()) {
                    System.err.println("File " + name + " can not be read.");
                } else {
                    urls.add(file.toURI().toURL());
                }
            }
        }
        URL[] array = urls.toArray(new URL[urls.size()]);
        for (int i = 0, len = array.length; i < len; i++) {
            System.out.println(" location " + (i + 1) + " is " + array[i]);
        }
        if (parent == null) {
            return new URLClassLoader(array);
        }
        return new URLClassLoader(array, parent);
    }

    private void start() throws Exception {
        Method method = this.server.getClass().getMethod("start");
        method.invoke(this.server, (Object[]) null);
    }

    private void stop() throws Exception {
        Method method = this.server.getClass().getMethod("stopServer");
        method.invoke(this.server, (Object[]) null);
    }
}
