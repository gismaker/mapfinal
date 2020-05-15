package com.mapfinal.geometry;

import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;

public interface Geom {

	/**
	 * geom类型
	 * @return
	 */
	GeomType getGeomType();
	/**
	 * 子geom个数
	 * @return
	 */
	int getSize();
	
	/**
	 * 坐标点个数
	 * @return
	 */
	int getCoordinateSize();
	
	/**
	 * 中心点
	 * @return
	 */
	GeoPoint getCentroid();
	
	/**
	 * 内点
	 * @return
	 */
	GeoPoint getInteriorPoint();
	
	/**
	 * 是否为空
	 * @return
	 */
	boolean	isEmpty();
	
	/**
	 * 包围盒
	 * @return
	 */
	Envelope getEnvelope();
	
	/**
	 * 转换到Geometry
	 * @return
	 */
	Geometry toGeometry();
	
	//boolean contains(Point createPoint);
	
}
