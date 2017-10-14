package com.aljumaro.test.eurekaedge.rest;

import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Profile({ "default", "insecure" })
@RestController
@RequestMapping("/api")
public class RestTemplateClientApiGateway {

	private final RestTemplate restTemplate;

	public RestTemplateClientApiGateway(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	@GetMapping("/resttemplate/{name}")
	Map<String, String> restTemplate(@PathVariable String name) {
		//@formatter:off
		ParameterizedTypeReference<Map<String, String>> type = new ParameterizedTypeReference<Map<String, String>>() {};
		//@formatter:on

		ResponseEntity<Map<String, String>> response = this.restTemplate.exchange("http://greeting-service/hi/{name}",
				HttpMethod.GET, null, type, name);

		return response.getBody();
	}
}
