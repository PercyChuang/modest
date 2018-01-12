package com.modest.mybatis.mapper;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import com.modest.core.util.BeanUtils;

/**
 * 通过此类，我们可以从上下文件中拿到我们定义的base包路经,然后交给mybaites扫描
 * 注意：在core中的ScannerConfigurer的值。这里我们把它用作DAO扫描的类处理。
 * @author 庄濮向 Edmond Chuang
 */
public class MapperScannerConfigurer extends org.mybatis.spring.mapper.MapperScannerConfigurer {
    private ApplicationContext applicationContext;
    private String basePackage;

    @Override
    public void setBasePackage(final String basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        super.setApplicationContext(applicationContext);
        setAnnotationClass(Repository.class);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.basePackage == null) {
            String allBasePackages = BeanUtils.getAllBasePackagesString(this.applicationContext);
            super.setBasePackage(allBasePackages.toString());
        } else {
            super.setBasePackage(this.basePackage);
        }
        super.afterPropertiesSet();
    }
}