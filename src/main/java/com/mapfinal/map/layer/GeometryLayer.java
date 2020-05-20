package com.mapfinal.map.layer;

import java.util.Map;

import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;

import com.mapfinal.converter.scene.ScenePoint;
import com.mapfinal.event.Event;
import com.mapfinal.event.EventListener;
import com.mapfinal.geometry.GeoKit;
import com.mapfinal.geometry.Latlng;
import com.mapfinal.geometry.ScreenPoint;
import com.mapfinal.map.AbstractLayer;
import com.mapfinal.map.MapContext;
import com.mapfinal.render.RenderEngine;

public class GeometryLayer extends AbstractLayer {

	protected Geometry geometry;
	/**
	 * 属性信息
	 */
	protected Map<String, Object> attributes;
	
	public GeometryLayer(Geometry geometry) {
		this.geometry = geometry;
	}

	@Override
	public Envelope getEnvelope() {
		// TODO Auto-generated method stub
		return geometry.getEnvelopeInternal();
	}

	@Override
	public void draw(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		if(geometry!=null) {
			engine.render(event, getRenderer(), geometry);
		}
	}
	
	@Override
	public boolean handleEvent(Event event) {
		// TODO Auto-generated method stub
		if(geometry==null) return false;
		if(event.isAction("mouseClick")) {
			MapContext context = event.get("map");
			//用户点击的像素坐标
			ScreenPoint sp = event.get("screenPoint");
			Latlng p1 = context.pointToLatLng(ScenePoint.by(sp));
			if(geometry.contains(GeoKit.createPoint(p1))) {
				return sendEvent(Event.by(getEventAction("Click"), "layer", this));
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

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

}
