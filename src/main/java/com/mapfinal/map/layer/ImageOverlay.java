package com.mapfinal.map.layer;

import org.locationtech.jts.geom.Envelope;

import com.mapfinal.event.Event;
import com.mapfinal.geometry.LatlngBounds;
import com.mapfinal.map.AbstractLayer;
import com.mapfinal.render.RenderEngine;
import com.mapfinal.resource.image.Image;

/**
 * 图片
 * @author Henry Yang 杨勇 (gismail@foxmail.com)
 * @version 1.0
 * @Package com.mapfinal.map.layer
 */
public class ImageOverlay extends AbstractLayer {

	private LatlngBounds bounds;
	private Image image;
	
	public ImageOverlay(LatlngBounds bounds, Image image) {
		this.bounds = bounds;
		this.image = image;
	}

	@Override
	public Envelope getEnvelope() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		if(image==null || bounds==null || !bounds.isValid()) return;
		if(image.getData()==null) {
			image.read();
		}
		if(image.getData()==null) return;
		if(isVisible()) engine.renderImage(event, bounds, image, getOpacity());
	}
	
	@Override
	public boolean handleEvent(Event event) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public LatlngBounds getBounds() {
		return bounds;
	}

	public void setBounds(LatlngBounds bounds) {
		this.bounds = bounds;
	}

}
