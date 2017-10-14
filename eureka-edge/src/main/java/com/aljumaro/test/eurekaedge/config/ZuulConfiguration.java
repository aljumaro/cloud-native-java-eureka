package com.aljumaro.test.eurekaedge.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableZuulProxy
public class ZuulConfiguration {

	@Bean
	CommandLineRunner zullDefaultRoutes(RouteLocator routeLocator) {

		Log log = LogFactory.getLog(getClass());

		return args -> {
			routeLocator.getRoutes().stream().forEach(route -> {
				log.info(String.format("%s (%s) - %s", route.getId(), route.getLocation(), route.getFullPath()));
			});
		};
	}

}
