package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
@SpringBootApplication
public class Client3_Application {

	public static void main(String[] args) {
		SpringApplication.run(Client3_Application.class, args);
		System.out.println("=====客户端Client3启动成功==--");
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}