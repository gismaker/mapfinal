package com.mapfinal.resource;

import java.util.Map;

public abstract class FeatureResourceManager<V extends FeatureResource<K>, K, C extends FeatureCollection<V,K>> implements ResourceManager<V, K, C> {

	private Map<String, C> collection;
	
	@Override
	public long getMemorySize() {
		// TODO Auto-generated method stub
		long memorySize = 0;
		for (C tc : collection.values()) {
			memorySize += tc.getMemorySize();
		}
		return memorySize;
	}

	@Override
	public void addCollection(String key, C collection) {
		// TODO Auto-generated method stub
		this.collection.put(key, collection);
	}

	@Override
	public C getCollection(String key) {
		// TODO Auto-generated method stub
		return collection.get(key);
	}

	@Override
	public V getResource(String collectionKey, K resourceKey) {
		// TODO Auto-generated method stub
		C c = getCollection(collectionKey);
		return c!=null ? c.get(resourceKey) : null;
	}
	
}
