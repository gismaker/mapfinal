package com.mapfinal.map;

import org.locationtech.jts.geom.Geometry;

import com.mapfinal.MapfinalObject;

/**
 * Graphic类用于表示一个地物，可以包含地物的几何信息、属性信息。
 * @author Henry Yang 杨勇 (gismail@foxmail.com)
 * @version 1.0
 * @Package com.mapfinal.map
 */
public interface Graphic extends MapfinalObject {
	
	java.util.Map<String, Object> getAttributes();

	Geometry getGeometry();

	void setGeometry(Geometry geometry);
}
