package com.aljumaro.test.eurekaedge;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.stereotype.Component;

@Component
public class DiscoveryClientCLR implements CommandLineRunner {

	private final Log log = LogFactory.getLog(getClass());

	private final DiscoveryClient discoveryClient;
	private final Registration registration;

	public DiscoveryClientCLR(DiscoveryClient discoveryClient, Registration registration) {
		super();
		this.discoveryClient = discoveryClient;
		this.registration = registration;
	}

	@Override
	public void run(String... args) throws Exception {
		this.log.info("localServiceInstance: " + this.registration.getServiceId());

		this.discoveryClient.getInstances("greeting-service").stream().forEach(this::logServiceInstance);
	}

	private void logServiceInstance(ServiceInstance serviceInstance) {
		String msg = String.format("host = %s, port = %s, service ID = %s", serviceInstance.getHost(),
				serviceInstance.getPort(), serviceInstance.getServiceId());
		log.info(msg);
	}

}
