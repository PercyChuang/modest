package com.modest.starter.server.config;

import com.modest.core.model.BaseObject;

public class ContainerConfig extends BaseObject {
    private static final long serialVersionUID = -1069291224235222482L;
    private String appContextPath;
    private int sotpListenPort;

    public int getSotpListenPort() {
        return this.sotpListenPort;
    }

    public void setSotpListenPort(final int sotpListenPort) {
        this.sotpListenPort = sotpListenPort;
    }

    public String getAppContextPath() {
        return this.appContextPath;
    }

    public void setAppContextPath(final String appContextPath) {
        this.appContextPath = appContextPath;
    }
}