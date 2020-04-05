package com.mapfinal.resource.image;

import java.util.HashMap;
import java.util.Map;

import com.mapfinal.Mapfinal;
import com.mapfinal.resource.Resource;
import com.mapfinal.resource.ResourceManager;

/**
 * 图片管理器
 * @author yangyong
 * @param 
 */
public class ImageManager implements ResourceManager<String, Image, ImageCollection> {

	private Map<String, ImageCollection> collection;
	private ImageHandle handle;
	private String cacheFolder = "image";
	//级别， 0 内存，1 本地，2 网络
	//private int level = 0;
	
	private static final ImageManager me = new ImageManager();
	
	public ImageManager() {
		collection = new HashMap<String, ImageCollection>();
		handle = Mapfinal.me().getFactory().getImageHandle();
	}
	
	public static ImageManager me() {
		return me;
	}
	
	@Override
	public String getResourceType() {
		// TODO Auto-generated method stub
		return "image";
	}
	
	@Override
	public long getMemorySize() {
		// TODO Auto-generated method stub
		long memorySize = 0;
		for (ImageCollection tc : collection.values()) {
			memorySize += tc.getMemorySize();
		}
		return memorySize;
	}
	

	public String getCacheFolder() {
		return cacheFolder;
	}

	public void setCacheFolder(String cacheFolder) {
		this.cacheFolder = cacheFolder;
	}

	public ImageHandle getHandle() {
		return handle;
	}

	public void setHandle(ImageHandle handle) {
		this.handle = handle;
	}

	public Map<String, ImageCollection> getCollection() {
		return collection;
	}

	public void setCollection(Map<String, ImageCollection> collection) {
		this.collection = collection;
	}

	@Override
	public Image getResource(String collectionKey, String resourceKey) {
		// TODO Auto-generated method stub
		ImageCollection c = getCollection(collectionKey);
		return c!=null ? c.get((String)resourceKey) : null;
	}

	@Override
	public void addCollection(String key, ImageCollection collection) {
		// TODO Auto-generated method stub
		this.collection.put(key, collection);
	}

	@Override
	public ImageCollection getCollection(String key) {
		// TODO Auto-generated method stub
		return this.collection.get(key);
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
