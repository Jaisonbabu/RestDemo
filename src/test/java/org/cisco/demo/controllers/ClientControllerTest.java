package org.cisco.demo.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.cisco.demo.error.ApiError;
import org.cisco.demo.exceptions.ObjectNotFoundException;
import org.cisco.demo.service.ClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
		try {
			when(clientServiceMock.create(getRequestObject())).thenReturn(getMockObject_Success());
			mockMvc.perform(post("/api/objects").contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsBytes(getRequestObject()))).andDo(print())
			.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))

			// Check if data exists
			.andExpect(jsonPath("$.name").exists()).andExpect(jsonPath("$.uid").exists())
			.andExpect(jsonPath("$.errorMsg").doesNotExist())

			// check the data type
			.andExpect(jsonPath("$.name").isString()).andExpect(jsonPath("$.uid").isString())

			// check the values
			.andExpect(jsonPath("$.name").value("jaison"))
			.andExpect(jsonPath("$.uid").value("918eff12-e0a3-4d45-8191-35a4330fd601"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateObjectError() {
		try {
			mockMvc.perform(post("/api/objects").contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsBytes("Non JSON Obj"))).andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))

			// Check if data exists
			.andExpect(jsonPath("$.uid").doesNotExist()).andExpect(jsonPath("$.message").exists())
			.andExpect(jsonPath("$.path").exists())

			// check the data type
			.andExpect(jsonPath("$.message").isString()).andExpect(jsonPath("$.path").isString())

			// check the values
			.andExpect(jsonPath("$.message").value("Malformed JSON request"))
			.andExpect(jsonPath("$.path").value("uri=/api/objects"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetObjectByIDSuccess() {
		try {
			when(clientServiceMock.getObjectById("918eff12-e0a3-4d45-8191-35a4330fd601"))
			.thenReturn(getMockObject_Success());
			mockMvc.perform(get("/api/objects/918eff12-e0a3-4d45-8191-35a4330fd601")).andDo(print())
			.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))

			// Check if data exists
			.andExpect(jsonPath("$.name").exists()).andExpect(jsonPath("$.uid").exists())
			.andExpect(jsonPath("$.errorMsg").doesNotExist())

			// check the data type
			.andExpect(jsonPath("$.name").isString()).andExpect(jsonPath("$.uid").isString())

			// check the values
			.andExpect(jsonPath("$.name").value("jaison"))
			.andExpect(jsonPath("$.uid").value("918eff12-e0a3-4d45-8191-35a4330fd601"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetObjectByInvalidId() {
		try {
			when(clientServiceMock.getObjectById("918eff12")).thenThrow(ObjectNotFoundException.class);
			
			mockMvc.perform(get("/api/objects/918eff12")).andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetAllObjectsSuccess() {
		try {
			when(clientServiceMock.getObjects()).thenReturn(getObjects());
			mockMvc.perform(get("/api/objects")).andDo(print()).andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))

			// Check if data exists
			.andExpect(jsonPath("$", hasSize(2)));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateObjectByIDSuccess() {
		try {
			when(clientServiceMock.update("918eff12-e0a3-4d45-8191-35a4330fd601", getMockObject_update()))
			.thenReturn(getMockObject_update());
			mockMvc.perform(put("/api/objects/918eff12-e0a3-4d45-8191-35a4330fd601")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsBytes(getMockObject_update()))).andDo(print())
			.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))

			// Check if data exists
			.andExpect(jsonPath("$.org").exists()).andExpect(jsonPath("$.uid").exists())
			.andExpect(jsonPath("$.errorMsg").doesNotExist())

			// check the data type
			.andExpect(jsonPath("$.org").isString()).andExpect(jsonPath("$.uid").isString())

			// check the values
			.andExpect(jsonPath("$.org").value("cisco"))
			.andExpect(jsonPath("$.uid").value("918eff12-e0a3-4d45-8191-35a4330fd601"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateInvalidObjectId() {
		try {
			when(clientServiceMock.update("918eff12", getMockObject_update())).thenThrow(ObjectNotFoundException.class);
			mockMvc.perform(put("/api/objects/918eff12").contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(objectMapper.writeValueAsBytes(getMockObject_update()))).andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteObjectByIDSuccess() {
		try {

			Mockito.doNothing().when(clientServiceMock).deleteObjectById("918eff12-e0a3-4d45-8191-35a4330fd601");

			mockMvc.perform(delete("/api/objects/918eff12-e0a3-4d45-8191-35a4330fd601")).andDo(print())
			.andExpect(status().isOk());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDeletInvalidObjectId() {
		try {
			Mockito.doThrow(ObjectNotFoundException.class).when(clientServiceMock).deleteObjectById("918eff12");

			mockMvc.perform(delete("/api/objects/918eff12"))
			.andExpect(status().isNotFound()).andDo(print())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private HashMap<String, String> getMockObject_Success() {
		HashMap<String, String> objectCreateSuccess = new HashMap<String, String>();
		objectCreateSuccess.put("uid", "918eff12-e0a3-4d45-8191-35a4330fd601");
		objectCreateSuccess.put("name", "jaison");
		return objectCreateSuccess;
	}

	private HashMap<String, String> getMockObject_update() {
		HashMap<String, String> objectCreateSuccess = new HashMap<String, String>();
		objectCreateSuccess.put("uid", "918eff12-e0a3-4d45-8191-35a4330fd601");
		objectCreateSuccess.put("org", "cisco");
		return objectCreateSuccess;
	}

	private List<String> getObjects() {
		List<String> objectsList = new ArrayList<String>();
		String objUrl = getObjUrl("918eff12-e0a3-4d45-8191-35a4330fd601");
		objectsList.add(objUrl);
		objUrl = getObjUrl("068eff12-e0a3-4d45-8191-35a4330fd809");
		objectsList.add(objUrl);
		return objectsList;
	}

	private HashMap<String, String> getRequestObject() {
		HashMap<String, String> objectCreateSuccess = new HashMap<String, String>();
		objectCreateSuccess.put("name", "jaison");
		return objectCreateSuccess;
	}


	private ApiError getObject_Error() {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Malformed JSON request", "uri=/api/objects");
		return apiError;
	}

	private ApiError getObjectId_Error() {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "This object id does not exist", "uri=/api/objects");
		return apiError;
	}

	private String getObjUrl(String uuid) {
		String objUrl = "{\"url\":\"https://ciscorestdemo.herokuapp.com/api/objects/" + uuid + "}";
		System.out.println(objUrl);
		objUrl = objUrl.replace("\\", "");
		return objUrl;
	}

	private ApiError getHandleHttpMessageNotReadable() {
		String errorMessage = "Malformed JSON request";
		String path = "uri=api/objects";
		ApiError apierror = new ApiError(HttpStatus.BAD_REQUEST, errorMessage, path);
		return apierror;
	}

}
