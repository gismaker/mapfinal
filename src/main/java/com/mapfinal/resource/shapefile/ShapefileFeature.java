package com.mapfinal.resource.shapefile;

import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.map.Feature;
import org.locationtech.jts.geom.Geometry;

public class ShapefileFeature extends Feature {
	
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