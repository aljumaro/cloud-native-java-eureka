package com.aljumaro.test.eurekaauth.client;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Client {

	@Id
	@GeneratedValue
	private long id;

	private String clientId, secret;

	private final String scopes = StringUtils.arrayToCommaDelimitedString(new String[] { "openid" });

	private final String authorizedGrantTypes = StringUtils
			.arrayToCommaDelimitedString(new String[] { "authorization_code", "refresh_token", "password" });

	private final String authorities = StringUtils
			.arrayToCommaDelimitedString(new String[] { "ROLE_USER", "ROLE_ADMIN" });

	private final String autoApproveScopes = StringUtils.arrayToCommaDelimitedString(new String[] { ".*" });

	public Client(String clientId, String secret) {
		super();
		this.clientId = clientId;
		this.secret = secret;
	}

}
