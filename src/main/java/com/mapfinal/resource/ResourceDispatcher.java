package com.mapfinal.resource;

import com.mapfinal.converter.CRS;
import com.mapfinal.dispatcher.Dispatcher;
import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.map.GeoElement;

public interface ResourceDispatcher<G extends GeoElement> {
	
	/**
	 * 资源名称
	 * @return
	 */
	String getName();
	
	/**
	 * 当前内存中的Feature
	 * @param sio
	 * @return
	 */
	//G current(SpatialIndexObject sio);
	
	/**
	 * 连接初始化
	 * @return
	 */
	Dispatcher connection();
	/**
	 * 按索引读取
	 * @param obj
	 * @return
	 */
	G read(SpatialIndexObject sio);
	/**
	 * 关闭连接
	 */
	void close();
	
	/**
	 * 获取坐标系
	 * @return
	 */
	CRS getCRS();
}
