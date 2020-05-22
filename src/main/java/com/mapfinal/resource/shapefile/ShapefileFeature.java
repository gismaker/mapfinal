package com.mapfinal.resource.shapefile;

import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.map.FeatureSio;

import org.locationtech.jts.geom.Geometry;

public class ShapefileFeature extends FeatureSio<Long> {
	
	private int shpType;
	
	public ShapefileFeature(String id, SpatialIndexObject obj, Geometry geometry, int shpType) {
		super(Long.valueOf(id), obj, geometry);
		this.setEnvelope(geometry.getEnvelopeInternal());
		this.shpType = shpType;
	}

	public int getShpType() {
		return shpType;
	}

	public void setShpType(int shpType) {
		this.shpType = shpType;
	}
}