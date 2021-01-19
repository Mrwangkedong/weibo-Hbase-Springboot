package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication        //标注一个主程序类，说明是SpringBoot应用
public class DemoApplication {

	public static void main(String[] args) {
		//Spring启动起来
		SpringApplication.run(DemoApplication.class, args);
	}

}
