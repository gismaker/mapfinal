package com.mapfinal.map.layer;

import org.locationtech.jts.geom.Envelope;

import com.mapfinal.event.Event;
import com.mapfinal.map.AbstractLayer;
import com.mapfinal.render.RenderEngine;

/**
 * https://blog.csdn.net/qq_26222859/article/details/48677373
 * @author yangyong
 *
 */
public class GraphicsLayer extends AbstractLayer {

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
	public boolean handleEvent(Event event) {
		// TODO Auto-generated method stub
		
		return false;
	}

}
