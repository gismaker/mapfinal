package com.mapfinal.converter;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;

/**
 * wgs84采用(经度, 纬度)
 */
public interface Converter {
	
	CRS getSourceCRS();
	CRS getTargetCRS();
	
	public default void transform(Geometry geometry) {
		ConverterCoordinateFilter filter = new ConverterCoordinateFilter(this);
		geometry.apply(filter);
	}
	
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
