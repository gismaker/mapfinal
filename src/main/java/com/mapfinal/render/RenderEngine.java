package com.mapfinal.render;


import com.mapfinal.event.Event;
import com.mapfinal.geometry.Latlng;
import com.mapfinal.map.Feature;
import com.mapfinal.map.GeoImage;
import com.mapfinal.resource.image.Image;

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
	
	void renderImage(Event event, Latlng latlng, Image image, float opacity);
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
