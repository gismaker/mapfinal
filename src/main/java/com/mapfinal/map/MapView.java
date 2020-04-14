package com.mapfinal.map;

import com.mapfinal.converter.SpatialReference;
import com.mapfinal.converter.scene.SceneCRS;
import com.mapfinal.converter.scene.ScenePoint;
import com.mapfinal.event.Event;
import com.mapfinal.event.EventKit;
import com.mapfinal.geometry.Latlng;
import com.mapfinal.kit.StringKit;
import com.mapfinal.render.RenderEngine;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;

public class MapView extends LayerGroup {

	private MapContext context;
	private int x0 = 0, y0 = 0;
	private int dx = 0, dy = 0;
	private boolean bMove = false;
	//private Renderer backgroundRenderer;
	boolean isZoomScale = false;
	
	public MapView() {
		context = new MapContext();
	}

	@Override
	public void draw(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		event.set("map", context);
//		ScenePoint ct = context.getCenterPoint();
//		Coordinate t = new Coordinate(-ct.getX() + context.getWidth() / 2 + dx,
//				-ct.getY() + context.getHeight() / 2 + dy);
		Coordinate t = new Coordinate(dx, dy);
		engine.renderInit(t);
//		if (backgroundRenderer != null) {
//			backgroundRenderer.draw(event, engine);
//		}
		super.draw(event, engine);
		engine.renderEnd();
	}

	@Override
	public boolean handleEvent(Event event) {
		// TODO Auto-generated method stub
		if (StringKit.isBlank(event.getAction()))
			return false;
		switch (event.getAction()) {
		case "mouseWheel":
			isZoomScale = true;
			if(event.get("rotation")!=null) {
				int rotation = event.get("rotation");
				if (rotation == 1) {
					zoomOut();
				}
				if (rotation == -1) {
					zoomIn();
				}
			}
			if(event.get("scaleFactor")!=null) {
				float scaleFactor = event.get("scaleFactor");
				float hzoom = context.getZoom();
				hzoom += scaleFactor - 1;
				context.setZoom(hzoom);
			}
			// System.out.println("[GeoMap.onEvent] current zoom: " +
			// getZoom());
			EventKit.sendEvent("redraw");
			isZoomScale = false;
			break;
		case "mouseDown":
			x0 = event.get("x");
			y0 = event.get("y");
			bMove = true;
			break;
		case "mouseUp":
			if (bMove) {
				bMove = false;
				if(!isZoomScale) {
					context.resetCenter(dx, dy);
					//System.out.println("[GeoMap.onEvent] mouseUp: " + dx + ", " + dy);
				}
				isZoomScale = false;
				dx = 0;
				dy = 0;
				// System.out.println("[GeoMap.onEvent] mouseUp");
				EventKit.sendEvent("redraw");
			}
			break;
		case "mouseMove":
			if (bMove && !isZoomScale) {
				int x = event.get("x");
				int y = event.get("y");
				dx = x - x0;
				dy = y - y0;
				// System.out.println("[GeoMap.onEvent] move x: " + x + ", y: "
				// + y + ", x0: " + x0 + ", y0: " + y0);
				// System.out.println("[GeoMap.onEvent] move dx: " + dx + ", dy:
				// " + dy);
				EventKit.sendEvent("redraw");
			}
			break;
		case "mouseCoordinate":
			int x = event.get("x");
			int y = event.get("y");
			Latlng latlng = mouseCoordinate(x, y);
			System.out.println("mouse latlng: " + latlng.toString());
			break;
		default:
			super.handleEvent(event);
			break;
		}
		return false;
	}
	
	public Latlng mouseCoordinate(float x, float y) {
		ScenePoint ct = context.getCenterPoint();
		Coordinate t = new Coordinate(-ct.getX() + context.getWidth() / 2 + dx,
				-ct.getY() + context.getHeight() / 2 + dy);
		double tx = x - t.x;
		double ty = y - t.y;
		return getSceneCRS().pointToLatLng(new ScenePoint(tx, ty), getZoom());
		//System.out.println("mouse latlng: " + latlng.toString());
	}

	public MapContext getContext() {
		return context;
	}

	public void setContext(MapContext context) {
		this.context = context;
	}

	public void resize(int width, int height) {
		context.resize(width, height);
		EventKit.sendEvent("redraw");
	}

	public SceneCRS getSceneCRS() {
		return context.getSceneCRS();
	}

	public void setSceneCRS(SceneCRS sceneCRS) {
		context.setSceneCRS(sceneCRS);
	}

	public double getZoom() {
		return context.getZoom();
	}

	/**
	 * 放大
	 */
	public void zoomIn() {
		context.zoomIn();
	}

	/**
	 * 缩小
	 */
	public void zoomOut() {
		context.zoomOut();
	}

	public void setZoom(float zoom) {
		context.setZoom(zoom);
	}

	public Latlng getCenter() {
		return context.getCenter();
	}
	
	public String getCenterString() {
		return context.getCenter().toString();
	}

	public void setCenter(Latlng center) {
		context.setCenter(center);
	}

	public float getZoomSnap() {
		return context.getZoomSnap();
	}

	public void setZoomSnap(float zoomSnap) {
		context.setZoomSnap(zoomSnap);
	}

	public float getZoomDelta() {
		return context.getZoomDelta();
	}

	public void setZoomDelta(float zoomDelta) {
		context.setZoomDelta(zoomDelta);
	}

	@Override
	public float getMinZoom() {
		return context.getMinZoom();
	}

	@Override
	public void setMinZoom(float minZoom) {
		super.setMinZoom(minZoom);
		context.setMinZoom(minZoom);
	}

	@Override
	public float getMaxZoom() {
		return context.getMaxZoom();
	}

	@Override
	public void setMaxZoom(float maxZoom) {
		super.setMaxZoom(maxZoom);
		context.setMaxZoom(maxZoom);
	}

	public boolean isZoomAnimation() {
		return context.isZoomAnimation();
	}

	public void setZoomAnimation(boolean zoomAnimation) {
		context.setZoomAnimation(zoomAnimation);
	}

	public int getZoomAnimationThreshold() {
		return context.getZoomAnimationThreshold();
	}

	public void setZoomAnimationThreshold(int zoomAnimationThreshold) {
		context.setZoomAnimationThreshold(zoomAnimationThreshold);
	}

	public boolean isFadeAnimation() {
		return context.isFadeAnimation();
	}

	public void setFadeAnimation(boolean fadeAnimation) {
		context.setFadeAnimation(fadeAnimation);
	}

	public boolean isMarkerZoomAnimation() {
		return context.isMarkerZoomAnimation();
	}

	public void setMarkerZoomAnimation(boolean markerZoomAnimation) {
		context.setMarkerZoomAnimation(markerZoomAnimation);
	}

	public int getWidth() {
		return context.getWidth();
	}

	public int getHeight() {
		return context.getHeight();
	}

	public Envelope getSceneEnvelope() {
		return context.getSceneEnvelope();
	}

	public void setSceneEnvelope(Envelope sceneEnvelope) {
		context.setSceneEnvelope(sceneEnvelope);
	}

	public Envelope getMapEnvelop() {
		return context.getMapEnvelop();
	}

	public void setMapEnvelop(Envelope mapEnvelop) {
		context.setMapEnvelop(mapEnvelop);
	}
	
	@Override
	public SpatialReference getSpatialReference() {
		return context.getSpatialReference();
	}
	
	@Override
	public void setSpatialReference(SpatialReference spatialReference) {
		super.setSpatialReference(spatialReference);
		context.setSpatialReference(spatialReference);
	}
}
