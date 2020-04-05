package com.mapfinal.map.layer;

import java.util.List;

import org.locationtech.jts.geom.Envelope;

import com.mapfinal.event.Event;
import com.mapfinal.map.AbstractLayer;
import com.mapfinal.map.Layer;
import com.mapfinal.render.RenderEngine;

public class GroupLayer extends AbstractLayer {

	private List<Layer> layers;
	
	@Override
	public Envelope getEnvelope() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void add(Layer node) {
		layers.add(node);
	}
	
	public void add(int index, Layer node) {
		layers.add(index, node);
	}
	
	public void remove(Layer node) {
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
		Layer layer = layers.remove(form);
		layers.add(to, layer);
	}
	
	public List<Layer> getLayers() {
		return layers;
	}

	public void setLayers(List<Layer> layers) {
		this.layers = layers;
	}

	@Override
	public void draw(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
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
