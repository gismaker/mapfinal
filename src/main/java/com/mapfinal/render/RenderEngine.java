package com.mapfinal.render;


import com.mapfinal.converter.scene.ScenePoint;
import com.mapfinal.event.Event;
import com.mapfinal.geometry.Latlng;
import com.mapfinal.geometry.LatlngBounds;
import com.mapfinal.map.Feature;
import com.mapfinal.map.GeoImage;
import com.mapfinal.map.MapContext;
import com.mapfinal.render.style.FillSymbol;
import com.mapfinal.render.style.LineSymbol;
import com.mapfinal.render.style.MarkerSymbol;
import com.mapfinal.render.style.Symbol;
import com.mapfinal.resource.image.Image;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

/**
 * 渲染器
 * 等待升级，支持双缓冲
 * @author yangyong
 */
public interface RenderEngine {
	
	void renderStart();
	void renderEnd();
	
	void translate(Coordinate coordinate);
	
	/**
	 * 渲染要素
	 * @param event
	 * @param renderer
	 * @param feature
	 */
	void renderFeature(Event event, Symbol symbol, Feature feature);
	
	/**
	 * 渲染图片要素
	 * @param event
	 * @param renderer
	 * @param feature
	 */
	void renderImageFeature(Event event, Symbol symbol, GeoImage feature);
	
	/**
	 * 渲染图片
	 * @param event
	 * @param latlng
	 * @param image
	 * @param opacity
	 */
	void renderImage(Event event, Latlng latlng, Image image, float opacity);
	/**
	 * 渲染图片
	 * @param event
	 * @param latlngBounds
	 * @param image
	 * @param opacity
	 */
	void renderImage(Event event, LatlngBounds latlngBounds, Image image, float opacity);
	
	
	
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
	/**
	 * 渲染图形
	 * @param event
	 * @param symbol
	 * @param geometry
	 */
	default void render(Event event, Symbol symbol, Geometry geometry) {
		if(geometry==null) return;
		if(symbol==null) {
			if("MultiLineString".equals(geometry.getGeometryType()) || geometry instanceof MultiLineString) {
				renderPolyline(event, null, geometry);
			} else if("LineString".equals(geometry.getGeometryType()) || geometry instanceof LineString) {
				renderPolyline(event, null, geometry);
			} else if("LineRing".equals(geometry.getGeometryType()) || geometry instanceof LineString) {
				renderPolyline(event, null, geometry);
			} else if("MultiPolygon".equals(geometry.getGeometryType()) || geometry instanceof MultiPolygon) {
				renderPolygon(event, null, geometry);
			} else if("Polygon".equals(geometry.getGeometryType()) || geometry instanceof Polygon) {
				renderPolygon(event, null, geometry);
			} else if("Point".equals(geometry.getGeometryType()) || geometry instanceof Point) {
				renderPoint(event, null, geometry);
			} else if("MultiPoint".equals(geometry.getGeometryType()) || geometry instanceof MultiPoint) {
				renderPoint(event, null, geometry);
			}
		} else {
			if(symbol instanceof FillSymbol) {
				renderPolygon(event, (FillSymbol) symbol, geometry);
			} else if(symbol instanceof LineSymbol) {
				renderPolyline(event, (LineSymbol) symbol, geometry);
			}
		}
	}
	
	default void renderPolygon(Event event, FillSymbol symbol, Geometry geometry) {
		if("MultiPolygon".equals(geometry.getGeometryType()) || geometry instanceof MultiPolygon) {
			int numberPolygon = geometry.getNumGeometries();
			for (int i=0; i<numberPolygon; i++) {
				renderPolygon(event, symbol, (Polygon)geometry.getGeometryN(i));
			}
		} else if("Polygon".equals(geometry.getGeometryType()) || geometry instanceof Polygon) {
			renderPolygon(event, symbol, (Polygon)geometry);
		} 
	}
	
	void renderPolygon(Event event, FillSymbol symbol, Polygon geometry);
	/**
	 * 渲染线框
	 * @param event
	 * @param symbol
	 * @param geometry
	 */
	default void renderPolyline(Event event, LineSymbol symbol, Geometry geometry) {
		if(geometry==null) return;
        if("MultiLineString".equals(geometry.getGeometryType()) || geometry instanceof MultiLineString) {
        	int numberPolygon = geometry.getNumGeometries();
    		for (int i=0; i<numberPolygon; i++) {
    			LineString line = (LineString)geometry.getGeometryN(i);
    			renderLine(event, symbol, line.getCoordinateSequence());
    		}
		} else if("LineString".equals(geometry.getGeometryType()) || geometry instanceof LineString) {
			LineString line = (LineString)geometry;
			renderLine(event, symbol, line.getCoordinateSequence());
		} else if("LineRing".equals(geometry.getGeometryType()) || geometry instanceof LineString) {
			LinearRing line = (LinearRing)geometry;
			renderLine(event, symbol, line.getCoordinateSequence());
		} else if("MultiPolygon".equals(geometry.getGeometryType()) || geometry instanceof MultiPolygon) {
			int numberPolygon = geometry.getNumGeometries();
			for (int i=0; i<numberPolygon; i++) {
				Polygon polygon = (Polygon)geometry.getGeometryN(i);
				LineString line = polygon.getExteriorRing();
				renderLine(event, symbol, line.getCoordinateSequence());
				int numberHole = polygon.getNumInteriorRing();
				for (int j = 0; j < numberHole; j++) {
					LineString holeLine = polygon.getInteriorRingN(j);
					renderLine(event, symbol, holeLine.getCoordinateSequence());
				}
			}
		} else if("Polygon".equals(geometry.getGeometryType()) || geometry instanceof Polygon) {
			Polygon polygon = (Polygon)geometry;
			LineString line = polygon.getExteriorRing();
			renderLine(event, symbol, line.getCoordinateSequence());
			int numberHole = polygon.getNumInteriorRing();
			for (int j = 0; j < numberHole; j++) {
				LineString holeLine = polygon.getInteriorRingN(j);
				renderLine(event, symbol, holeLine.getCoordinateSequence());
			}
		} 
	}
	/**
	 * 渲染线框
	 * @param event
	 * @param symbol
	 * @param coordinates
	 */
	default void renderLine(Event event, LineSymbol symbol, CoordinateSequence coordinates) {
		if(coordinates==null || coordinates.size() < 2) return;
		MapContext context = event.get("map");
        for (int i = 1; i < coordinates.size(); i++) {
			Coordinate la = coordinates.getCoordinate(i-1);
			Coordinate lb = coordinates.getCoordinate(i);
			ScenePoint sp1 = context.latLngToPoint(Latlng.create(la));
			ScenePoint sp2 = context.latLngToPoint(Latlng.create(lb));
			renderLine(symbol, sp1, sp2);
		}
	}
	/**
	 * 渲染线段
	 * @param symbol
	 * @param sp
	 * @param ep
	 */
	void renderLine(LineSymbol symbol, Coordinate sp, Coordinate ep);
	
	/**
	 * 渲染点图形
	 * @param event
	 * @param symbol
	 * @param geometry
	 */
	default void renderPoint(Event event, MarkerSymbol symbol, Geometry geometry) {
		if(geometry==null) return;
		if("MultiLineString".equals(geometry.getGeometryType()) || geometry instanceof MultiLineString) {
        	int numberPolygon = geometry.getNumGeometries();
    		for (int i=0; i<numberPolygon; i++) {
    			LineString line = (LineString)geometry.getGeometryN(i);
    			renderPoint(event, symbol, line.getCoordinateSequence());
    		}
		} else if("LineString".equals(geometry.getGeometryType()) || geometry instanceof LineString) {
			LineString line = (LineString)geometry;
			renderPoint(event, symbol, line.getCoordinateSequence());
		} else if("LineRing".equals(geometry.getGeometryType()) || geometry instanceof LineString) {
			LinearRing line = (LinearRing)geometry;
			renderPoint(event, symbol, line.getCoordinateSequence());
		} else if("MultiPolygon".equals(geometry.getGeometryType()) || geometry instanceof MultiPolygon) {
			int numberPolygon = geometry.getNumGeometries();
			for (int i=0; i<numberPolygon; i++) {
				Polygon polygon = (Polygon)geometry.getGeometryN(i);
				LineString line = polygon.getExteriorRing();
				renderPoint(event, symbol, line.getCoordinateSequence());
				int numberHole = polygon.getNumInteriorRing();
				for (int j = 0; j < numberHole; j++) {
					LineString holeLine = polygon.getInteriorRingN(j);
					renderPoint(event, symbol, holeLine.getCoordinateSequence());
				}
			}
		} else if("Polygon".equals(geometry.getGeometryType()) || geometry instanceof Polygon) {
			Polygon polygon = (Polygon)geometry;
			LineString line = polygon.getExteriorRing();
			renderPoint(event, symbol, line.getCoordinateSequence());
			int numberHole = polygon.getNumInteriorRing();
			for (int j = 0; j < numberHole; j++) {
				LineString holeLine = polygon.getInteriorRingN(j);
				renderPoint(event, symbol, holeLine.getCoordinateSequence());
			}
		} else if("Point".equals(geometry.getGeometryType()) || geometry instanceof Point) {
			Point point = (Point) geometry;
			 renderPoint(event, symbol, point.getCoordinate());
		} else if("MultiPoint".equals(geometry.getGeometryType()) || geometry instanceof MultiPoint) {
			int numberPolygon = geometry.getNumGeometries();
    		for (int i=0; i<numberPolygon; i++) {
    			Point pt = (Point)geometry.getGeometryN(i);
    			renderPoint(event, symbol, pt.getCoordinate());
    		}
		}
	}
	/**
	 * 渲染点
	 * @param event
	 * @param symbol
	 * @param coordinates
	 */
	default void renderPoint(Event event, MarkerSymbol symbol, CoordinateSequence coordinates) {
		if(coordinates==null || coordinates.size() < 2) return;
		MapContext context = event.get("map");
        for (int i = 1; i < coordinates.size(); i++) {
			Coordinate la = coordinates.getCoordinate(i-1);
			ScenePoint sp = context.latLngToPoint(Latlng.create(la));
			renderPoint(symbol, sp);
		}
	}
	
	/**
	 * 渲染点
	 * @param event
	 * @param symbol
	 * @param coordinate
	 */
	default void renderPoint(Event event, MarkerSymbol symbol, Coordinate coordinate) {
		if(coordinate==null) return;
		MapContext context = event.get("map");
		ScenePoint sp = context.latLngToPoint(Latlng.create(coordinate));
		renderPoint(symbol, sp);
	}
	
	/**
	 * 渲染像素点
	 * @param symbol
	 * @param coordinate
	 */
	void renderPoint(MarkerSymbol symbol, Coordinate coordinate);
	/**
	 * 刷新
	 */
	void update();
}
