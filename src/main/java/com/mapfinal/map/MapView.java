package com.mapfinal.map;

import com.mapfinal.Mapfinal;
import com.mapfinal.converter.scene.SceneCRS;
import com.mapfinal.converter.scene.ScenePoint;
import com.mapfinal.event.Event;
import com.mapfinal.geometry.Latlng;
import com.mapfinal.kit.StringKit;
import com.mapfinal.render.RenderEngine;
import com.mapfinal.render.Renderer;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;

public class MapView extends LayerGroup {

	private MapContext context;
	private int x0 = 0, y0 = 0;
	private int dx = 0, dy = 0;
	private boolean bMove = false;
	private Renderer backgroundRenderer;

	public MapView() {
		context = new MapContext();
	}

	@Override
	public void onRender(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		event.set("map", context);
		// Latlng center = context.getCenter();
		// ScenePoint ct = context.getSceneCRS().latLngToPoint(center,
		// context.getZoom());
		ScenePoint ct = context.getCenterPoint();
		Coordinate t = new Coordinate(-ct.getX() + context.getWidth() / 2 + dx,
				-ct.getY() + context.getHeight() / 2 + dy);
		engine.renderInit(t);
		if (backgroundRenderer != null) {
			backgroundRenderer.onRender(event, engine);
		}
		super.onRender(event, engine);
		engine.renderEnd();
	}

	@Override
	public void onEvent(Event event) {
		// TODO Auto-generated method stub
		if (StringKit.isBlank(event.getAction()))
			return;
		switch (event.getAction()) {
		case "mouseWheel":
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
			Mapfinal.me().getScene().update();
			break;
		case "mouseDown":
			x0 = event.get("x");
			y0 = event.get("y");
			bMove = true;
			break;
		case "mouseUp":
			if (bMove) {
				bMove = false;
				context.resetCenter(dx, dy);
				dx = 0;
				dy = 0;
				// System.out.println("[GeoMap.onEvent] mouseUp");
				Mapfinal.me().getScene().update();
			}
			break;
		case "mouseMove":
			if (bMove) {
				int x = event.get("x");
				int y = event.get("y");
				dx = x - x0;
				dy = y - y0;
				// System.out.println("[GeoMap.onEvent] move x: " + x + ", y: "
				// + y + ", x0: " + x0 + ", y0: " + y0);
				// System.out.println("[GeoMap.onEvent] move dx: " + dx + ", dy:
				// " + dy);
				Mapfinal.me().getScene().update();
			}
			break;
		case "mouseCoordinate":
			int x = event.get("x");
			int y = event.get("y");
			Latlng latlng = mouseCoordinate(x, y);
			System.out.println("mouse latlng: " + latlng.toString());
			break;
		default:
			super.onEvent(event);
			break;
		}
	}
	
	public Latlng mouseCoordinate(int x, int y) {
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

	public float getMinZoom() {
		return context.getMinZoom();
	}

	public void setMinZoom(float minZoom) {
		context.setMinZoom(minZoom);
	}

	public float getMaxZoom() {
		return context.getMaxZoom();
	}

	public void setMaxZoom(float maxZoom) {
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

	public String getCrsName() {
		return context.getCrsName();
	}

	public void setCrsName(String crsName) {
		context.setCrsName(crsName);
	}

	public Renderer getBackgroundRenderer() {
		return backgroundRenderer;
	}

	public void setBackgroundRenderer(Renderer backgroundRenderer) {
		this.backgroundRenderer = backgroundRenderer;
	}
}
