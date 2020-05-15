package com.mapfinal.map.layer;

import org.locationtech.jts.geom.Envelope;

import com.mapfinal.converter.scene.ScenePoint;
import com.mapfinal.event.Event;
import com.mapfinal.event.EventKit;
import com.mapfinal.event.EventListener;
import com.mapfinal.geometry.Latlng;
import com.mapfinal.geometry.ScreenPoint;
import com.mapfinal.map.AbstractLayer;
import com.mapfinal.map.MapContext;
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
		if(event.isAction("mouseClick")) {
			MapContext context = event.get("map");
			ScenePoint p1 = context.latLngToPoint(center);
			int w = image.getWidth();
			int h = image.getHeight();
			int sw = (int) (w*scale);
			int sh = (int) (h*scale);
			//左上角像素值
			int x = (int) Math.round(p1.getX()-sw*0.5);
			int y = (int) Math.round(p1.getY()-sh*0.5);
			//右下角像素值
			int ex = x + sw;
			int ey = y + sh;
			
			//用户点击的像素坐标
			ScreenPoint sp = event.get("screenPoint");
			float x0 = sp.getX();
			float y0 = sp.getY();
			
			if(x0 >= x && x0<=ex && y0 >= y && y0<=ey) {
				return sendEvent(Event.by(getEventAction("Click"), "center", center));
			}
		}
		return false;
	}
	
	public void addClick(EventListener listener) {
		addListener(getEventAction("Click"), listener);
	}
	
	public void removeClick(EventListener listener) {
		removeListener(getEventAction("Click"), listener);
	}
	
	public void clearClick() {
		clearListener(getEventAction("Click"));
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
