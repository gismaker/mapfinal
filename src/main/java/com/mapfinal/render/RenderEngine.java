package com.mapfinal.render;


import com.mapfinal.event.Event;
import com.mapfinal.map.Feature;
import com.mapfinal.map.GeoImage;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;

/**
 * 渲染器
 * 等待升级，支持双缓冲
 * @author yangyong
 */
public interface RenderEngine {
	
	void renderInit(Coordinate translate);
	void translate(Coordinate coordinate);
	void renderEnd();
	
	void render(Event event, Renderer renderer, Geometry geometry);

	void renderFeature(Event event, Renderer renderer, Feature feature);
	void renderImageFeature(Event event, Renderer renderer, GeoImage feature);
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
