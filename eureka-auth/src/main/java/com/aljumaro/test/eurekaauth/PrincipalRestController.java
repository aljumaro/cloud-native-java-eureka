package com.aljumaro.test.eurekaauth;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PrincipalRestController {

	@RequestMapping("/user")
	Principal principal(Principal p) {
		return p;
	}

}