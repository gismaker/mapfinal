package com.mapfinal.resource;

public class Resource {

	public enum Type {
		shp,
		image,
		tile,
		wms,
		postgis,
		wfs,
		geojson
	}
	
	public enum FileType {
		http,
		file,
		cache,
		database
	}
	
	public enum ImageType {
		png,
		jpg,
		bmp
	}
	
	/**
	 * 缓存策略
	 * @author yangyong
	 *
	 */
	public enum CacheType {
		/**
		 * 最近最少使用
		 */
		LRU,
		/**
		 * 先进先出
		 */
		FIFO,
		/**
		 * 较少使用
		 */
		LFU,
		/**
		 * 场景控制（根据当前场景判断）
		 */
		SCENE
	}
}
