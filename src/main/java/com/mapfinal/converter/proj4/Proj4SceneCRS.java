package com.mapfinal.converter.proj4;

import org.osgeo.proj4j.CoordinateReferenceSystem;

import com.mapfinal.converter.scene.SceneEarthCRS;
import com.mapfinal.converter.scene.Transformation;

public class Proj4SceneCRS extends SceneEarthCRS {

	private CoordinateReferenceSystem crs;
	
	public Proj4SceneCRS(CoordinateReferenceSystem crs) {
		this.setCrs(crs);
		setCode(crs.getName());
		setProjection(new Proj4Projection(crs.getProjection()));
		double scale = 0.5 / (Math.PI * crs.getProjection().getEllipsoid().getEquatorRadius());
		setTransformation(new Transformation(scale, 0.5f, -scale, 0.5f));
		//setInfinite(true);
	}

	public CoordinateReferenceSystem getCrs() {
		return crs;
	}

	public void setCrs(CoordinateReferenceSystem crs) {
		this.crs = crs;
	}

}
