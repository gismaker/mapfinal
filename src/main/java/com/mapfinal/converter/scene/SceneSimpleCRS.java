package com.mapfinal.converter.scene;

import org.locationtech.jts.geom.Coordinate;

public class SceneSimpleCRS extends SceneEarthCRS {
	
	public SceneSimpleCRS() {
		setCode("simple");
		WGS84Projection smp = new WGS84Projection();
		setProjection(smp);
		setTransformation(new Transformation(1, 0, -1, 0));
		setInfinite(true);
	}
	
	public double scale(double zoom) {
		return Math.pow(2, zoom);
	}

	public double zoom(double scale) {
		return Math.log(scale) / Math.log(2);
	}

	public double distance(Coordinate latlng1, Coordinate latlng2) {
		double dx = latlng2.y - latlng1.y,
		    dy = latlng2.x - latlng1.x;

		return Math.sqrt(dx * dx + dy * dy);
	}
}
