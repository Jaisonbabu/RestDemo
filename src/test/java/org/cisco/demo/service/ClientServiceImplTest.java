package org.cisco.demo.service;

import org.cisco.demo.controllers.ClientController;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(ClientController.class)
public class ClientServiceImplTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	ClientService clientServiceMock;

	@Autowired
	ObjectMapper objectMapper;
}
