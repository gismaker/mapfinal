package com.mapfinal.resource;

import java.util.Map;

import com.mapfinal.Mapfinal;
import com.mapfinal.event.Event;
import com.mapfinal.kit.SyncWriteMap;

public class ResourceManager {

	private static final ResourceManager me = new ResourceManager();
	private Map<String, ResourceObject> resources = new SyncWriteMap<String, ResourceObject>(32, 0.25F);
	private int readerThreadMaxNumber = 5;
	private ImageCache imageCache;

	private ResourceManager(){
		ImageCacheService service = Mapfinal.me().getFactory().createImageCacheService(Resource.CacheType.LRU, 10);
		setImageCache(new ImageCache(service, "common", Resource.ImageType.jpg));
	}
	
	public static ResourceManager me() {
		return me;
	}
	
	public void renderBefore(Event event) {
		for (ResourceObject ro : resources.values()) {
			ro.renderBefore(event);
		}
	}
	
	public void renderEnd(Event event) {
		for (ResourceObject ro : resources.values()) {
			ro.renderEnd(event);
		}
	}
	
	public void putResource(ResourceObject resource) {
		resources.put(resource.getName(), resource);
	}
	
	public ResourceObject getResource(String resourceName) {
		return resources.get(resourceName);
	}
	
	public Map<String, ResourceObject> getResources() {
		return resources;
	}

	public void setResources(Map<String, ResourceObject> resources) {
		this.resources = resources;
	}

	public int getReaderThreadMaxNumber() {
		return readerThreadMaxNumber;
	}

	public void setReaderThreadMaxNumber(int readerThreadMaxNumber) {
		this.readerThreadMaxNumber = readerThreadMaxNumber;
	}

	public ImageCache getImageCache() {
		return imageCache;
	}

	public void setImageCache(ImageCache imageCache) {
		this.imageCache = imageCache;
	}
}
