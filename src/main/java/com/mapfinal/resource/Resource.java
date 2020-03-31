package com.mapfinal.resource;

import com.mapfinal.event.Event;

/**
 * 资源，负责预加载和加载，数据内容
 * @author yangyong
 *
 */
public interface Resource {

	public enum Type {
		shp,
		image,
		tile,
		wms,
		postgis,
		wfs,
		geojson,
		bundle,
		unknown
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
		bmp,
		other
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
	
	/**
	 * 资源执行某一项事件或任务
	 * @param event
	 */
	public void execute(Event event);
	
	/**
	 * 调用次数
	 * @return
	 */
	public int getReference();
	
	/**
	 * 预加载
	 */
	public void prepare();
	
	/**
	 * 加载资源
	 */
	public void read();
	
	/**
	 * 写入资源
	 */
	public void writer();
}
