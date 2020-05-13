package com.mapfinal.map.layer;

import org.locationtech.jts.geom.Envelope;

import com.mapfinal.event.Event;
import com.mapfinal.geometry.Latlng;
import com.mapfinal.map.AbstractLayer;
import com.mapfinal.render.RenderEngine;
import com.mapfinal.resource.image.Image;

/**
 * Marker
 * @author Henry Yang 杨勇 (gismail@foxmail.com)
 * @version 1.0
 * @Package com.mapfinal.map.layer
 */
public class Marker extends AbstractLayer {

	private Latlng center;
	private Image image;
	private float scale = 1;
	
	public Marker(Latlng latlng, Image image) {
		this.setCenter(latlng);
		this.setImage(image);
	}
	
	@Override
	public Envelope getEnvelope() {
		// TODO Auto-generated method stub
		return new Envelope(center);
	}

	@Override
	public void draw(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		if(image==null || center==null) return;
		if(image.getData()==null) {
			image.read();
		}
		if(image.getData()==null) return;
		event.set("image:scale", scale);
		if(isVisible()) engine.renderImage(event, center, image, getOpacity());
	}
	
	@Override
	public boolean handleEvent(Event event) {
		// TODO Auto-generated method stub
		return false;
	}

	public Latlng getCenter() {
		return center;
	}

	public void setCenter(Latlng center) {
		this.center = center;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

}
