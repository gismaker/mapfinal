package com.mapfinal.map.layer;

import org.locationtech.jts.geom.Envelope;

import com.mapfinal.event.Event;
import com.mapfinal.map.AbstractLayer;
import com.mapfinal.render.RenderEngine;

public class WmsLayer extends AbstractLayer {

	@Override
	public Envelope getEnvelope() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		if(!event.isRender()) return;
		if(!isDrawable()) return;
		if(!isVisible()) return;
	}

	@Override
	public boolean handleEvent(Event event) {
		// TODO Auto-generated method stub
		return false;
	}

}
