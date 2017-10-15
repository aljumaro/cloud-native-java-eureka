package com.aljumaro.test.eurekaedge.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.event.EventListener;

//@Component
public class RoutesListener {

	private final RouteLocator routeLocator;
	private final DiscoveryClient discoveryClient;
	private final Log log = LogFactory.getLog(getClass());

	public RoutesListener(RouteLocator routeLocator, DiscoveryClient discoveryClient) {
		super();
		this.routeLocator = routeLocator;
		this.discoveryClient = discoveryClient;
	}

	@EventListener(HeartbeatEvent.class)
	void onHeartBeatEvent(HeartbeatEvent event) {
		this.log.info("onHeartBeatEvent()");

		discoveryClient.getServices().stream().map(x -> x.toUpperCase()).forEach(this.log::info);
	}

	@EventListener(RoutesRefreshedEvent.class)
	void onRoutesRefreshedEvent(RoutesRefreshedEvent event) {
		this.log.info("onRoutesRefreshedEvent()");

		routeLocator.getRoutes().stream().map(x -> x.getFullPath()).forEach(this.log::info);
	}

}
