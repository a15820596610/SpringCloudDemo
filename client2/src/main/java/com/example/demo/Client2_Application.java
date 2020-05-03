package com.example.demo;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class Client2_Application {

	public static void main(String[] args) {
		SpringApplication.run(Client2_Application.class, args);
		System.out.println("=====客户端Client2启动成功==--");
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}

