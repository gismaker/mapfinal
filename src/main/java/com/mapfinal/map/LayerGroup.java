package com.mapfinal.map;

import java.util.ArrayList;
import java.util.List;

import com.mapfinal.event.Event;
import com.mapfinal.render.RenderEngine;
import com.mapfinal.render.SceneNode;

public class LayerGroup implements SceneNode {

	private List<Layer> layers;
	private String name;
	private boolean bVisible = true;
	
	public LayerGroup() {
		setLayers(new ArrayList<Layer>());
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
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean isDrawable() {
		// TODO Auto-generated method stub
		return false;
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
	
	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return bVisible;
	}

	@Override
	public void setVisible(boolean bVisible) {
		// TODO Auto-generated method stub
		this.bVisible = bVisible;
	}
}
