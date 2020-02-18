package com.mapfinal.resource.tile;

import com.mapfinal.Mapfinal;
import com.mapfinal.map.ImageFeature;
import com.mapfinal.resource.ImageCache;
import com.mapfinal.resource.ImageCacheService;
import com.mapfinal.resource.Resource;
import com.mapfinal.resource.ResourceMapCache;

public class TileCache extends ResourceMapCache<String, ImageFeature> {

	private ImageCache imageCache;
	
	public TileCache(TileResourceObject tro, Resource.CacheType cacheType) {
		ImageCacheService service = Mapfinal.me().getFactory().createImageCacheService(cacheType, 20);
		imageCache = new ImageCache(service, tro.getCacheFolder(), tro.getImageType());
	}
	
	public Object getImage(String featureId) {
		ImageFeature f = get(featureId);
		return imageCache.get(f.getUrl());
	}

	public ImageCache getImageCache() {
		return imageCache;
	}

	public void setImageCache(ImageCache imageCache) {
		this.imageCache = imageCache;
	}

	public String print() {
		// TODO Auto-generated method stub
		return "Cache: " + size() + ", imageCache: " + imageCache.size();
	}
	
}
