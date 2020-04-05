package com.mapfinal.resource;

import com.mapfinal.MapfinalObject;

/**
 * 资源，负责预加载和加载，数据内容
 * @author yangyong
 *
 */
public interface Resource<D extends Data> extends MapfinalObject {

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
		database,
		other
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
	 * 资源名称
	 */
	public String getName();
	/**
	 * 资源地址，文件路径、网络地址、内存数据主键
	 * @return
	 */
	public String getUrl();
	/**
	 * 资源类型
	 * @return
	 */
	public String getType();
	
	/**
     * 内存占用
     * @return
     */
    long getMemorySize();
    
	/**
	 * 预加载
	 */
	void prepare();
	
	/**
	 * 加载资源
	 */
	D read();
	
	/**
	 * 写入资源
	 */
	void writer(D data);
}
