package com.modest.core.config;

import org.springframework.core.io.Resource;

/**
 * 如果配置了此对象，将替换默认的配置
 * 默认路经在classpath:conf/*.properties
 * @author 庄濮向 Edmond Chuang
 */
public class PropertiesPath {

	private Resource[] locations;

	public Resource[] getLocations() {
		return locations;
	}

	public void setLocations(Resource... locations) {
		this.locations = locations;
	}
	
}
