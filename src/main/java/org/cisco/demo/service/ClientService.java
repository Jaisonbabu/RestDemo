package org.cisco.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cisco.demo.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Component;

@Component
public interface ClientService {

	public Map<String, String> create(HashMap<String, String> jsonObj);

	public List<String> getObjects();

	public Map<String, String> update(String objectId, HashMap<String, String> jsonObj) throws ObjectNotFoundException;

	public Map<String, String> getObjectById(String objectId) throws ObjectNotFoundException;

	public void deleteObjectById(String objectId) throws ObjectNotFoundException;
}
