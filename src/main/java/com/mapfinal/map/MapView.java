package com.mapfinal.map;

import com.mapfinal.converter.SpatialReference;
import com.mapfinal.converter.scene.SceneCRS;
import com.mapfinal.event.Event;
import com.mapfinal.event.EventKit;
import com.mapfinal.event.listener.MapMoveListener;
import com.mapfinal.event.listener.MapZoomListener;
import com.mapfinal.geometry.Latlng;
import com.mapfinal.kit.StringKit;
import com.mapfinal.render.RenderEngine;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;

public class MapView extends LayerGroup {

	private MapContext context;
	
	public MapView() {
		setName("map_" + StringKit.getUuid32());
		context = new MapContext();
		context.setMainThread(true);
		this.addListener("map:move", new MapMoveListener());
		this.addListener("map:zoom", new MapZoomListener());
	}

	@Override
	public void draw(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		if("pick".equals(event.getAction())) {
			MapContext pickContext = (MapContext) context.clone();//context.newContext();//
			event.set("map", pickContext);
			float x = event.get("pick_screen_x");
			float y = event.get("pick_screen_y");
			Latlng center = context.mouseCoordinate(x, y);
			pickContext.setCenter(center);
			pickContext.resize(event.get("width"), event.get("height"));
		} else {
			context.resize(event.get("width"), event.get("height"));
			event.set("map", context);
		}
//		ScenePoint ct = context.getCenterPoint();
//		Coordinate t = new Coordinate(-ct.getX() + context.getWidth() / 2 + dx,
//				-ct.getY() + context.getHeight() / 2 + dy);
		Coordinate t = context.currentDrag();
		engine.translate(t);
//		if (backgroundRenderer != null) {
//			backgroundRenderer.draw(event, engine);
//		}
		super.draw(event, engine);
	}

	@Override
	public boolean handleEvent(Event event) {
		// TODO Auto-generated method stub
		if (StringKit.isBlank(event.getAction()))
			return false;
		event.set("map", context);
		boolean flag = super.handleEvent(event);
		if(flag) return flag;
		switch (event.getAction()) {
		case "mouseWheel":
			return sendEvent("map:zoom", event);
		case "mouseDown":
			return sendEvent("map:move", event);
		case "mouseUp":
			return sendEvent("map:move", event);
		case "mouseMove":
			return sendEvent("map:move", event);
		default:
			return false;
		}
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

//	public Envelope getMapEnvelop() {
//		return context.getMapEnvelop();
//	}
//
//	public void setMapEnvelop(Envelope mapEnvelop) {
//		context.setMapEnvelop(mapEnvelop);
//	}
	
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
