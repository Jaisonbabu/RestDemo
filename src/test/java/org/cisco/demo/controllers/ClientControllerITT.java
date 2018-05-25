package org.cisco.demo.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.cisco.demo.RestDemoApplication;
import org.cisco.demo.error.ApiError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientControllerITT {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	ObjectMapper objectMapper;

	@Test
	public void testCreateObjectITSucesss() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Object obj = null;
		try {
			obj = objectMapper.writeValueAsBytes(getObject_Success());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(obj,headers);
		ResponseEntity<Object> responseEntity = restTemplate.postForEntity("/api/objects", requestEntity, Object.class);
		
		assertEquals(true,responseEntity.hasBody());
		Object responseBody = responseEntity.getBody();
		HashMap<String, String> map = objectMapper.convertValue(responseBody, HashMap.class);
		System.out.println(responseEntity.getBody());
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		assertEquals("jaison",map.get("name"));
		assertTrue(map.get("uid") instanceof String);
	}
	
	@Test
	public void testCreateObjectITError() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Object obj = null;
		try {
			obj = objectMapper.writeValueAsBytes(null);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(obj,headers);
		ResponseEntity<Object> responseEntity = restTemplate.postForEntity("/api/objects", requestEntity, Object.class);
		
		assertEquals(true,responseEntity.hasBody());
		Object responseBody = responseEntity.getBody();
		ApiError apiError = objectMapper.convertValue(responseBody, ApiError.class);
		System.out.println(responseEntity.getBody());
		
		assertEquals(HttpStatus.BAD_REQUEST, apiError.getStatus());
		
		assertEquals("Malformed JSON request",apiError.getMessage());
		assertEquals("uri=/api/objects", apiError.getPath());
	}
	
	private HashMap<String,String> getObject_Success() {
		HashMap<String, String> objectCreateSuccess = new HashMap<String,String>();
		objectCreateSuccess.put("name", "jaison");
		return objectCreateSuccess;
	} 
	
	private ApiError getObject_Error() {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Malformed JSON request", "uri=/api/objects" );
		return apiError;
	} 

}
