package org.cisco.demo.dao.injector;

import org.cisco.demo.dao.ClientDao;

public interface ClientDaoInjector {
	
	public ClientDao getObjectStoreImpl();

}
