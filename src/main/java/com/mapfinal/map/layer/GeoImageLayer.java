package com.mapfinal.map.layer;

import org.locationtech.jts.geom.Envelope;

import com.mapfinal.event.Event;
import com.mapfinal.map.AbstractLayer;
import com.mapfinal.map.GeoImage;
import com.mapfinal.render.RenderEngine;

/**
 * 图片
 * @author yangyong
 *
 */
public class GeoImageLayer extends AbstractLayer {

	private GeoImage feature;
	
	@Override
	public Envelope getEnvelope() {
		// TODO Auto-generated method stub
		return feature.getEnvelope();
	}

	@Override
	public void draw(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		if(feature==null) return;
		engine.renderImageFeature(getRenderer(), getMapContext(event), feature);
	}

	public GeoImage getFeatuer() {
		return feature;
	}

	public void setFeatuer(GeoImage feature) {
		this.feature = feature;
	}

}
