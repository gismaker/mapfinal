package com.mapfinal.converter.scene;

public class SceneGcj02mCRS extends SceneEarthCRS {
	
	public SceneGcj02mCRS() {
		setCode("GCJ02_Mercator");
		Gcj02MercatorProjection smp = new Gcj02MercatorProjection();
		setProjection(smp);
		double scale = 0.5 / (Math.PI * smp.getR());
		setTransformation(new Transformation(scale, 0.5f, -scale, 0.5f));
	}
}
