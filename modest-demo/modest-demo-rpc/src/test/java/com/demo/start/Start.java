package com.demo.start;

import com.modest.starter.startup.StartupWithDubbo;

/**
 * 非单元测试的方式来启动项目
 * @author Edmond Chuang
 */
public class Start {
	public static void main(String[] args) throws Exception {
		StartupWithDubbo.start("applicationContext.xml");
	}
}
