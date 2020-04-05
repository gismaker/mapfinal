package com.mapfinal.map;

import com.mapfinal.converter.SpatialReference;
import com.mapfinal.event.Event;
import com.mapfinal.render.Renderer;

/**
 * https://blog.csdn.net/Smart3S/article/details/81121349
 * @author yangyong
 *
 */
public abstract class AbstractLayer implements Layer {

	private String name;
	private String title;
	private float opacity = 1.0f;
	private float minZoom = 0;
	private float maxZoom = 18;
	private boolean visible = true;
	/**
	 * 坐标系，应用于转换器
	 */
	private SpatialReference spatialReference;
	/**
	 * 渲染器
	 */
	private Renderer renderer;
	/**
	 * 父类
	 */
	private Layer parent;
	
	public MapContext getMapContext(Event event) {
		if(event!=null) {
			return event.get("map");
		}
		return null;
	}

	@Override
	public void addTo(LayerGroup layerGroup) {
		layerGroup.add(this);
		this.setParent(layerGroup);
	}
	
	@Override
	public void setParent(Layer layer) {
		// TODO Auto-generated method stub
		parent = layer;
	}

	@Override
	public Layer getParent() {
		// TODO Auto-generated method stub
		return parent;
	}
	
	@Override
	public boolean isDrawable() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public void drawFinished(int id, float percent) {
		// TODO Auto-generated method stub
		if (parent != null) {
			parent.drawFinished(id, percent);
        }
	}
	
	@Override
	public boolean handleEvent(Event event) {
		// TODO Auto-generated method stub
		if(renderer!=null && Renderer.EVENT_CANCELDRAW.equals(event.getAction())) {
			renderer.cancelDraw(event);
		}
		return false;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getTitle() {
		return title;
	}
	
	@Override
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public SpatialReference getSpatialReference() {
		return spatialReference;
	}
	
	public void setSpatialReference(SpatialReference spatialReference) {
		this.spatialReference = spatialReference;
	}
	
	@Override
	public Renderer getRenderer() {
		return renderer;
	}
	
	@Override
	public void setRenderer(Renderer renderer) {
		this.renderer = renderer;
	}
	
	@Override
	public float getOpacity() {
		return opacity;
	}
	
	@Override
	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}
	
	@Override
	public float getMinZoom() {
		return minZoom;
	}
	
	@Override
	public void setMinZoom(float minZoom) {
		this.minZoom = minZoom;
	}
	
	@Override
	public float getMaxZoom() {
		return maxZoom;
	}
	
	@Override
	public void setMaxZoom(float maxZoom) {
		this.maxZoom = maxZoom;
	}
	
	@Override
	public boolean isVisible() {
		return visible;
	}
	
	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
