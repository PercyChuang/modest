package com.modest.core.annotation;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;

public interface AnnotationHandler {

    void handle(Map<String, Set<BeanDefinition>> annotationConfigs) throws Exception;
}
