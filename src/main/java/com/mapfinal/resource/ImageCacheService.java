package com.mapfinal.resource;

import java.util.List;

import com.mapfinal.event.Callback;

/**
 * 图片缓存策略  - 三级缓存
 * <br>网络缓存, 不优先加载, 速度慢,浪费流量
 * <br>本地缓存, 次优先加载, 速度快
 * <br>内存缓存, 优先加载, 速度最快（包括强引用（LruCache）和软引用（SoftReference））
 * <br>https://blog.csdn.net/u011916937/article/details/84955151
 * @author yangyong
 *
 */
public interface ImageCacheService {

	/**
	 * Clears the cache.
	 */
	public void clear();

	public int size();
	
	/**
	 * 缓存加载
	 * @param url
	 * @return
	 */
	public abstract Object readFromCache(String url);
	/**
	 * 缓存写入
	 * @param url
	 * @param image
	 */
	public abstract void writerToCache(String url, Object image);
	/**
	 * 本地加载
	 * @param url
	 * @return
	 */
	public Object readFromLocal(String url, String cachePath, Resource.ImageType type);
	/**
	 * 本地写入
	 * @param fileName
	 * @param image
	 */
	public void writerToLocal(String fileName, Object image);
	
	/**
	 * 网络加载
	 * @param url
	 * @return
	 */
	public void downloadImage(String url, Callback callback);
	
	/**
	 * 网络上传
	 * @param url
	 */
	public void uploadImage(String url);
	
	/**
	 * 编码文件名
	 * @param url
	 * @return
	 */
	public String encodeName(String url);
	
	/**
	 * 删除缓存
	 * @param url
	 * @return
	 */
	public boolean remove(String url);
	
	/**
	 * 刷新缓存
	 */
	public void refresh();
	
	/**
	 * 遍历
	 * @return
	 */
	public List<String> getKeys();
}
