package com.mapfinal.converter.scene;

import com.mapfinal.geometry.Latlng;

public abstract class SceneEarthCRS extends SceneCRS {

	private double R = 6371000;

	// distance between two geographical points using spherical law of cosines
	// approximation
	public double distance(Latlng latlng1, Latlng latlng2) {
		double rad = Math.PI / 180;
		double lat1 = latlng1.lat() * rad;
		double lat2 = latlng2.lat() * rad;
		double a = Math.sin(lat1) * Math.sin(lat2)
				+ Math.cos(lat1) * Math.cos(lat2) * Math.cos((latlng2.lng() - latlng1.lng()) * rad);
		return this.R * Math.acos(Math.min(a, 1));
	}
}
