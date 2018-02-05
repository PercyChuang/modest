package com.modest.starter.startup;

import com.modest.starter.startup.StartupWithDubbo;

public class Server {
	public static void main(String[] args) throws Exception {
		StartupWithDubbo.start("applicationContext.xml");
	}
}
