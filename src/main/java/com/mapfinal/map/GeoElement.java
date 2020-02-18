package com.mapfinal.map;

import org.locationtech.jts.geom.Geometry;

public interface GeoElement {
	
	java.util.Map<String, Object> getAttributes();

	Geometry getGeometry();

	void setGeometry(Geometry geometry);
}
