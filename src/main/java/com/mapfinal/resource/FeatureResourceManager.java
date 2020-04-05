package com.mapfinal.resource;

import java.util.Map;

public abstract class FeatureResourceManager<K, V extends FeatureResource<K>, C extends FeatureCollection<K, V>> implements ResourceManager<K, V, C> {

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
	
	@Override
	public void remove(String collectionKey) {
		// TODO Auto-generated method stub
		this.collection.remove(collectionKey);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		this.collection.clear();
	}
}
