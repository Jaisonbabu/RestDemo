package org.cisco.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cisco.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ClientController {

	private static final Logger logger = LogManager.getLogger(ClientController.class);

	@Autowired
	ClientService clientservice;

	@PostMapping(value="/objects", produces= MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> create(@RequestBody HashMap<String, String> jsonObj ) {
		logger.info("ClientController : create");
		System.out.println(jsonObj.get("user"));
		return clientservice.create(jsonObj);	
	}

	@PutMapping(value="/objects/{objectId}", produces= MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> update(@PathVariable String objectId, @RequestBody HashMap<String, String> jsonObj) {
		logger.info("ClientController : update ");
		return clientservice.update(objectId,jsonObj);	
	}
	
	@GetMapping(value="/objects/{objectId}", produces= MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> getObjectById(@PathVariable String objectId) {
		logger.info("ClientController : getObjectById");
		return clientservice.getObjectById(objectId);	
	}
	
	@DeleteMapping(value="/objects/{objectId}")
	public void deleteObjectById(@PathVariable String objectId) {
		logger.info("ClientController : deleteObjectById");
		clientservice.deleteObjectById(objectId);	
	}

	@GetMapping(value="/objects", produces= MediaType.APPLICATION_JSON_VALUE)
	public List<String> getObjects() {
		logger.info("ClientController : getObjects");
		return clientservice.getObjects();
	}

}
