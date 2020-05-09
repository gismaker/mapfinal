package com.mapfinal.map;

import com.mapfinal.converter.SpatialReference;
import com.mapfinal.converter.scene.SceneCRS;
import com.mapfinal.converter.scene.SceneEPSG3857CRS;
import com.mapfinal.converter.scene.ScenePoint;
import com.mapfinal.geometry.Latlng;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;

public class MapContext {

	/**
	 * 场景坐标系
	 */
	private SceneCRS sceneCRS;
	/**
	 * 空间坐标系统，通常和场景的坐标系一致	
	 */
	private SpatialReference spatialReference;
	/**
	 * 当前窗口对应的地图坐标系包围盒
	 */
	private Envelope sceneEnvelope;
	/**
	 * 地图本身的包围盒
	 */
	private Envelope mapEnvelop;
	/**
	 * 当前窗口的中心点
	 */
	private Latlng center;
	/**
	 * 场景坐标系下的中心点坐标
	 */
	private ScenePoint centerPoint;
	/**
	 * 当前地图的缩放级别
	 */
	private float zoom = 1;
	/**
	 * 强制map的缩放尺寸是该值的多倍
	 */
	private float zoomSnap = 0;
	/**
	 * 缩放粒度，缩放级别变化一次的大小，控制map的缩放水平
	 */
	private float zoomDelta = 0.25f;
	/**
	 * 最小缩放级别
	 */
	private float minZoom = 0;
	/**
	 * 最大缩放级别
	 */
	private float maxZoom = 24;
	/**
	 * 是否缩放动画
	 */
	private boolean zoomAnimation = true;
	/**
	 * 缩放大小超过该设定值时缩放动画失效
	 */
	private int zoomAnimationThreshold = 4;
	/**
	 * 淡入淡出动画
	 */
	private boolean fadeAnimation = true;
	/**
	 * marker是否激活缩放动画
	 */
	private boolean markerZoomAnimation = true;
	/**
	 * 地图窗口的大小
	 */
	private int width = 256;
	/**
	 * 地图窗口的大小
	 */
	private int height = 256;
	/**
	 * 屏幕的长宽
	 */
	private ScenePoint sceneSize;
	
	public MapContext() {
		// TODO Auto-generated constructor stub
		sceneCRS = new SceneEPSG3857CRS();
		center = new Latlng(0, 0);
		centerPoint = getSceneCRS().latLngToPoint(center, this.zoom);
		resetSceneEnvelope();
		sceneSize = getSceneCRS().latLngToPoint(new Latlng(-90, 180), zoom);
	}
	
	public void resize(int width, int height) {
		if(this.width!=width || this.height!=height) {
			this.width = width;
			this.height = height;
			resetSceneEnvelope();
		}
	}
	
	public void resetSceneEnvelope() {
		//System.out.println(centerPoint.toString());
		int x1 = (int) (centerPoint.x - width/2), y1 = (int) (centerPoint.y - height/2);
		int x2 = (int) (centerPoint.x + width/2), y2 = (int) (centerPoint.y + height/2);
		//System.out.println("x1 :" + x1 + ", y1: " + y1);
		//System.out.println("x2 :" + x2 + ", y2: " + y2);
		Latlng c1 = sceneCRS.pointToLatLng(new ScenePoint(x1, y1), zoom);
		Latlng c2 = sceneCRS.pointToLatLng(new ScenePoint(x2, y2), zoom);
		//System.out.println("[MapContext] c1[" + c1.toString() + "]");
		//System.out.println("[MapContext] c2[" + c2.toString() + "]");
		if(sceneEnvelope==null) {
			sceneEnvelope = new Envelope(c1, c2);
		} else {
			sceneEnvelope.init(c1, c2);
		}
		//System.out.println("envelope: " + sceneEnvelope.toString());
	}
	
	public void resetCenter(float dx, float dy) {
		if(dx == 0 && dy==0) return;
		centerPoint.subtract(new ScenePoint(dx, dy));
		center = getSceneCRS().pointToLatLng(centerPoint, zoom);
		resetSceneEnvelope();
	}
	
	public ScenePoint latLngToPoint(Latlng latlng, double zoom) {
		ScenePoint p = getSceneCRS().latLngToPoint(latlng, zoom);
		ScenePoint ct = getCenterPoint();
		p.translate(-ct.getX() + getWidth() / 2, -ct.getY() + getHeight() / 2);
		return p;
	}
	
	public ScenePoint latLngToPoint(Latlng latlng) {
		return latLngToPoint(latlng, zoom);
	}
	
	public Latlng pointToLatLng(ScenePoint point, double zoom) {
		ScenePoint ct = getCenterPoint();
		point.subtract(-ct.getX() + getWidth() / 2, -ct.getY() + getHeight() / 2);
		return getSceneCRS().pointToLatLng(point, zoom);
	}
	
	public Latlng pointToLatLng(ScenePoint point) {
		return pointToLatLng(point, zoom);
	}
	
	/**
	 * 投影坐标转屏幕坐标
	 * @param latlng
	 * @param zoom
	 * @return
	 */
	public ScenePoint coordinateToPoint(Coordinate coordinate, double zoom) {
		ScenePoint p = getSceneCRS().coordinateToPoint(coordinate, zoom);
		ScenePoint ct = getCenterPoint();
		p.translate(-ct.getX() + getWidth() / 2, -ct.getY() + getHeight() / 2);
		return p;
	}
	
	public ScenePoint coordinateToPoint(Coordinate coordinate) {
		return coordinateToPoint(coordinate, zoom);
	}
	
	/**
	 * 投影坐标转屏幕坐标
	 * @param latlng
	 * @param zoom
	 * @return
	 */
	public Coordinate pointToCoordinate(ScenePoint point, double zoom) {
		ScenePoint ct = getCenterPoint();
		point.subtract(-ct.getX() + getWidth() / 2, -ct.getY() + getHeight() / 2);
		return getSceneCRS().pointToCoordinate(point, zoom);
	}
	
	public Coordinate pointToCoordinate(ScenePoint point) {
		return pointToCoordinate(point, zoom);
	}
	
	public ScenePoint sceneToView(ScenePoint point) {
		ScenePoint ct = getCenterPoint();
		point.translate(-ct.getX() + getWidth() / 2, -ct.getY() + getHeight() / 2);
		return point;
	}
	
	public ScenePoint viewToScene(ScenePoint point) {
		ScenePoint ct = getCenterPoint();
		point.subtract(-ct.getX() + getWidth() / 2, -ct.getY() + getHeight() / 2);
		return point;
	}

	public SceneCRS getSceneCRS() {
		return sceneCRS;
	}

	public void setSceneCRS(SceneCRS sceneCRS) {
		this.sceneCRS = sceneCRS;
	}

	public float getZoom() {
		return zoom;
	}
	/**
	 * 放大
	 */
	public void zoomIn() {
		float zoom = getZoom();
		zoom+=zoomDelta;
        setZoom(zoom);
	}
	/**
	 * 缩小
	 */
	public void zoomOut() {
		float zoom = getZoom();
		zoom-=zoomDelta;
        setZoom(zoom);
	}

	public void setZoom(float zoom) {
		this.zoom = zoom;
		this.zoom = this.zoom < minZoom ? minZoom : this.zoom;
		this.zoom = this.zoom > maxZoom ? maxZoom : this.zoom;
		ScenePoint ct = getSceneCRS().latLngToPoint(center, this.zoom);
		setCenterPoint(ct);
		resetSceneEnvelope();
		sceneSize = getSceneCRS().latLngToPoint(new Latlng(-90, 180), zoom);
	}

	public Latlng getCenter() {
		return center;
	}

	public void setCenter(Latlng center) {
		this.center = center;
		ScenePoint ct = getSceneCRS().latLngToPoint(center, this.zoom);
		setCenterPoint(ct);
		resetSceneEnvelope();
	}
	
	public void setCenter(double lat, double lng) {
		this.center.setLat(lat);
		this.center.setLng(lng);
		ScenePoint ct = getSceneCRS().latLngToPoint(center, this.zoom);
		setCenterPoint(ct);
		resetSceneEnvelope();
	}

	public float getZoomSnap() {
		return zoomSnap;
	}

	public void setZoomSnap(float zoomSnap) {
		this.zoomSnap = zoomSnap;
	}

	public float getZoomDelta() {
		return zoomDelta;
	}

	public void setZoomDelta(float zoomDelta) {
		this.zoomDelta = zoomDelta;
	}

	public float getMinZoom() {
		return minZoom;
	}

	public void setMinZoom(float minZoom) {
		this.minZoom = minZoom;
	}

	public float getMaxZoom() {
		return maxZoom;
	}

	public void setMaxZoom(float maxZoom) {
		this.maxZoom = maxZoom;
	}

	public boolean isZoomAnimation() {
		return zoomAnimation;
	}

	public void setZoomAnimation(boolean zoomAnimation) {
		this.zoomAnimation = zoomAnimation;
	}

	public int getZoomAnimationThreshold() {
		return zoomAnimationThreshold;
	}

	public void setZoomAnimationThreshold(int zoomAnimationThreshold) {
		this.zoomAnimationThreshold = zoomAnimationThreshold;
	}

	public boolean isFadeAnimation() {
		return fadeAnimation;
	}

	public void setFadeAnimation(boolean fadeAnimation) {
		this.fadeAnimation = fadeAnimation;
	}

	public boolean isMarkerZoomAnimation() {
		return markerZoomAnimation;
	}

	public void setMarkerZoomAnimation(boolean markerZoomAnimation) {
		this.markerZoomAnimation = markerZoomAnimation;
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
/*
	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
*/
	public Envelope getSceneEnvelope() {
		return sceneEnvelope;
	}

	public void setSceneEnvelope(Envelope sceneEnvelope) {
		this.sceneEnvelope = sceneEnvelope;
	}

	public Envelope getMapEnvelop() {
		return mapEnvelop;
	}

	public void setMapEnvelop(Envelope mapEnvelop) {
		this.mapEnvelop = mapEnvelop;
	}

	public ScenePoint getCenterPoint() {
		return centerPoint;
	}

	public void setCenterPoint(ScenePoint centerPoint) {
		this.centerPoint = centerPoint;
	}

	public ScenePoint getSceneSize() {
		return sceneSize;
	}

	public void setSceneSize(ScenePoint sceneSize) {
		this.sceneSize = sceneSize;
	}

	public SpatialReference getSpatialReference() {
		return spatialReference;
	}

	public void setSpatialReference(SpatialReference spatialReference) {
		this.spatialReference = spatialReference;
	}
	
}
