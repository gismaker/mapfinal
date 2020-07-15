package com.mapfinal.map.layer;

import java.util.List;

import org.locationtech.jts.geom.Envelope;

import com.mapfinal.event.Event;
import com.mapfinal.map.AbstractLayer;
import com.mapfinal.render.RenderEngine;

public class FeatureGroupLayer extends AbstractLayer {

	private List<FeatureLayer> layers;
	
	@Override
	public Envelope getEnvelope() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void add(FeatureLayer node) {
		layers.add(node);
	}
	
	public void add(int index, FeatureLayer node) {
		layers.add(index, node);
	}
	
	public void remove(FeatureLayer node) {
		layers.remove(node);
	}
	
	public void remove(int index) {
		layers.remove(index);
	}
	
	public void clear() {
		layers.clear();
	}
	
	/**
	 * 移动
	 * @param form
	 * @param to
	 */
	public void move(int form, int to) {
		FeatureLayer layer = layers.remove(form);
		layers.add(to, layer);
	}
	
	public List<FeatureLayer> getLayers() {
		return layers;
	}

	public void setLayers(List<FeatureLayer> layers) {
		this.layers = layers;
	}

	@Override
	public void draw(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		if(!isDrawable()) return;
		if(!isVisible()) return;
		for (int i = 0; i < layers.size(); i++) {
			layers.get(i).draw(event, engine);
		}
	}

	@Override
	public boolean handleEvent(Event event) {
		// TODO Auto-generated method stub
		for (int i = 0; i < layers.size(); i++) {
			boolean flag = layers.get(i).handleEvent(event);
			if(flag) return flag;
		}
		return false;
	}
}
