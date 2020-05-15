package com.mapfinal.geometry;

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
}
