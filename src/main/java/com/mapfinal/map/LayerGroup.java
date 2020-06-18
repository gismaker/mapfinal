package com.mapfinal.map;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Envelope;

import com.mapfinal.event.Event;
import com.mapfinal.render.RenderEngine;
import com.mapfinal.render.SceneNode;

public class LayerGroup extends AbstractLayer implements SceneNode {

	private List<Layer> layers;
	
	public LayerGroup() {
		setLayers(new ArrayList<Layer>());
	}
	
	public int add(Layer node) {
		boolean flag = layers.add(node);
		int id = flag ? layers.size()-1 : -1;
		node.setId(id);
		return id;
	}
	
	public void add(int index, Layer node) {
		node.setId(index);
		layers.add(index, node);
	}
	
	public Layer get(int index) {
		if(index >=0 && index < layers.size()) {
			return layers.get(index);
		}
		return null;
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
	
	public void moveTop(Layer layer) {
		layers.remove(layer);
		layers.add(0, layer);
	}
	
	public void moveEnd(Layer layer) {
		layers.remove(layer);
		layers.add(layer);
	}
	
	public List<Layer> getLayers() {
		return layers;
	}

	public void setLayers(List<Layer> layers) {
		this.layers = layers;
	}

	@Override
	public boolean isDrawable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void draw(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		if(!isVisible()) return;
		for (int i = 0; i < layers.size(); i++) {
			layers.get(i).draw(event, engine);
		}
	}

	@Override
	public boolean handleEvent(Event event) {
		// TODO Auto-generated method stub
		//倒序
		for (int i = layers.size(); i > 0; i--) {
			boolean flag = layers.get(i-1).handleEvent(event);
			if(flag) return flag;
		}
		return false;
	}
	
	@Override
	public Envelope getEnvelope() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void drawFinished(int id, float percent) {
		// TODO Auto-generated method stub
	}

}
