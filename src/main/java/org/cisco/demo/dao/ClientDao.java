package org.cisco.demo.dao;

import java.util.HashMap;
import java.util.Map;

public class ClientDao {
	
	private ObjectStore objectCache;
	
	public ClientDao(ObjectStore objectCache) {
		this.objectCache = objectCache;
	}
	
	public HashMap<String, String> updateStore(HashMap<String, String> jsonObj, String uuid) {
		return objectCache.updateStore(jsonObj, uuid);
	}

	public Map<String, HashMap<String, String>> getObjStore() {
		return objectCache.getObjStore();
	}
	
	public HashMap<String, String> getObjectById(String uuid) {
		return objectCache.getObjectById(uuid);
	}

	public void deleteObject(String uuid) {
		objectCache.deleteObject(uuid);
		
	}
}
