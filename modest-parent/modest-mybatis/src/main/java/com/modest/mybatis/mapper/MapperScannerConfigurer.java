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

	/**
	 * annotationClass 属性指定了要寻找的注解名称。
	 * markerInterface 属性指定了要寻找的父接口。如果两者都被指定了，加入到接口中的映射器会匹配两种标准。
	 * 默认情况下，这两个属性都是 null，所以在基包中给定的所有接口可以作为映射器加载。
	 * 这里指定加了repository注解的类。
	 * 后期指定扩展baseDao时，可以在这里扩展。
	 * springboot已把annotationClass设置成Mapper和repository两个值，加入这两个值都会生效。
	 **/
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