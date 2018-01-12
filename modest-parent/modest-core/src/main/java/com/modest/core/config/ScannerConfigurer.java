package com.modest.core.config;

public class ScannerConfigurer {
    private String[] basePackages;
    private String[] annotationTypes;

    public String[] getBasePackages() {
        return this.basePackages;
    }

    public void setBasePackages(final String[] basePackages) {
        this.basePackages = basePackages;
    }

    public String[] getAnnotationTypes() {
        return this.annotationTypes;
    }

    public void setAnnotationTypes(final String[] annotationTypes) {
        this.annotationTypes = annotationTypes;
    }
}
