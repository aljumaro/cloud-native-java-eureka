package com.aljumaro.test.eurekaedge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.util.concurrent.RateLimiter;

@Configuration
public class RateLimiterConfiguration {

	@Bean
	RateLimiter rateLimiter() {
		return RateLimiter.create(1.0D / 10.0D);
	}

}
