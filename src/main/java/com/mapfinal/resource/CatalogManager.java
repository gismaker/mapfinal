package com.mapfinal.resource;

import java.util.HashMap;
import java.util.Map;

public class CatalogManager {

	private static CatalogManager manage;
	
	public static CatalogManager me() {
		if(manage==null) {
			manage = new CatalogManager();
		}
		return manage;
	}
	
	//目录树
	private Map<String, DataStore> dataStoreMap;
	
	public CatalogManager() {
		dataStoreMap = new HashMap<String, DataStore>();
	}
	
	public Map<String, DataStore> getDataStoreMap() {
		return dataStoreMap;
	}

	public void put(String key, DataStore dataSource) {
		dataStoreMap.put(key, dataSource);
	}
	
	public DataStore get(String key) {
		return dataStoreMap.get(key);
	}
}
