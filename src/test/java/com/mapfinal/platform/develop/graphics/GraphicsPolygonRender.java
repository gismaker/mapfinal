package com.mapfinal.platform.develop.graphics;

import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import com.mapfinal.converter.ConverterKit;
import com.mapfinal.converter.scene.SceneCRS;
import com.mapfinal.converter.scene.ScenePoint;
import com.mapfinal.event.Event;
import com.mapfinal.geometry.Latlng;
import com.mapfinal.map.MapContext;
import com.mapfinal.render.RenderEngine;
import com.mapfinal.render.Renderable;
import com.mapfinal.render.Renderer;
import com.mapfinal.render.style.FillSymbol;
import com.mapfinal.render.style.LineSymbol;
import org.locationtech.jts.geom.Coordinate;

public class GraphicsPolygonRender implements Renderable {
	
	private List<ScenePoint> points;
	private Polygon polygon;
	private double currentZoom = 1;
	private String id = "NaN";
	
	public GraphicsPolygonRender() {
	}
	
	public GraphicsPolygonRender(String id, Polygon polygon, List<ScenePoint> points) {
		this.setId(id);
		this.polygon = polygon;
		this.points = points;
	}
	
	public static GraphicsPolygonRender create(org.locationtech.jts.geom.Polygon geoPolygon, SceneCRS crs) {
		int[] xPoints = new int[geoPolygon.getNumPoints()];
		int[] yPoints = new int[geoPolygon.getNumPoints()];
		int nPoints = geoPolygon.getNumPoints();
		List<ScenePoint> points = new ArrayList<>();
		for (int j = 0; j < geoPolygon.getNumPoints(); j++) {
			Coordinate coordinate = geoPolygon.getCoordinates()[j];
			Latlng latlng = Latlng.create(coordinate);
			double[] wgs1 = ConverterKit.wgs2gcj(latlng.lat(), latlng.lng());
			latlng.x  = wgs1[1];
			latlng.y  = wgs1[0];
			ScenePoint sp = crs.latLngToPoint(latlng, 1);
			xPoints[j] = sp.getSx();
			yPoints[j] = sp.getSy();
			points.add(sp);
		}
		Polygon pgon = new Polygon(xPoints, yPoints, nPoints);
		return new GraphicsPolygonRender("NaN", pgon, points);
		
	}
	
	public void addPoint(ScenePoint point) {
		if(points==null){points = new ArrayList<ScenePoint>();}
		points.add(point);
		if(polygon==null){polygon = new Polygon();}
		polygon.addPoint(point.getSx(), point.getSy());
	}
	
	protected boolean IsEqual(double a,double b) {
	    return Math.abs(a-b) < 0.000001;
	}
	
	protected void update(MapContext context, double zoom) {
		if(polygon==null || points==null) return;
		//if(!IsEqual(zoom,currentZoom)) {
			currentZoom = zoom;
			double scale = Math.pow(2, zoom-1);
			//System.out.println("[GraphicsPolygonRenderer] zoom: " + currentZoom + ", " + zoom + ", scale: "+scale);
			for (int i = 0; i < points.size(); i++) {
				ScenePoint pt = context.sceneToView(points.get(i));
				polygon.xpoints[i] = (int) Math.round(pt.x * scale);
				polygon.ypoints[i] = (int) Math.round(pt.y * scale);
			}
//			if(id.equals("19") || id.equals("20")) {
//				System.out.println("x:" + polygon.xpoints[0] + ", y:" + polygon.xpoints[0] + ", x:" + points.get(0).x + ", y:" + points.get(0).y);
//			}
		//}
	}

	@Override
	public void draw(Event event, RenderEngine engine, Renderer renderer) {
		// TODO Auto-generated method stub
		if(polygon!=null) {
			//System.out.println("[GraphicsPolygonRenderer] id: " + id + ", points: " + points.size() + ", " + polygon.npoints);
			if (engine instanceof GraphicsRenderEngine) {
				GraphicsRenderEngine g = (GraphicsRenderEngine) engine;
				MapContext context = event.get("map");
				double zoom = context.getZoom();
				update(context, zoom);
				
				if(renderer!=null && renderer.getSymbol()!=null && renderer.getSymbol() instanceof FillSymbol) {
					FillSymbol symbol = (FillSymbol) renderer.getSymbol();
					int color = symbol.getColor();
					g.getGraphics2D().setColor(ColorUtil.intToColor(color));
					g.getGraphics2D().fillPolygon(polygon);
					if(symbol.getOutline() != null) {
						LineSymbol lsymbol = symbol.getOutline();
						int lineColor = lsymbol.getColor();
						g.getGraphics2D().setColor(ColorUtil.intToColor(lineColor));
					} else {
						g.getGraphics2D().setColor(new Color(255, 0, 0, 125));
					}
					g.getGraphics2D().drawPolygon(polygon);
				} else {
					g.getGraphics2D().setColor(new Color(0, 255, 0, 125));
					g.getGraphics2D().fillPolygon(polygon);
					g.getGraphics2D().setColor(new Color(255, 0, 0, 125));
					g.getGraphics2D().drawPolygon(polygon);
					
				}
			}
		}
	}

	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub

	}
	
	public Polygon getPolygon() {
		return polygon;
	}

	public void setPolygon(Polygon polygon) {
		this.polygon = polygon;
	}

	public List<ScenePoint> getPoints() {
		return points;
	}

	public void setPoints(List<ScenePoint> points) {
		this.points = points;
	}

	public double getCurrentZoom() {
		return currentZoom;
	}

	public void setCurrentZoom(double currentZoom) {
		this.currentZoom = currentZoom;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
