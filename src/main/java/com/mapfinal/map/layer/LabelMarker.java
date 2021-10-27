package com.mapfinal.map.layer;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Envelope;

import com.mapfinal.event.Event;
import com.mapfinal.event.EventListener;
import com.mapfinal.map.AbstractLayer;
import com.mapfinal.render.Label;
import com.mapfinal.render.RenderEngine;

public class LabelMarker extends AbstractLayer {

	private List<Label> labels = null;
	
	public LabelMarker() {
	}
	
	public void addLabel(Label label) {
		if(labels==null) {
			labels = new ArrayList<Label>();
		}
		labels.add(label);
	}
	
	@Override
	public Envelope getEnvelope() {
		return null;
	}

	@Override
	public void draw(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		if(!isDrawable()) return;
		if(!isVisible()) return;
		if(!event.isRender()) return;
		if(labels==null) return;
		for (Label label : labels) {
			engine.renderLabel(event, label);;
		}
	}
	
	@Override
	public boolean handleEvent(Event event) {
		// TODO Auto-generated method stub
		if(event.isAction("mouseClick")) {
			for (Label label : labels) {
				boolean flag = label.handleEvent(event, this);
				if(flag) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void addClick(EventListener listener) {
		addListener(getEventAction("click"), listener);
	}
	
	public void removeClick(EventListener listener) {
		removeListener(getEventAction("click"), listener);
	}
	
	public void clearClick() {
		clearListener(getEventAction("click"));
	}
}
