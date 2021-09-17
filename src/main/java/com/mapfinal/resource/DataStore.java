package com.mapfinal.resource;

import com.mapfinal.MapfinalObject;

/**
 * 数据源
 * @author yangyong
 *
 */
public interface DataStore extends MapfinalObject {

	/**
	 * 数据源ID
	 * @return
	 */
	String getId();
	
	/**
	 * 数据源名称
	 * @return
	 */
	String getName();
	
	/**
	 * 数据源地址
	 * @return
	 */
	String getUrl();
	
	/**
	 * 数据源类型
	 * @return
	 */
	DataStoreType getType();
	
	/**
	 * 连接
	 */
	void start();
	
	/**
	 * 断开
	 */
	void stop();
	
	/**
	 * 获取资源
	 * @param name
	 * @return
	 */
	Resource getResource(String name);
}
