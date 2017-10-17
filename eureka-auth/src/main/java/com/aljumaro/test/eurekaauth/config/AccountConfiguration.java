package com.aljumaro.test.eurekaauth.config;

import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.aljumaro.test.eurekaauth.account.Account;
import com.aljumaro.test.eurekaauth.account.AccountRepository;
import com.aljumaro.test.eurekaauth.client.Client;
import com.aljumaro.test.eurekaauth.client.ClientRepository;

@Configuration
public class AccountConfiguration {

	private final Log log = LogFactory.getLog(getClass());

	// @formatter:off
	
	@Bean
	UserDetailsService userDetailsService(AccountRepository accountRepository) {
		
		return username -> 
			accountRepository.findByUsername(username).map(account -> {
				boolean active = account.isActive();
				return new User(account.getUsername(), account.getPassword(), active, active, active, active,
						AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER"));
			})
			.orElseThrow(() -> new UsernameNotFoundException(String.format("username %s not found!", username)));
	}

	@Bean
	CommandLineRunner populateDB(AccountRepository accountRepository, ClientRepository clientRepository) {
		return args -> {
			Stream.of("dsyer:cloud", "pwebb:boot", "mminella:batch", "rwinch:security",
				    "jlong:spring").map(u -> u.split(":"))
				.forEach(up -> accountRepository.save(new Account(up[0], up[1], true)));
			
			Stream.of("html5:password", "android:secret").map(x -> x.split(":"))
			   .forEach(x -> clientRepository.save(new Client(x[0], x[1])));
		};
	}
	// @formatter:on
}
