package com.mapfinal.converter.scene;

public class SceneEPSG3857CRS extends SceneEarthCRS {
	
	public SceneEPSG3857CRS() {
		setCode("EPSG:3857");
		SphericalMercatorProjection smp = new SphericalMercatorProjection();
		setProjection(smp);
		double scale = 0.5 / (Math.PI * smp.getR());
		setTransformation(new Transformation(scale, 0.5f, -scale, 0.5f));
	}
}
