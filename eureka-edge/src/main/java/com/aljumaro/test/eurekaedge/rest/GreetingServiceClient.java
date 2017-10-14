package com.aljumaro.test.eurekaedge.rest;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "greeting-service")
public interface GreetingServiceClient {

	@RequestMapping(value = "/hi/{name}", method = RequestMethod.GET)
	Map<String, String> greeting(@PathVariable("name") String name);
}
