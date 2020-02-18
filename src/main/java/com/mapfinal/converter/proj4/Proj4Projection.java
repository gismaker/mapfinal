package com.mapfinal.converter.proj4;

import org.osgeo.proj4j.ProjCoordinate;

import com.mapfinal.converter.scene.Projection;
import com.mapfinal.converter.scene.ScenePoint;
import com.mapfinal.geometry.Latlng;
import org.locationtech.jts.geom.Envelope;

public class Proj4Projection implements Projection {

	private org.osgeo.proj4j.proj.Projection proj;
	
	public Proj4Projection(org.osgeo.proj4j.proj.Projection proj) {
		this.proj = proj;
	}
	
	@Override
	public ScenePoint project(Latlng latlng) {
		// TODO Auto-generated method stub
		ProjCoordinate src = new ProjCoordinate(latlng.lat(), latlng.lng());
		ProjCoordinate dst = new ProjCoordinate();
		dst = proj.project(src, dst);
		return new ScenePoint(dst.x, dst.y);
	}

	@Override
	public Latlng unproject(ScenePoint point) {
		// TODO Auto-generated method stub
		ProjCoordinate src = new ProjCoordinate(point.x, point.y);
		ProjCoordinate dst = new ProjCoordinate();
		dst = proj.inverseProject(src, dst);
		return new Latlng(dst.x, dst.y);
	}

	@Override
	public Envelope bounds() {
		// TODO Auto-generated method stub
		double d = proj.getEllipsoid().getEquatorRadius() * Math.PI;
		return new Envelope(-d, d, -d, d);
	}

}
