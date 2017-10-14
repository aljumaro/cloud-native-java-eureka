package com.aljumaro.test.eurekaedge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class EurekaEdgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaEdgeApplication.class, args);
	}
}
