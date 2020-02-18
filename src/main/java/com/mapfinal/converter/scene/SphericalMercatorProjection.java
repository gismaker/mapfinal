package com.mapfinal.converter.scene;

import com.mapfinal.geometry.Latlng;
import org.locationtech.jts.geom.Envelope;

public class SphericalMercatorProjection implements Projection {

	public static double R = 6378137, MAX_LATITUDE = 85.0511287798;
	public static double MAX_DIMENSION = R * Math.PI;
	
	@Override
	public ScenePoint project(Latlng latlng) {
		// TODO Auto-generated method stub
		double d = Math.PI / 180, max = MAX_LATITUDE, lat = Math.max(Math.min(max, latlng.lat()), -max),
				sin = Math.sin(lat * d);
		return new ScenePoint(R * latlng.lng() * d, R * Math.log((1 + sin) / (1 - sin)) / 2);
	}

	@Override
	public Latlng unproject(ScenePoint point) {
		// TODO Auto-generated method stub
		double d = 180 / Math.PI;
		return new Latlng((2 * Math.atan(Math.exp(point.y / R)) - (Math.PI / 2)) * d, point.x * d / R);
	}

	@Override
	public Envelope bounds() {
		// TODO Auto-generated method stub
		double d = R * Math.PI;
		return new Envelope(-d, d, -d, d);
	}
	
	public double getR() {
		return R;
	}

}
