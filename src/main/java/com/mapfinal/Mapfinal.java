package com.mapfinal;

import com.mapfinal.event.EventKit;
import com.mapfinal.event.EventManager;
import com.mapfinal.geometry.Latlng;
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
public class Mapfinal {

	private SceneGraph scene;
	private MapView map = new MapView();
	private String cacheFolder;
	private Platform platform;
	
	private static final Mapfinal me = new Mapfinal();
	private Mapfinal() {}
	public static Mapfinal me() {
		return me;
	}
	
	public void init(SceneGraph scene, Platform platform) {
		this.platform = platform;
		this.platform.init();
		this.platform.initGeometryFactory();
		
		this.scene = scene;
		scene.addNode(map);
		
		this.cacheFolder = this.platform.getCacheFolder();
		System.out.println("cacheFolder: " + cacheFolder);
		EventManager.me().registerListener(SceneRedrawListener.class);
	}
	
	public void init(Platform platform) {
		this.platform = platform;
		this.platform.init();
		this.platform.initGeometryFactory();
		
		this.cacheFolder = this.platform.getCacheFolder();
		System.out.println("cacheFolder: " + cacheFolder);
		EventManager.me().registerListener(SceneRedrawListener.class);
	}
	
	public void setCenter(Latlng center) {
		map.setCenter(center);
	}
	
	public void setZoom(float zoom) {
		map.setZoom(zoom);
	}
	
	public void resize(int width, int height) {
		map.resize(width, height);
	}
	
	public MapView getMap() {
		return map;
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
	
	public static Platform platform() {
		return me.platform;
	}
	
	public static MapView map() {
		return me.map;
	}
	
	public static MapContext context() {
		return me.map.getContext();
	}
	
	public static SceneGraph scene() {
		return me.scene;
	}
	
	public static ImageHandle imageHandle() {
		return me().getPlatform().getImageHandle();
	}
	
	public static void redraw() {
		EventKit.sendEvent("redraw");
	}
	public Platform getPlatform() {
		return platform;
	}
	public void setPlatform(Platform platform) {
		this.platform = platform;
	}
	
}
