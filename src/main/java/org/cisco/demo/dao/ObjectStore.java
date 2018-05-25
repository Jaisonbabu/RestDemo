package org.cisco.demo.dao;

import java.util.HashMap;
import java.util.Map;

public interface ObjectStore {

	public HashMap<String, String> updateStore(HashMap<String, String> jsonObj, String uuid);

	public Map<String, HashMap<String, String>> getObjStore();
	
	public HashMap<String, String> getObjectById(String uuid);
	
	public void deleteObject(String uuid);

}
