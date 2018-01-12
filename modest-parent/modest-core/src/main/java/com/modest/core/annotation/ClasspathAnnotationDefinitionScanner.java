package com.modest.core.annotation;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.AnnotationMetadata;

import com.modest.core.util.AnnotationUtils;

/**
 * 通过此类可以扫描到我们自定义注解的使用类。
 * @author 庄濮向 Edmond.Chuang
 */
public class ClasspathAnnotationDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

    private Map<String, Set<BeanDefinition>> annotationConfigs;

    public ClasspathAnnotationDefinitionScanner() {
        super(false);
    }

    public Map<String, Set<BeanDefinition>> getAnnotationConfigs() {
        return this.annotationConfigs;
    }

    @Override
    protected boolean isCandidateComponent(final AnnotatedBeanDefinition beanDefinition) {
        AnnotationMetadata metadata = beanDefinition.getMetadata();
        return (metadata.isInterface() || metadata.isAbstract() || metadata.isConcrete() || metadata.isFinal())
                && metadata.isIndependent();
    }

    public void findAllAnnotationConfig(final String[] allAnnotationTypes, final String[] basePackages)
            throws ClassNotFoundException {
        Map<String, Set<BeanDefinition>> annonConfigs = new ConcurrentHashMap<String, Set<BeanDefinition>>();
        for (String annotationType : allAnnotationTypes) {
            Set<BeanDefinition> candidates = AnnotationUtils.findAnnotationBeanDefinition(basePackages, annotationType);
            annonConfigs.put(annotationType, candidates);
        }
        this.annotationConfigs = Collections.unmodifiableMap(annonConfigs);
    }
}
