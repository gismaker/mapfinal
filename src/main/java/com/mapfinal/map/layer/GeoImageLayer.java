package com.mapfinal.map.layer;

import org.locationtech.jts.geom.Envelope;

import com.mapfinal.event.Event;
import com.mapfinal.map.GeoImage;
import com.mapfinal.render.RenderEngine;

/**
 * 图片
 * @author yangyong
 *
 */
public class GeoImageLayer extends AbstractLayer {

	private GeoImage featuer;
	
	@Override
	public Envelope getEnvelope() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onRender(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEvent(Event event) {
		// TODO Auto-generated method stub
		
	}

	public GeoImage getFeatuer() {
		return featuer;
	}

	public void setFeatuer(GeoImage featuer) {
		this.featuer = featuer;
	}

}
