package com.mapfinal.map;

import com.mapfinal.converter.SpatialReference;
import com.mapfinal.converter.scene.SceneCRS;
import com.mapfinal.converter.scene.SceneEPSG3857CRS;
import com.mapfinal.converter.scene.ScenePoint;
import com.mapfinal.geometry.Latlng;
import com.mapfinal.render.LabelEngine;
import com.mapfinal.render.LabelSTRTreeEngine;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.util.Assert;

public class MapContext implements Cloneable {

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
	//private Envelope mapEnvelop;
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
	 * 正在缩放
	 */
	private boolean isZoomScale = true;
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
	
	private float dragX=0, dragY=0;
	
	private boolean isMainThread = false;
	
	private LabelEngine labelEngine;
	
	public MapContext() {
		// TODO Auto-generated constructor stub
		sceneCRS = new SceneEPSG3857CRS();
		spatialReference = SpatialReference.mercator();
		center = new Latlng(0, 0);
		centerPoint = getSceneCRS().latLngToPoint(center, this.zoom);
		resetSceneEnvelope();
		sceneSize = getSceneCRS().latLngToPoint(new Latlng(-90, 180), zoom);
		setLabelEngine(new LabelSTRTreeEngine());
	}
//	
//	public MapContext(MapContext context) {
//		// TODO Auto-generated constructor stub
//		this.sceneCRS = context.getSceneCRS();
//		this.spatialReference = context.getSpatialReference();
//		this.sceneEnvelope = context.getSceneEnvelope();
//		this.mapEnvelop = context.getMapEnvelop();
//		this.center = context.getCenter();
//		this.centerPoint = context.getCenterPoint();
//		this.zoom = context.getZoom();
//		this.zoomSnap = context.getZoomSnap();
//		this.zoomDelta = context.getZoomDelta();
//		this.minZoom = context.getMinZoom();
//		this.maxZoom = context.getMaxZoom();
//		this.isZoomScale = context.isZoomScale();
//		this.zoomAnimation = context.isZoomAnimation();
//		this.zoomAnimationThreshold = context.getZoomAnimationThreshold();
//		this.fadeAnimation = context.isFadeAnimation();
//		this.markerZoomAnimation = context.isMarkerZoomAnimation();
//		this.width = context.getWidth();
//		this.height = context.getHeight();
//		this.sceneSize = context.getSceneSize();
//		this.dragX = context.getDragX();
//		this.dragY = context.getDragY();
//		isMainThread = false;
//	}
	
	public MapContext newContext() {
//		//Java对象转化为JSON对象
//		JSONObject jsonObject = (JSONObject) JSON.toJSON(this);
//		System.out.println(jsonObject.toJSONString());
//		//JSON对象转换成Java对象
//		MapContext newc = JSONObject.toJavaObject(jsonObject, MapContext.class);
//		JSONObject newJson = (JSONObject) JSON.toJSON(newc);
//		System.out.println(newJson.toJSONString());
//		return newc;
		
		MapContext context = new MapContext();
		context.sceneCRS = new SceneEPSG3857CRS();
		if(this.getSpatialReference()!=null) {
			context.spatialReference = new SpatialReference(this.getSpatialReference().getName());
		} else {
			context.spatialReference = null;
		}
		context.sceneEnvelope = new Envelope(this.getSceneEnvelope());
		//context.mapEnvelop = new Envelope(this.getMapEnvelop());
		context.center = (Latlng) this.getCenter().clone();
		context.centerPoint = (ScenePoint) this.getCenterPoint().clone();
		context.sceneSize = (ScenePoint) this.getSceneSize().clone();
		context.zoom = this.getZoom();
		context.zoomSnap = this.getZoomSnap();
		context.zoomDelta = this.getZoomDelta();
		context.minZoom = this.getMinZoom();
		context.maxZoom = this.getMaxZoom();
		context.isZoomScale = this.isZoomScale();
		context.zoomAnimation = this.isZoomAnimation();
		context.zoomAnimationThreshold = this.getZoomAnimationThreshold();
		context.fadeAnimation = this.isFadeAnimation();
		context.markerZoomAnimation = this.isMarkerZoomAnimation();
		context.width = this.getWidth();
		context.height = this.getHeight();
		context.dragX = this.getDragX();
		context.dragY = this.getDragY();
		context.isMainThread = false;
		return context;
        
	}
	
	@Override
	public Object clone() {
		try {
			MapContext context = (MapContext) super.clone();
			context.sceneCRS = new SceneEPSG3857CRS();
			if(context.getSpatialReference()!=null) { 
				context.spatialReference = new SpatialReference(context.getSpatialReference().getName());
			} else {
				context.spatialReference = null;
			}
			context.sceneEnvelope = new Envelope(context.getSceneEnvelope());
			//context.mapEnvelop = new Envelope(context.getMapEnvelop());
			context.center = (Latlng) context.getCenter().clone();
			context.centerPoint = (ScenePoint) context.getCenterPoint().clone();
			context.sceneSize = (ScenePoint) context.getSceneSize().clone();
			context.isMainThread = false;
			return context; // return the clone
		} catch (CloneNotSupportedException e) {
			Assert.shouldNeverReachHere("this shouldn't happen because this class is Cloneable");
			return null;
		}
	}
	
	public void drag(float x, float y) {
		dragX = x;
		dragY = y;
	}
	
	public Coordinate currentDrag() {
		return new Coordinate(dragX, dragY);
	}
	
	/**
	 * 鼠标位置的坐标
	 * @param x
	 * @param y
	 * @return
	 */
	public Latlng mouseCoordinate(float x, float y) {
		ScenePoint ct = getCenterPoint();
		Coordinate t = new Coordinate(-ct.getX() + getWidth() / 2 + dragX,
				-ct.getY() + getHeight() / 2 + dragY);
		double tx = x - t.x;
		double ty = y - t.y;
		return getSceneCRS().pointToLatLng(new ScenePoint(tx, ty), getZoom());
		//System.out.println("mouse latlng: " + latlng.toString());
	}
	
	public void resize(int width, int height) {
		if(this.width!=width || this.height!=height) {
			this.width = width;
			this.height = height;
			resetSceneEnvelope();
		}
	}
	
	public void resetSceneEnvelope() {
		//System.out.println("w: " + width + ", h: " + height);
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
	
	public void resetCenterForDrag() {
		if(dragX == 0 && dragY==0) return;
		centerPoint.subtract(new ScenePoint(dragX, dragY));
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
	
	/**
	 * 场景坐标转屏幕坐标
	 * @param point
	 * @return
	 */
	public ScenePoint sceneToView(ScenePoint point) {
		ScenePoint ct = getCenterPoint();
		point.translate(-ct.getX() + getWidth() / 2, -ct.getY() + getHeight() / 2);
		return point;
	}
	
	/**
	 * 屏幕坐标转场景坐标
	 * @param point
	 * @return
	 */
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
		this.zoom = this.zoom > maxZoom ? (maxZoom - zoomDelta) : this.zoom;
		ScenePoint ct = getSceneCRS().latLngToPoint(center, this.zoom);
		setCenterPoint(ct);
		resetSceneEnvelope();
		sceneSize = getSceneCRS().latLngToPoint(new Latlng(-90, 180), zoom);
	}
	
	/**
	 * 场景设置
	 * @param center
	 * @param zoom
	 */
	public void setView(Latlng center, float zoom) {
		this.center = center;
		this.zoom = zoom;
		this.zoom = this.zoom < minZoom ? minZoom : this.zoom;
		this.zoom = this.zoom > maxZoom ? maxZoom : this.zoom;
		ScenePoint ct = getSceneCRS().latLngToPoint(this.center, this.zoom);
		setCenterPoint(ct);
		resetSceneEnvelope();
		sceneSize = getSceneCRS().latLngToPoint(new Latlng(-90, 180), this.zoom);
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
	
	public void fitBounds(Envelope envelope) {
		fitBounds(envelope, null, null, null);
	}
	
	public void fitBounds(Envelope envelope, Float maxZoom, ScenePoint paddingTopLeft, ScenePoint paddingBottonRight) {
		if(envelope==null || envelope.isNull()) return;
		paddingTopLeft = paddingTopLeft==null ? new ScenePoint(0, 0) : paddingTopLeft;
		paddingBottonRight = paddingBottonRight==null ? new ScenePoint(0, 0) : paddingBottonRight;
		ScenePoint nsp = (ScenePoint) paddingTopLeft.clone();
		//System.out.println("1-nsp: " + nsp);
		float fzoom = getBoundsZoom(envelope, false, nsp.plus(paddingBottonRight));
		//System.out.println("9-fzoom: " + fzoom);
		fzoom = maxZoom!=null ? Math.min(maxZoom, fzoom) : fzoom;
		//System.out.println("10-fzoom: " + fzoom);
		ScenePoint paddingOffset = paddingBottonRight.subtract(paddingTopLeft).divideBy(2);
		//System.out.println("11-paddingOffset: " + paddingOffset);
		ScenePoint swPoint = latLngToPoint(new Latlng(envelope.getMinY(), envelope.getMinX()), fzoom);
		ScenePoint nePoint = latLngToPoint(new Latlng(envelope.getMaxY(), envelope.getMaxX()), fzoom);
		//System.out.println("12-swPoint: " + swPoint);
		//System.out.println("13-nePoint: " + nePoint);
		Latlng fcenter = pointToLatLng(swPoint.plus(nePoint).divideBy(2).plus(paddingOffset), fzoom);
		//System.out.println("14-fcenter: " + fcenter);
		setView(fcenter, fzoom);
	}

	private float getBoundsZoom(Envelope envelope, boolean inside, ScenePoint padding) {
		// TODO Auto-generated method stub
		padding = padding==null ? new ScenePoint(0, 0) : padding;
		float fzoom = getZoom();
		float min = getMinZoom();
		float max = getMaxZoom();
		Latlng nw =	 new Latlng(envelope.getMaxY(), envelope.getMinX());
		Latlng se = new Latlng(envelope.getMinY(), envelope.getMaxX());
		//System.out.println("2-nw: " + nw);
		//System.out.println("3-se: " + se);
		ScenePoint size = new ScenePoint(width, height).subtract(padding);
		//System.out.println("4-size: " + size);
		ScenePoint boundsSize = latLngToPoint(se, fzoom).subtract(latLngToPoint(nw, fzoom));
		//System.out.println("5-boundsSize: " + boundsSize);
		//snap = L.Browser.any3d ? this.options.zoomSnap : 1;
		float snap = getZoomSnap();
		double scale = Math.min(size.x / boundsSize.x, size.y / boundsSize.y);
		//System.out.println("6-scale: " + scale);
		fzoom = getScaleZoom(scale, fzoom);
		//System.out.println("7-fzoom: " + fzoom);
		if (snap > 0) {
			fzoom = Math.round(fzoom / (snap / 100)) * (snap / 100); // don't jump if within 1% of a snap level
			fzoom = (float) (inside ? Math.ceil(fzoom / snap) * snap : Math.floor(fzoom / snap) * snap);
			//System.out.println("8-fzoom: " + fzoom);
		}
		return Math.max(min, Math.min(max, fzoom));
	}

	private float getScaleZoom(double scale, Float fromZoom) {
		// TODO Auto-generated method stub
		fromZoom = fromZoom == null ? this.zoom : fromZoom;
		Double fzoom = sceneCRS.zoom(scale * sceneCRS.scale(fromZoom));
		return fzoom.isNaN() ? Float.POSITIVE_INFINITY : fzoom.floatValue();
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

//	public Envelope getMapEnvelop() {
//		return mapEnvelop;
//	}
//
//	public void setMapEnvelop(Envelope mapEnvelop) {
//		this.mapEnvelop = mapEnvelop;
//	}

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

	public boolean isZoomScale() {
		return isZoomScale;
	}

	public void setZoomScale(boolean isZoomScale) {
		this.isZoomScale = isZoomScale;
	}

	public float getDragX() {
		return dragX;
	}

	public float getDragY() {
		return dragY;
	}

	public boolean isMainThread() {
		return isMainThread;
	}

	public void setMainThread(boolean isMainThread) {
		this.isMainThread = isMainThread;
	}

	public LabelEngine getLabelEngine() {
		return labelEngine;
	}

	public void setLabelEngine(LabelEngine labelEngine) {
		this.labelEngine = labelEngine;
	}
}
