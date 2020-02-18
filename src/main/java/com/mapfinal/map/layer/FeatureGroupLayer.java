package com.mapfinal.map.layer;

import java.util.List;

import org.locationtech.jts.geom.Envelope;

import com.mapfinal.event.Event;
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
	public void onRender(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		for (int i = 0; i < layers.size(); i++) {
			layers.get(i).onRender(event, engine);
		}
	}

	@Override
	public void onEvent(Event event) {
		// TODO Auto-generated method stub
		for (int i = 0; i < layers.size(); i++) {
			layers.get(i).onEvent(event);
		}
	}
}
