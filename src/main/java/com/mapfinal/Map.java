package com.mapfinal;

import com.mapfinal.event.EventKit;
import com.mapfinal.event.EventManager;
import com.mapfinal.geometry.Latlng;
import com.mapfinal.map.Layer;
import com.mapfinal.map.MapContext;
import com.mapfinal.map.MapView;
import com.mapfinal.render.SceneGraph;
import com.mapfinal.render.SceneRedrawListener;
import com.mapfinal.resource.image.ImageHandle;

/**
 * 参考：
 * 天地图Android-SDK API
 * http://www.njmap.gov.cn/geomap-api/apidoc/androidJavaApiDoc/index.html?overview-summary.html
 * ArcGIS Runtime SDK for Java 100.7.0
 * https://developers.arcgis.com/java/latest/api-reference/reference/index.html
 * @author yangyong
 *
 */
public class Map {

	private SceneGraph scene;
	private MapView view = new MapView();
	private String cacheFolder;
	private Platform platform;
	
	public Map(SceneGraph scene, Platform platform) {
		this.platform = platform;
		this.platform.init();
		this.platform.initGeometryFactory();
		
		this.scene = scene;
		scene.addNode(view);
		
		this.cacheFolder = this.platform.getCacheFolder();
		System.out.println("cacheFolder: " + cacheFolder);
		EventManager.me().registerListener(SceneRedrawListener.class);
	}
	
	public void addLayer(Layer layer) {
		view.add(layer);
	}
	
	public void setCenter(Latlng center) {
		view.setCenter(center);
	}
	
	public void setZoom(float zoom) {
		view.setZoom(zoom);
	}
	
	public void resize(int width, int height) {
		view.resize(width, height);
	}
	
	public MapView getMap() {
		return view;
	}

	public SceneGraph getScene() {
		return scene;
	}
	
	public String getCacheFolder() {
		return cacheFolder;
	}
	public void setCacheFolder(String cacheFolder) {
		this.cacheFolder = cacheFolder;
	}
	
	public Platform platform() {
		return platform;
	}
	
	public MapView view() {
		return view;
	}
	
	public MapContext context() {
		return view.getContext();
	}
	
	public SceneGraph scene() {
		return scene;
	}
	
	public ImageHandle<?> imageHandle() {
		return getPlatform().getImageHandle();
	}
	
	public void redraw() {
		EventKit.sendEvent("redraw");
	}
	public Platform getPlatform() {
		return platform;
	}
	public void setPlatform(Platform platform) {
		this.platform = platform;
	}
	
}
