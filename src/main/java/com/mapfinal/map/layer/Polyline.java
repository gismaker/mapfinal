package com.mapfinal.map.layer;

import org.locationtech.jts.geom.Envelope;

import com.mapfinal.event.Event;
import com.mapfinal.geometry.GeoLineString;
import com.mapfinal.geometry.GeoMultiLineString;
import com.mapfinal.geometry.GeomType;
import com.mapfinal.geometry.Latlng;
import com.mapfinal.render.RenderEngine;

public class Polyline extends Path {
	
	protected Envelope envelope;
	protected GeoMultiLineString geometry;
	protected float smoothFactor = 1.0f;
	
	public Polyline(GeoLineString line) {
		this.geometry = new GeoMultiLineString(line);
		setGeomType(GeomType.LINESTRING);
		envelope = this.geometry.getEnvelope();
	}
	
	public Polyline(GeoMultiLineString multiLine) {
		this.geometry = multiLine;
		setGeomType(GeomType.MULTILINESTRING);
		envelope = this.geometry.getEnvelope();
	}
	
	public float getSmoothFactor() {
		return smoothFactor;
	}

	public void setSmoothFactor(float smoothFactor) {
		this.smoothFactor = smoothFactor;
	}

	@Override
	public Envelope getEnvelope() {
		// TODO Auto-generated method stub
		return envelope;
	}

	@Override
	public void draw(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEmpyt() {
		// TODO Auto-generated method stub
		return geometry.isEmpty();
	}

	@Override
	public boolean contains(Latlng point) {
		// TODO Auto-generated method stub
		return false;
	}
}
