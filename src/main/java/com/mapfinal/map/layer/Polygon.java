package com.mapfinal.map.layer;

import org.locationtech.jts.geom.Envelope;

import com.mapfinal.event.Event;
import com.mapfinal.geometry.Latlng;
import com.mapfinal.render.RenderEngine;

public class Polygon extends Path {

	@Override
	public Envelope getEnvelope() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isEmpyt() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Latlng point) {
		// TODO Auto-generated method stub
		return false;
	}

}
