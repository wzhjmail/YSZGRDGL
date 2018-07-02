package com.wzj.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Configuration
public class SMSUtil {
	//在controller中可以直接使用
	@Value("#{configProperties['name']}")
	private String name;
			
	/*@Bean(name="name")
	public String getName(){
		return name;
	}
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				new String[]{"classpath:spring-mybatis.xml","classpath:spring-mvc.xml"});
		String name = (String) ctx.getBean("name");
		System.out.println(name+"====================");
	}*/
}
