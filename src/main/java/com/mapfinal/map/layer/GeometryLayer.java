package com.mapfinal.map.layer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

public abstract class GeometryLayer extends AbstractLayer {

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
	public boolean handleEvent(Event event) {
		// TODO Auto-generated method stub
		if(geometry==null) return false;
		if(event.isAction("mouseClick")) {
			MapContext context = event.get("map");
			//用户点击的像素坐标
			ScreenPoint sp = event.get("screenPoint");
			Latlng p1 = context.pointToLatLng(ScenePoint.by(sp));
			if(geometry.contains(GeoKit.createPoint(p1))) {
				return sendEvent(Event.by(getEventAction("click"), "layer", this));
			}
		}
		return false;
	}
	
	public void addClick(EventListener listener) {
		addListener(getEventAction("click"), listener);
	}
	
	public void removeClick(EventListener listener) {
		removeListener(getEventAction("click"), listener);
	}
	
	public void clearClick() {
		clearListener(getEventAction("click"));
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
	
	public void addAttribute(String name, Object value) {
		if(attributes==null) {
			attributes = new HashMap<>();
		}
		attributes.put(name, value);
	}
	
	public void addAttr(String name, Object value) {
		if(attributes==null) {
			attributes = new HashMap<>();
		}
		attributes.put(name, value);
	}
	
	public <M>M getAttr(String name) {
		if(attributes==null) {
			return null;
		}
		return (M) attributes.get(name);
	}
	
	public <M>M getAttr(String name, M defaultValue) {
		if(attributes==null) {
			return defaultValue;
		}
		return attributes.get(name)==null ? defaultValue : (M) attributes.get(name);
	}
	
	public Object getAttribute(String name) {
		if(attributes==null) {
			return null;
		}
		return attributes.get(name);
	}
	
	public Object getAttribute(String name, Object defaultValue) {
		if(attributes==null) {
			return defaultValue;
		}
		return attributes.get(name)==null ? defaultValue : attributes.get(name);
	}
	
	public Object removeAttr(String name) {
		if(attributes!=null) {
			return attributes.remove(name);
		}
		return null;
	}
	
	public Object removeAttribute(String name) {
		if(attributes!=null) {
			return attributes.remove(name);
		}
		return null;
	}
	
	public Set<String> fields() {
		if(attributes!=null) {
			return attributes.keySet();
		}
		return null;
	}
}
