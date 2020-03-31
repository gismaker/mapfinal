package com.mapfinal.resource.shapefile;

import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.event.Event;
import com.mapfinal.resource.FeatureResource;

import org.locationtech.jts.geom.Geometry;

public class ShapefileFeature extends FeatureResource<Long> {
	
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

	@Override
	public void execute(Event event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void read() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writer() {
		// TODO Auto-generated method stub
		
	}
}