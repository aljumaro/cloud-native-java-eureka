package com.aljumaro.test.eurekaauth.config;

import java.util.Collections;
import java.util.Optional;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import com.aljumaro.test.eurekaauth.client.Client;
import com.aljumaro.test.eurekaauth.client.ClientRepository;

@Configuration
public class ClientConfiguration {

	private LoadBalancerClient loadBalancerClient;

	public ClientConfiguration(LoadBalancerClient loadBalancerClient) {
		super();
		this.loadBalancerClient = loadBalancerClient;
	}

	// @formatter:off
	@Bean
	ClientDetailsService clientDetailsService(ClientRepository clientRepository) {
		return clientId -> clientRepository.findByClientId(clientId)
				.map(this::toBaseClient)
				.orElseThrow(() -> new ClientRegistrationException(String.format(
					     "no client %s registered", clientId)));
	}
	// @formatter:on

	private BaseClientDetails toBaseClient(Client client) {
		BaseClientDetails details = new BaseClientDetails(client.getClientId(), null, client.getScopes(),
				client.getAuthorizedGrantTypes(), client.getAuthorities());
		details.setClientSecret(client.getSecret());

		ServiceInstance serviceInstance = this.loadBalancerClient.choose("edge-service");
		String greetingsClientRedirectUri = Optional.ofNullable(serviceInstance)
				.map(si -> "http://" + si.getHost() + ':' + si.getPort() + '/')
				.orElseThrow(() -> new ClientRegistrationException("couldn't find and bind a greetings-client IP"));

		details.setRegisteredRedirectUri(Collections.singleton(greetingsClientRedirectUri));
		return details;
	}
}
