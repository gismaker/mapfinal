package com.mapfinal.map.layer;


import java.util.Map;

import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;

import com.mapfinal.converter.scene.ScenePoint;
import com.mapfinal.event.Event;
import com.mapfinal.event.EventListener;
import com.mapfinal.geometry.GeoKit;
import com.mapfinal.geometry.GeomType;
import com.mapfinal.geometry.Latlng;
import com.mapfinal.geometry.ScreenPoint;
import com.mapfinal.map.AbstractLayer;
import com.mapfinal.map.MapContext;
import com.mapfinal.render.RenderEngine;
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
	private Appearance style;
	/**
	 * 图形
	 */
	private Geometry geometry;
	/**
	 * 图形类型
	 */
	private GeomType geoType;
	/**
	 * 属性信息
	 */
	private Map<String, Object> attributes;
	
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

	public Appearance getStyle() {
		return style;
	}

	public void setStyle(Appearance style) {
		this.style = style;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
		if(geometry!=null) {
			this.geoType = GeomType.valueOf(geometry.getGeometryType());
		}
	}

	public GeomType getGeoType() {
		return geoType;
	}

	public void setGeoType(GeomType geoType) {
		this.geoType = geoType;
	}

}
