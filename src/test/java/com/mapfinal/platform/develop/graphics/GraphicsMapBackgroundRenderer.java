package com.mapfinal.platform.develop.graphics;

import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import com.mapfinal.converter.scene.ScenePoint;
import com.mapfinal.event.Event;
import com.mapfinal.geometry.Latlng;
import com.mapfinal.map.MapContext;
import com.mapfinal.render.RenderEngine;
import com.mapfinal.render.SimpleRenderer;
import com.mapfinal.render.style.SimpleFillSymbol;

public class GraphicsMapBackgroundRenderer extends SimpleRenderer {

	public GraphicsMapBackgroundRenderer() {
		super(new SimpleFillSymbol(ColorUtil.colorToInt(Color.white)), new GraphicsPolygonRender());
	}
	
	@Override
	public void onRender(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		GraphicsPolygonRender render = (GraphicsPolygonRender) getRenderable();
		if(render.getPolygon()==null) {
			MapContext context = event.get("map");
			ScenePoint p1 = context.getSceneCRS().latLngToPoint(new Latlng(90, -180), 1);
			ScenePoint p3 = context.getSceneCRS().latLngToPoint(new Latlng(-90, 180), 1);
			ScenePoint p2 = new ScenePoint(p1.x, p3.y);
			ScenePoint p4 = new ScenePoint(p3.x, p1.y);
			//System.out.println("[mapBG] p1: " + p1.toString());
			//System.out.println("[mapBG] p2: " + p2.toString());
			//System.out.println("[mapBG] p3: " + p3.toString());
			//System.out.println("[mapBG] p4: " + p4.toString());
			List<ScenePoint> points = new ArrayList<>();
			points.add(p1);
			points.add(p2);
			points.add(p3);
			points.add(p4);
			int[] xPoints = new int[4];
			int[] yPoints = new int[4];
			int nPoints = 4;
			for (int j = 0; j < 4; j++) {
				xPoints[j] = points.get(j).getSx();
				yPoints[j] = points.get(j).getSy();
			}
			render.setId("mapBG");
			render.setPolygon(new Polygon(xPoints, yPoints, nPoints));
			render.setPoints(points);
		}
		render.onRender(event, engine, this);
	}
}
