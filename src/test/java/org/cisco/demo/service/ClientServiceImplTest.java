package org.cisco.demo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.cisco.demo.dao.ClientDao;
import org.cisco.demo.dao.ObjectStoreCacheImpl;
import org.cisco.demo.exceptions.ObjectNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ClientServiceImplTest {

	@TestConfiguration
	static class ClientServiceImplTestContextConfiguration {
		
		@Bean
		public ClientDao clientDao() {
			return new ClientDao(ObjectStoreCacheImpl.getInstance());
		}
		
		@Bean
		public ClientService clientService() {
			return new ClientServiceImpl();
		}

	}

	@MockBean
	ClientDao clientDao;
	
	@Autowired
	ClientService clientService;
	
	@Before
	public void setup() {
		when(clientDao.getObjStore()).thenReturn(getObjStore());
	}
	
	@Before
    public void resetMySingleton() throws SecurityException,
            NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {
        Field instance = ObjectStoreCacheImpl.class.getDeclaredField("objStore");
        instance.setAccessible(true);
    }

	@Test
	public void createObjectSuccess() {
		when(clientDao.updateStore(getRequestObject(),"918eff12-e0a3-4d45-8191-35a4330fd601")).thenReturn(getMockObject_Success());
		
		Map<String, String> clientObj = clientService.create(getRequestObject());
		
		assertEquals(clientObj.get("name"),"jaison");
	}
	
	@Test
	public void objectCreateCheckNegative() {
		when(clientDao.updateStore(getRequestObject(),"918eff12-e0a3-4d45-8191-35a4330fd601")).thenReturn(getMockObject_Success());
		
		Map<String, String> clientObj = clientService.create(getRequestObject());
		
		assertNotEquals(clientObj.get("name"),"cisco");
	}
	
	@Test
	public void testNullObjectIdForUpdate() {
		when(clientDao.getObjStore()).thenReturn(getObjStore());
		
		try{
			Map<String, String> clientObj = clientService.update(null, getMockUpdateObject());
		} catch (ObjectNotFoundException ex) {
			assertEquals(ex.getErrorMessage(),"This object id does not exist");
		}	
		
	}
	
	private HashMap<String, String> getRequestObject() {
		HashMap<String, String> objectCreateSuccess = new HashMap<String, String>();
		objectCreateSuccess.put("name", "jaison");
		return objectCreateSuccess;
	}
	
	private HashMap<String, String> getMockObject_Success() {
		HashMap<String, String> objectCreateSuccess = new HashMap<String, String>();
		objectCreateSuccess.put("uid", "918eff12-e0a3-4d45-8191-35a4330fd601");
		objectCreateSuccess.put("name", "jaison");
		return objectCreateSuccess;
	}
	
	private HashMap<String, String> getMockUpdateObject() {
		HashMap<String, String> objectCreateSuccess = new HashMap<String, String>();
		objectCreateSuccess.put("uid", "918eff12-e0a3-4d45-8191-35a4330fd601");
		objectCreateSuccess.put("org", "cisco");
		return objectCreateSuccess;
	}
	
	private Map<String,HashMap<String, String>> getObjStore() {
		Map<String,HashMap<String, String>> objStore = new HashMap<String,HashMap<String, String>>();
		HashMap<String, String> objectCreateSuccess = new HashMap<String, String>();
		objectCreateSuccess.put("uid", "918eff12-e0a3-4d45-8191-35a4330fd601");
		objectCreateSuccess.put("name", "jaison");
		objStore.put("918eff12-e0a3-4d45-8191-35a4330fd601", objectCreateSuccess);
		return objStore;
	}

}

