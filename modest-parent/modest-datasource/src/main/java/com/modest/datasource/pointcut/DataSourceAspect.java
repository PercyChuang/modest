package com.modest.datasource.pointcut;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.modest.datasource.DynamicDataSourceHolder;
import com.modest.datasource.annotation.DataSource;

public class DataSourceAspect {

    /**
     * 拦截目标方法，获取由@DataSource指定的数据源标识，设置到线程存储中以便切换数据源
     * 
     * @param point
     * @throws Exception
     */
    public void intercept(JoinPoint point) throws Exception {
        Class<?> target = point.getTarget().getClass();
        MethodSignature signature = (MethodSignature) point.getSignature();
        // 默认使用目标类型的注解，如果没有则使用其实现接口的注解
        for (Class<?> clazz : target.getInterfaces()) {
            resolveDataSource(clazz, signature.getMethod());
        }
        resolveDataSource(target, signature.getMethod());
    }

    public void interceptAfter(JoinPoint point) throws Exception {
        DynamicDataSourceHolder.clearDataSource();
    }
    /**
     * 提取目标对象方法注解和类型注解中的数据源标识
     * 
     * @param clazz
     * @param method
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     */
    private void resolveDataSource(Class<?> clazz, Method method) throws NoSuchMethodException, SecurityException {
        Class<?>[] types = method.getParameterTypes();
        // 默认使用类型注解
        if (clazz.isAnnotationPresent(DataSource.class)) {
            DataSource source = clazz.getAnnotation(DataSource.class);
            DynamicDataSourceHolder.setDataSource(source.value());
        }
        // 方法注解可以覆盖类型注解
        Method m = clazz.getMethod(method.getName(), types);
        if (m != null && m.isAnnotationPresent(DataSource.class)) {
            DataSource source = m.getAnnotation(DataSource.class);
            DynamicDataSourceHolder.setDataSource(source.value());
        }
    }

}
