package com.mapfinal.map;

import org.locationtech.jts.geom.Geometry;

import com.mapfinal.dispatcher.SpatialIndexObject;

public class FeatureSio<K> extends Feature<K> {

	/**
	 * 索引对象
	 */
	private SpatialIndexObject spatialIndexObject;
	
	public FeatureSio() {
	}
	
	public FeatureSio(K id, SpatialIndexObject spatialIndexObject, Geometry geometry) {
		super(id, geometry);
		this.spatialIndexObject = spatialIndexObject;
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		spatialIndexObject = null;
	}
	
	/**
	 * 空间索引对象
	 * 
	 * @return
	 */
	public SpatialIndexObject getSpatialIndexObject() {
		return spatialIndexObject;
	}

	public void setSpatialIndexObject(SpatialIndexObject spatialIndexObject) {
		this.spatialIndexObject = spatialIndexObject;
	}

}
