package com.aljumaro.test.eurekaclient;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Configuration
@Profile("secure")
@EnableResourceServer
@EnableOAuth2Client
class OAuthResourceConfiguration {
}