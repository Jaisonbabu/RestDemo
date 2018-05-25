package org.cisco.demo.dao.injector;

import org.cisco.demo.dao.ObjectStoreCacheImpl;
import org.cisco.demo.dao.ClientDao;

public class ClientCacheDaoInjector implements ClientDaoInjector{

	@Override
	public ClientDao getObjectStoreImpl() {
		return new ClientDao(ObjectStoreCacheImpl.getInstance());
	}

}
