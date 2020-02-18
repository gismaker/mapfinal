package com.mapfinal.converter;

import org.locationtech.jts.geom.Coordinate;

/**
 * wgs84采用(经度, 纬度)
 */
public interface Converter {
	
	CRS getSourceCRS();
	CRS getTargetCRS();
	
	/**
	 * wgs84采用(经度, 纬度)
	 * @param src
	 * @param tgt
	 * @return
	 */
	Coordinate transform(Coordinate src, Coordinate tgt);
	
	/**
	 * wgs84采用(经度, 纬度)
	 * @param src
	 * @param tgt
	 * @return
	 */
	Coordinate transform(Coordinate coordinate);
	
	Coordinate transform(double x, double y, double z);
}
