package com.mapfinal.resource.image;

import java.util.List;

import com.mapfinal.cache.Cache;
import com.mapfinal.cache.impl.LruCacheImpl;
import com.mapfinal.resource.ResourceCollection;

/**
 * 图片组
 * @author yangyong
 * @param <M>
 */
public class ImageCollection implements ResourceCollection<Image, String> {

	//名称
	private String name = "default";
	//文件路径 或 网络地址，唯一键
	private String url;
	//ResourceObject被调用次数
	private int reference = 0;
	private Cache<String, Image> imageCache;
	//级别， 0 内存，1 本地，2 网络
	//private int level = 0;
	
	public ImageCollection(String name) {
		this.name = name;
		imageCache = new LruCacheImpl<>(100);
	}
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "image";
	}

	@Override
	public long getMemorySize() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * 
	 * @param url=ResourceObject.url
	 * @return
	 */
	@Override
	public Image get(String url) {
		// TODO Auto-generated method stub
		return imageCache.get(url);
	}
	
	@Override
	public List<String> keys() {
		// TODO Auto-generated method stub
		return imageCache.keys();
	}

	@Override
	public void put(String url, Image value) {
		// TODO Auto-generated method stub
		imageCache.put(url, value);
		value.setCollection(this);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		imageCache.clear();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return imageCache.size();
	}

	@Override
	public boolean remove(String url) {
		// TODO Auto-generated method stub
		return imageCache.remove(url);
	}

	public Cache<String, Image> getCache() {
		return imageCache;
	}

	public void setCache(Cache<String, Image> imageCache) {
		this.imageCache = imageCache;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getReference() {
		return reference;
	}

	public void setReference(int reference) {
		this.reference = reference;
	}
}
