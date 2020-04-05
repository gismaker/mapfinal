package com.mapfinal.resource.tile;

import java.util.Map;

import com.mapfinal.common.SyncWriteMap;
import com.mapfinal.resource.ResourceManager;

public class TileManager implements ResourceManager<String, TileResource, TileCollection> {

	private Map<String, TileCollection> collection = null;
	
	public TileManager() {
		collection = new SyncWriteMap<String, TileCollection>(32, 0.25F);
	}
	
	private static final TileManager me = new TileManager();
	
	public static TileManager me() {
		return me;
	}
	
	@Override
	public String getResourceType() {
		// TODO Auto-generated method stub
		return "tile";
	}

	@Override
	public long getMemorySize() {
		// TODO Auto-generated method stub
		long memorySize = 0;
		for (TileCollection tc : collection.values()) {
			memorySize += tc.getMemorySize();
		}
		return memorySize;
	}

	@Override
	public void addCollection(String key, TileCollection collection) {
		// TODO Auto-generated method stub
		this.collection.put(key, collection);
	}

	@Override
	public TileCollection getCollection(String key) {
		// TODO Auto-generated method stub
		return collection.get(key);
	}

	@Override
	public TileResource getResource(String collectionKey, String resourceKey) {
		// TODO Auto-generated method stub
		TileCollection c = getCollection(collectionKey);
		return c!=null ? c.get((String)resourceKey) : null;
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
