package com.mapfinal.resource;

import java.util.List;
import java.util.Map;

import com.mapfinal.converter.CRS;
import com.mapfinal.dispatcher.Dispatcher;
import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.map.GeoElement;

public interface ResourceReader {

	/**
	 * 连接初始化
	 * @return
	 */
	Dispatcher connection();
	/**
	 * 按索引读取
	 * @param objs
	 * @return
	 */
	Map<String, GeoElement> read(List<SpatialIndexObject> sios);
	/**
	 * 按索引读取
	 * @param obj
	 * @return
	 */
	GeoElement read(SpatialIndexObject sio);
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
