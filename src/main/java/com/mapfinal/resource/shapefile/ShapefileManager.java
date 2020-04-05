package com.mapfinal.resource.shapefile;

import java.util.Map;

import com.mapfinal.common.SyncWriteMap;

public class ShapefileManager {

	private ShapefileReaderFactory readerFactory;
	private Map<String, Shapefile> resources;
	
	public ShapefileManager() {
		this.setReaderFactory(new DefaultShapefileReaderFactory());
		resources = new SyncWriteMap<String, Shapefile>(32, 0.25F);
	}

	private static final ShapefileManager me = new ShapefileManager();
	
	public static ShapefileManager me() {
		return me;
	}
	
	public Shapefile create(String url) {
		Shapefile shp = getResource(url);
		if(shp==null) {
			shp = new Shapefile(url);
		}
		return shp;
	}
	
	public String getResourceType() {
		return "shp";
	}
	
	public long getMemorySize() {
		long memorySize = 0;
		for (Shapefile tc : resources.values()) {
			memorySize += tc.getMemorySize();
		}
		return memorySize;
	}

	public void addResource(String key, Shapefile resources) {
		this.resources.put(key, resources);
	}

	public Shapefile getResource(String key) {
		return resources.get(key);
	}
	
	public void remove(String key) {
		this.resources.remove(key);
	}

	public void clear() {
		this.resources.clear();
	}
	
	public ShapefileReaderFactory getReaderFactory() {
		return readerFactory;
	}

	public void setReaderFactory(ShapefileReaderFactory readerFactory) {
		this.readerFactory = readerFactory;
	}
}
