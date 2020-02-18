package com.mapfinal.converter.scene;

import com.mapfinal.geometry.Latlng;
import org.locationtech.jts.geom.Envelope;

public interface Projection {

	/**
	 * 经纬度转投影坐标
	 * @param latlng
	 * @return
	 */
	ScenePoint project(Latlng latlng);
	
	/**
	 * 投影坐标转经纬度
	 * @param point
	 * @return
	 */
	Latlng unproject(ScenePoint point);
	
	Envelope bounds();
}
