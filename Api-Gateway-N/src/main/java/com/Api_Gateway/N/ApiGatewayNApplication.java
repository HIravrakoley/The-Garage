package com.Api_Gateway.N;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayNApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayNApplication.class, args);
	}

}
