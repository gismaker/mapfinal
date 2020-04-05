package com.mapfinal.resource.image;

import com.mapfinal.cache.Cache;
import com.mapfinal.resource.DataSet;

/**
 * 图集
 * @author yangyong
 *
 */
public class ImageSet implements DataSet {

	/**
	 * 名称
	 */
	private String name = "default";
	/**
	 * 缓存
	 */
	private Cache<String, Image> imageCache;
	
	public ImageSet(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
		//imageCache = new LruCacheImpl<>(100);
	}
	
	public void writer(ImageHandle handle) {
		// TODO Auto-generated method stub
		if(imageCache!=null) {
			//handle.writeFile(getUrl(), );
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public Cache<String, Image> getImageCache() {
		return imageCache;
	}

	public void setImageCache(Cache<String, Image> imageCache) {
		this.imageCache = imageCache;
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		if(imageCache!=null) imageCache.clear();
		imageCache = null;
	}


}
