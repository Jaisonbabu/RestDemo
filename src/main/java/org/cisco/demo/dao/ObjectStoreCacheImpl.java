package org.cisco.demo.dao;

import java.util.HashMap;
import java.util.Map;

public class ObjectStoreCacheImpl implements ObjectStore {

	private Map<String, HashMap<String, String>> objStore = new HashMap<String, HashMap<String, String>>();

	private ObjectStoreCacheImpl() {
	}

	private static class LazyHolder {
		static final ObjectStoreCacheImpl INSTANCE = new ObjectStoreCacheImpl();
	}

	public static ObjectStoreCacheImpl getInstance() {
		return LazyHolder.INSTANCE;
	}

	@Override
	public HashMap<String, String> updateStore(HashMap<String, String> jsonObj, String uuid) {
		objStore.put(uuid, jsonObj);
		return jsonObj;
	}

	@Override
	public Map<String, HashMap<String, String>> getObjStore() {
		return objStore;
	}

	@Override
	public HashMap<String, String> getObjectById(String uuid) {
		return objStore.get(uuid);
	}

	@Override
	public void deleteObject(String uuid) {
		objStore.remove(uuid);
		
	}

}
