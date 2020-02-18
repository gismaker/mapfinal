package com.mapfinal.converter.scene;

import com.mapfinal.geometry.Latlng;
import org.locationtech.jts.geom.Envelope;

public class WGS84Projection implements Projection {

	@Override
	public ScenePoint project(Latlng latlng) {
		// TODO Auto-generated method stub
		return new ScenePoint(latlng.lng(), latlng.lat());
	}

	@Override
	public Latlng unproject(ScenePoint point) {
		// TODO Auto-generated method stub
		return new Latlng(point.y, point.x);
	}

	@Override
	public Envelope bounds() {
		// TODO Auto-generated method stub
		//经度,纬度,对应屏幕坐标x=lng,y=lat
		return new Envelope(-180, 180, -90, 90);
	}

}
