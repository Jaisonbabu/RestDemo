package org.cisco.demo.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.cisco.demo.service.ClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(ClientController.class)
public class ClientControleerTest {
	
	@Autowired
	MockMvc mockMvc;

	@MockBean
	ClientService clientServiceMock;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	public void testCreateObjectSuccess() {
		try{
			when(clientServiceMock.create(getObject_Success())).thenReturn(getObjectCreate_Success());
			mockMvc.perform(post("/api/objects")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsBytes(getObject_Success())))
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

	private HashMap<String,String> getObjectCreate_Success() {
		HashMap<String, String> objectCreateSuccess = new HashMap<String,String>();
		objectCreateSuccess.put("uid", "918eff12-e0a3-4d45-8191-35a4330fd601");
		objectCreateSuccess.put("name", "jaison");
		return objectCreateSuccess;
	} 
	
	private HashMap<String,String> getObject_Success() {
		HashMap<String, String> objectCreateSuccess = new HashMap<String,String>();
		objectCreateSuccess.put("name", "jaison");
		return objectCreateSuccess;
	} 

}
