package com.mapfinal.render;

import com.mapfinal.event.Event;
import com.mapfinal.map.Feature;

public class FeatureRo<K> extends RenderObject {

	protected Feature<K> feature;
	
	public FeatureRo() {
	}
	
	public FeatureRo(Feature<K> feature) {
		this.feature = feature;
	}
	
	@Override
	public void draw(Event event, RenderEngine engine, Renderer renderer) {
		// TODO Auto-generated method stub
		if(feature!=null) {
			engine.renderFeature(event, renderer, feature);
		}
	}

	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub

	}

	public Feature<K> getFeature() {
		return feature;
	}

	public void setFeature(Feature<K> feature) {
		this.feature = feature;
	}
}
