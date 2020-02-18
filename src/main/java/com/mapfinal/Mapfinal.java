package com.mapfinal;

import com.mapfinal.geometry.Latlng;
import com.mapfinal.map.MapView;
import com.mapfinal.render.SceneGraph;

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
	private MapfinalFactory factory;
	
	private static final Mapfinal me = new Mapfinal();
	private Mapfinal() {}
	public static Mapfinal me() {
		return me;
	}
	
	public void init(SceneGraph scene, MapfinalFactory factory) {
		this.factory = factory;
		this.factory.init();
		this.scene = scene;
		scene.addNode(map);
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
	public MapfinalFactory getFactory() {
		return factory;
	}
	public String getCacheFolder() {
		return cacheFolder;
	}
	public void setCacheFolder(String cacheFolder) {
		this.cacheFolder = cacheFolder;
	}
}
