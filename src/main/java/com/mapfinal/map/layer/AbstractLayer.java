package com.mapfinal.map.layer;

import com.mapfinal.converter.SpatialReference;
import com.mapfinal.map.Layer;
import com.mapfinal.map.MapView;
import com.mapfinal.render.Renderer;

/**
 * https://blog.csdn.net/Smart3S/article/details/81121349
 * @author yangyong
 *
 */
public abstract class AbstractLayer implements Layer {

	private String name;
	private String title;
	private boolean visible = true;
	/**
	 * 坐标系，应用于转换器
	 */
	private SpatialReference spatialReference;
	/**
	 * 渲染器
	 */
	private Renderer renderer;
	
	private float opacity = 1.0f;
	
	// 最小显示
	private int minZoom = 0;
	// 最大显示
	private int maxZoom = 18;
	
	public void addTo(MapView map) {
		map.add(this);
	}
	
	@Override
	public boolean isDrawable() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public SpatialReference getSpatialReference() {
		return spatialReference;
	}
	public void setSpatialReference(SpatialReference spatialReference) {
		this.spatialReference = spatialReference;
	}
	public Renderer getRenderer() {
		return renderer;
	}
	public void setRenderer(Renderer renderer) {
		this.renderer = renderer;
	}
	public float getOpacity() {
		return opacity;
	}
	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}
	public int getMinZoom() {
		return minZoom;
	}
	public void setMinZoom(int minZoom) {
		this.minZoom = minZoom;
	}
	public int getMaxZoom() {
		return maxZoom;
	}
	public void setMaxZoom(int maxZoom) {
		this.maxZoom = maxZoom;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
