package com.demo.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestBase {

	public static void hide() {
		System.out.println("parent ");
	}
	
	public  void overri() {
		System.out.println("parent overri");
	}
}
