package com.modest.datasource.pointcut;

import org.aspectj.lang.annotation.Pointcut;

public class DataSourcePointcuts {

    @Pointcut("@within(com.modest.datasource.annotation.DataSource)")
    public void dsMarkPointcut() {}
}