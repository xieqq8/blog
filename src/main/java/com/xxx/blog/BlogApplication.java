package com.xxx.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

//@SpringBootApplication
//public class BlogApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(BlogApplication.class, args);
//	}
//}

// 发布用：
// 将项目的启动类Application.java继承SpringBootServletInitializer并重写configure方法
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class BlogApplication extends SpringBootServletInitializer {

	public static void main(String[] args){
		SpringApplication.run(BlogApplication.class, args);

	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BlogApplication.class);
	}
	
}
