package com.aljumaro.test.eurekaedge.rest;

import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("feign")
@RestController
@RequestMapping("/api")
public class FeignClientApiGateway {

	private final GreetingServiceClient greetingServiceClient;

	public FeignClientApiGateway(GreetingServiceClient greetingServiceClient) {
		super();
		this.greetingServiceClient = greetingServiceClient;
	}

	@GetMapping("/feign/{name}")
	Map<String, String> greeting(@PathVariable String name) {
		return greetingServiceClient.greeting(name);
	}
}
