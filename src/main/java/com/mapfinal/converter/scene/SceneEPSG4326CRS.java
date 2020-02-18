package com.mapfinal.converter.scene;

public class SceneEPSG4326CRS extends SceneEarthCRS {
	
	public SceneEPSG4326CRS() {
		setCode("EPSG:4326");
		WGS84Projection smp = new WGS84Projection();
		setProjection(smp);
		//经度,纬度,对应屏幕坐标x=lng,y=lat
		setTransformation(new Transformation(1f / 180f, 1, -1f / 180f, 0.5f));
	}
}
