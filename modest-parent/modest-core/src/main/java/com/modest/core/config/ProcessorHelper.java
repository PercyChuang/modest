package com.modest.core.config;

class ProcessorHelper {

    private final int processors;

    public ProcessorHelper() {
        processors = Runtime.getRuntime().availableProcessors();
    }

    public String getPoolSize() {
        return processors + "-" + (processors * 3);
    }

    public int getProcessors() {
        return processors;
    }

}
