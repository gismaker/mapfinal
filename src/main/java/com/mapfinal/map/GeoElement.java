package com.mapfinal.map;

import org.locationtech.jts.geom.Geometry;

import com.mapfinal.MapfinalObject;

public interface GeoElement extends MapfinalObject {
	
	java.util.Map<String, Object> getAttributes();

	Geometry getGeometry();

	void setGeometry(Geometry geometry);
}
