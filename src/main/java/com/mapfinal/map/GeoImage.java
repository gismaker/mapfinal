package com.mapfinal.map;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;

public interface GeoImage<M> extends Graphic {
	
	public M getImage(); 

	public Envelope getEnvelope();
	/**
	 * 左上角
	 * 
	 * @return
	 */
	public Coordinate getTopLeft();

	/**
	 * 左下角
	 * 
	 * @return
	 */
	public Coordinate getBottomLeft();

	/**
	 * 右上角
	 * 
	 * @return
	 */
	public Coordinate getTopRight();

	/**
	 * 右下角
	 * 
	 * @return
	 */
	public Coordinate getBottomRight();

}
