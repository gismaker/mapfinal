package com.mapfinal.resource;

import com.mapfinal.dispatcher.Dispatcher;
import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.map.Graphic;

/**
 * 资源调度器
 * @author yangyong
 *
 * @param <G>
 */
public interface ResourceDispatcher<G extends Graphic> {
	
	/**
	 * 资源名称
	 * @return
	 */
	String getName();
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
}
