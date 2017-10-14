package com.aljumaro.test.eurekaedge;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

@Component
public class LoadBalancedCLR implements CommandLineRunner {

	private final Log log = LogFactory.getLog(getClass());

	private final RestTemplate restTemplate;

	public LoadBalancedCLR(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	@Override
	public void run(String... args) throws Exception {
		Map<String, String> variables = Collections.singletonMap("name", "Cloud Natives!");

		ResponseEntity<JsonNode> response = this.restTemplate.getForEntity("//greeting-service/hi/{name}",
				JsonNode.class, variables);
		JsonNode body = response.getBody();
		String greeting = body.get("greeting").asText();
		log.info("greeting: " + greeting);

	}

}
