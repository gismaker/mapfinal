package com.mapfinal.map.layer;

import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiLineString;

import com.mapfinal.geometry.GeomType;
import com.mapfinal.geometry.Latlng;

public class Polyline extends Path {
	
	private float smoothFactor = 1.0f;
	
	public Polyline(LineString line) {
		setGeometry(line);
		setGeoType(GeomType.LINESTRING);
	}
	
	public Polyline(MultiLineString MultiLine) {
		setGeometry(MultiLine);
		setGeoType(GeomType.MULTILINESTRING);
	}
	
	public float getSmoothFactor() {
		return smoothFactor;
	}

	public void setSmoothFactor(float smoothFactor) {
		this.smoothFactor = smoothFactor;
	}
}
