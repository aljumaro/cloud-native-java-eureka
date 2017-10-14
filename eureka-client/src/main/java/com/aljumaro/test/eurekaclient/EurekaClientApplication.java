package com.aljumaro.test.eurekaclient;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
public class EurekaClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaClientApplication.class, args);
	}
}

@Profile({ "default", "insecure" })
@RestController
class GreetingController {

	@GetMapping("/hi/{name}")
	Map<String, String> hi(@PathVariable String name,
			@RequestHeader(name = "X-CNJ-name", required = false) Optional<String> header) {

		String person = header.orElse(name);

		return Collections.singletonMap("greeting", String.format("Hi, %s!", person));

	}
}
