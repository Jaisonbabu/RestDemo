package org.cisco.demo.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.HashMap;

import org.cisco.demo.error.ApiError;
import org.cisco.demo.service.ClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(ClientController.class)
public class ClientControllerTest {
	
	@Autowired
	MockMvc mockMvc;

	@MockBean
	ClientService clientServiceMock;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	public void testCreateObjectSuccess() {
		try{
			when(clientServiceMock.create(getRequestObject())).thenReturn(getMockObject_Success());
			mockMvc.perform(post("/api/objects")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsBytes(getRequestObject())))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			
			//Check if data exists
			.andExpect(jsonPath("$.name").exists())
			.andExpect(jsonPath("$.uid").exists())
			.andExpect(jsonPath("$.errorMsg").doesNotExist())

			//check the data type
			.andExpect(jsonPath("$.name").isString())
			.andExpect(jsonPath("$.uid").isString())
		
			// check the values
			.andExpect(jsonPath("$.name").value("jaison"))
			.andExpect(jsonPath("$.uid").value("918eff12-e0a3-4d45-8191-35a4330fd601"));
		} catch  (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetObjectByIDSuccess() {
		try{
			when(clientServiceMock.getObjectById("918eff12-e0a3-4d45-8191-35a4330fd601")).thenReturn(getMockObject_Success());
			mockMvc.perform(get("/api/objects/918eff12-e0a3-4d45-8191-35a4330fd601"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			
			//Check if data exists
			.andExpect(jsonPath("$.name").exists())
			.andExpect(jsonPath("$.uid").exists())
			.andExpect(jsonPath("$.errorMsg").doesNotExist())

			//check the data type
			.andExpect(jsonPath("$.name").isString())
			.andExpect(jsonPath("$.uid").isString())
		
			// check the values
			.andExpect(jsonPath("$.name").value("jaison"))
			.andExpect(jsonPath("$.uid").value("918eff12-e0a3-4d45-8191-35a4330fd601"));
		} catch  (Exception e) {
			e.printStackTrace();
		}
	}

	private HashMap<String,String> getMockObject_Success() {
		HashMap<String, String> objectCreateSuccess = new HashMap<String,String>();
		objectCreateSuccess.put("uid", "918eff12-e0a3-4d45-8191-35a4330fd601");
		objectCreateSuccess.put("name", "jaison");
		return objectCreateSuccess;
	} 
	
	private HashMap<String,String> getRequestObject() {
		HashMap<String, String> objectCreateSuccess = new HashMap<String,String>();
		objectCreateSuccess.put("name", "jaison");
		return objectCreateSuccess;
	} 
	
	private ApiError getObject_Error() {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Malformed JSON request", "uri=/api/objects" );
		return apiError;
	} 

}
