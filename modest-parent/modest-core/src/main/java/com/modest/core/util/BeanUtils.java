package com.modest.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;

import com.modest.core.config.ScannerConfigurer;

public class BeanUtils {

    public static String getAllBasePackagesString(final ApplicationContext applicationContext) {
        StringBuilder allBasePackages = new StringBuilder(128);
        getBasePackages(applicationContext, allBasePackages);
        String string = allBasePackages.toString().trim();
        if (string.endsWith(",")) {
            string = string.substring(0, string.length() - 1);
        }
        return string;
    }

    private static void getBasePackages(final ApplicationContext applicationContext, final StringBuilder allBasePackages) {
        Map<String, ScannerConfigurer> basePackagesMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(
                applicationContext, ScannerConfigurer.class, true, false);
        for (ScannerConfigurer configurer : basePackagesMap.values()) {
            String[] basePackages = configurer.getBasePackages();
            if (basePackages != null && basePackages.length > 0) {
                for (String basePackage : basePackages) {
                    if (StringUtils.isNotBlank(basePackage)) {
                        allBasePackages.append(basePackage.trim());
                        allBasePackages.append(",");
                    }
                }
            }
        }
        if (applicationContext.getParent() != null) {
            getBasePackages(applicationContext.getParent(), allBasePackages);
        }
    }

    public static Set<String> getAllBasePackagesList(final ApplicationContext applicationContext) {
        Map<String, Boolean> allBasePackagesMap = new HashMap<String, Boolean>();
        findBasePackages(applicationContext, allBasePackagesMap);
        return allBasePackagesMap.keySet();
    }

    private static void findBasePackages(final ApplicationContext applicationContext,
            final Map<String, Boolean> allBasePackagesMap) {
        Map<String, ScannerConfigurer> basePackagesMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(
                applicationContext, ScannerConfigurer.class, true, false);
        String[] basePackages = null;
        for (ScannerConfigurer configurer : basePackagesMap.values()) {
            basePackages = configurer.getBasePackages();
            if (basePackages != null && basePackages.length > 0) {
                for (String basePackage : basePackages) {
                    if (StringUtils.isNotBlank(basePackage)) {
                        allBasePackagesMap.put(basePackage.trim(), true);
                    }
                }
            }
        }
        if (applicationContext.getParent() != null) {
            findBasePackages(applicationContext.getParent(), allBasePackagesMap);
        }
    }

    public static <T> Set<T> findAllBeans(final ApplicationContext applicationContext, final Class<T> type) {
        Map<T, Boolean> container = new HashMap<T, Boolean>();
        findBeans(applicationContext, container, type);
        return container.keySet();
    }

    private static <T> void findBeans(final ApplicationContext applicationContext, final Map<T, Boolean> container,
            final Class<T> type) {
        Map<String, T> beansMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, type, true, false);
        for (T bean : beansMap.values()) {
            if (bean != null) {
                container.put(bean, true);
            }
        }
        if (applicationContext.getParent() != null) {
            findBeans(applicationContext.getParent(), container, type);
        }
    }
}