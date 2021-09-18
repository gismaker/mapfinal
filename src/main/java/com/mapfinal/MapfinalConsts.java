package com.mapfinal;

public interface MapfinalConsts {

	String version = "0.3.1";
	String author = "yangyong";
	
	String DEFAULT_ENCODING = "UTF-8";
	/**
	 * 初始化成功
	 */
	String MAP_STATUS_INITIALIZED= "map:status:initialized";
	/**
	 * 初始化失败
	 */
	String MAP_STATUS_INITIALIZATION_FAILED= "map:status:initialized";
	/**
	 * 图层加载成功
	 */
	String MAP_STATUS_LAYER_LOADED= "map:status:initialized";
	/**
	 * 图层加载失败
	 */
	String MAP_STATUS_LAYER_LOADING_FAILED= "map:status:initialized";
}
