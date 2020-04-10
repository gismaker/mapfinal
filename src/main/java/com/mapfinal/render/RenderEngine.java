package com.mapfinal.render;

import java.util.Map;

import com.mapfinal.event.Event;
import com.mapfinal.map.Feature;
import com.mapfinal.map.GeoImage;
import com.mapfinal.map.MapContext;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;

/**
 * 渲染器
 * 等待升级，支持双缓冲
 * 
 * 高德渲染器 http://a.amap.com/lbs/static/unzip/Android_Map_Doc/index.html
 * @author yangyong
 *
 */
public interface RenderEngine {
	
	Event getEvent();
	void setEvent(Event event);
	void setEventData(String name, Object data);
	<M> M getEventData(String name);
	Map<String, Object> getEventData();
	String getEventAction();
	
	void renderImageModeInit(MapContext map, Coordinate translate);
	void translateImageMode(Coordinate coordinate);
	void renderImageModeEnd();
	
	void renderInit(Coordinate translate);
	void translate(Coordinate coordinate);
	void renderEnd();
	
	void render(Renderer renderer, MapContext context, Geometry geometry);
	
	void renderFeature(Renderer renderer, MapContext context, Feature feature);
	void renderImageFeature(Renderer renderer, MapContext context, GeoImage feature);
	/*
	void renderTextOnPath();
	void renderImage();
	void renderPoint();
	void renderLine();
	void renderCircle();
	void renderBox();
	void renderCrossedBox();
	void renderCross();
	void renderPath();
	*/
	void update();
}
