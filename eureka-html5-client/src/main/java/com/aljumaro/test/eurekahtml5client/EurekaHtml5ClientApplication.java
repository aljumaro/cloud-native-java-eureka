package com.aljumaro.test.eurekahtml5client;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@EnableAutoConfiguration
@SpringBootApplication
public class EurekaHtml5ClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaHtml5ClientApplication.class, args);
	}
}

@RestController
class Html5ClientEndpoint {

	private final LoadBalancerClient loadBalancerClient;

	public Html5ClientEndpoint(LoadBalancerClient loadBalancerClient) {
		super();
		this.loadBalancerClient = loadBalancerClient;
	}

	@GetMapping("/greeting-client-uri")
	Map<String, String> greetingClientUri() {
		return Optional.ofNullable(this.loadBalancerClient.choose("edge-service"))
				.map(si -> Collections.singletonMap("uri", si.getUri().toString())).orElse(Collections.emptyMap());
	}

}
