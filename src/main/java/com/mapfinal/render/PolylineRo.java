package com.mapfinal.render;

import com.mapfinal.event.Event;
import com.mapfinal.kit.ColorKit;
import com.mapfinal.map.Feature;
import com.mapfinal.render.style.LineSymbol;
import com.mapfinal.render.style.SimpleLineSymbol;

public class PolylineRo extends FeatureRo {

	@Override
	public void draw(Event event, RenderEngine engine, Renderer renderer) {
		// TODO Auto-generated method stub
		LineSymbol symbol = null;
		if(renderer==null || !(renderer.getSymbol() instanceof LineSymbol)) {
			symbol = new SimpleLineSymbol(ColorKit.RED, 1);
		} else {
			symbol = (LineSymbol) renderer.getSymbol();
		}
		engine.renderPolyline(event, symbol, event.get("geometry"));
	}

	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		if(event.isAction("editMode")) {
			setEditMode(true);
		} else if(event.isAction("normalMode")) {
			setEditMode(false);
		} 
	}

}
