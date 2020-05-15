package com.mapfinal.map.layer;


import java.util.HashMap;
import java.util.Map;

import com.mapfinal.converter.scene.ScenePoint;
import com.mapfinal.event.Event;
import com.mapfinal.event.EventListener;
import com.mapfinal.geometry.GeomType;
import com.mapfinal.geometry.Latlng;
import com.mapfinal.geometry.ScreenPoint;
import com.mapfinal.map.AbstractLayer;
import com.mapfinal.map.MapContext;
import com.mapfinal.render.style.Appearance;

/**
 * 仿leaflet的PathLayer
 * @author Henry Yang 杨勇 (gismail@foxmail.com)
 * @version 1.0
 * @Package com.mapfinal.map.layer
 */
public abstract class Path extends AbstractLayer {

	/**
	 * 样式
	 */
	protected Appearance style;
	/**
	 * 图形
	 */
	//protected Geom geometry;
	/**
	 * 图形类型
	 */
	protected GeomType geomType;
	/**
	 * 属性信息
	 */
	protected Map<String, Object> attributes;
	
	@Override
	public boolean handleEvent(Event event) {
		// TODO Auto-generated method stub
		if(isEmpyt()) return false;
		if(event.isAction("mouseClick")) {
			MapContext context = event.get("map");
			//用户点击的像素坐标
			ScreenPoint sp = event.get("screenPoint");
			Latlng p1 = context.pointToLatLng(ScenePoint.by(sp));
			if(contains(p1)) {
				return sendEvent(Event.by(getEventAction("Click"), "layer", this));
			}
		}
		return false;
	}
	
	public abstract boolean isEmpyt();
	public abstract boolean contains(Latlng point);
	
	public void addClick(EventListener listener) {
		addListener(getEventAction("Click"), listener);
	}
	
	public void removeClick(EventListener listener) {
		removeListener(getEventAction("Click"), listener);
	}
	
	public void clearClick() {
		clearListener(getEventAction("Click"));
	}

	public Appearance getStyle() {
		return style;
	}

	public void setStyle(Appearance style) {
		this.style = style;
	}

	public GeomType getGeomType() {
		return geomType;
	}

	public void setGeomType(GeomType geomType) {
		this.geomType = geomType;
	}
	
	
	public void addAttr(String key, Object value) {
		if(attributes==null) {
			attributes = new HashMap<String, Object>();
		}
		attributes.put(key, value);
	}
	
	public void setAttr(String key, Object value) {
		if(attributes!=null) {
			attributes.put(key, value);
		}
	}
	
	public void removeAttr(String key) {
		if(attributes!=null) {
			attributes.remove(key);
		}
	}
	
	public void clearAttr(String key) {
		if(attributes!=null) {
			attributes.clear();
		}
	}
	
	public <M>M getAttr(String key) {
		if(attributes!=null) {
			return (M) attributes.get(key);
		}
		return null;
	}

	public Object getAttrObject(String key) {
		if(attributes!=null) {
			return attributes.get(key);
		}
		return null;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

}
