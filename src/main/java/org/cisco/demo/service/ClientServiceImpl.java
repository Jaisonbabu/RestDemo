package org.cisco.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cisco.demo.dao.ClientDao;
import org.cisco.demo.dao.injector.ClientCacheDaoInjector;
import org.cisco.demo.dao.injector.ClientDaoInjector;
import org.cisco.demo.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

	private static final Logger logger = LogManager.getLogger(ClientServiceImpl.class);

	private ClientDaoInjector clientDaoInjector = new ClientCacheDaoInjector();
	private ClientDao clientDao = clientDaoInjector.getObjectStoreImpl();

	@Override
	public Map<String, String> create(HashMap<String, String> jsonObj) {
		logger.debug("ClientServiceImpl : create");
		
		final String uuid = UUID.randomUUID().toString();
		jsonObj.put("uid", uuid);
		HashMap<String, String> newJsonObj = clientDao.updateStore(jsonObj,uuid);
		return newJsonObj;
	}

	@Override
	public List<String> getObjects() {
		logger.debug("ClientServiceImpl : getObjects");
		Map<String,HashMap<String, String>> objStore = clientDao.getObjStore();
		List<String> objectsList = new ArrayList<String>();
		for(Map.Entry<String,HashMap<String, String>> obj : objStore.entrySet()) {
			String objUrl = getObjUrl(obj.getKey());
			objectsList.add(objUrl);
		}
		return objectsList;
	}

	@Override
	public Map<String, String> update(String uuid, HashMap<String, String> jsonObj) throws ObjectNotFoundException {
		logger.debug("ClientServiceImpl : update");
		Map<String,HashMap<String, String>> objStore = clientDao.getObjStore();
			if (uuid != null && !objStore.containsKey(uuid)) {
				throw new ObjectNotFoundException("This object id does not exist");
			}
			HashMap<String, String> newJsonObj = new HashMap<String, String>();
			newJsonObj = clientDao.updateStore(jsonObj,uuid);
			return newJsonObj;
	}

	@Override
	public Map<String, String> getObjectById(String objectId) throws ObjectNotFoundException {
		logger.debug("ClientServiceImpl : getObjectById");
		Map<String,HashMap<String, String>> objStore = clientDao.getObjStore();
		if (objectId != null && !objStore.containsKey(objectId)) {
			throw new ObjectNotFoundException("This object id does not exist");
		}
		return clientDao.getObjectById(objectId);
	}

	@Override
	public void deleteObjectById(String objectId) throws ObjectNotFoundException {
		logger.debug("ClientServiceImpl : deleteObjectById");
		Map<String,HashMap<String, String>> objStore = clientDao.getObjStore();
		if (objectId != null && !objStore.containsKey(objectId)) {
			throw new ObjectNotFoundException("This object id does not exist");
		}
		clientDao.deleteObject(objectId);
	}

	private String getObjUrl( String uuid) {
		String objUrl = "{\"url\":\"https://ciscorestdemo.herokuapp.com/api/objects/"+uuid+"}";
		System.out.println(objUrl);
		objUrl = objUrl.replace("\\", "");
		return objUrl;
	}

}
