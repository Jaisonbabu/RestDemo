package org.cisco.demo.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {
	
	@GetMapping(value="/test/{userId}", produces= MediaType.APPLICATION_JSON_VALUE)
	public String getUserbyId( @PathVariable String userId ) {
		System.out.println(userId);
		return userId;
	}

}
