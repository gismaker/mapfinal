package com.mapfinal.render;

import java.util.Map;

import org.locationtech.jts.geom.Geometry;

import com.mapfinal.event.Event;
import com.mapfinal.map.Feature;
import com.mapfinal.render.style.Symbol;

public class SimpleRenderer implements Renderer {

	private Symbol symbol;
//	private Renderable renderable;
	
	public SimpleRenderer(Symbol symbol) {
		this.setSymbol(symbol);
	}
	
//	public SimpleRenderer(Symbol symbol, Renderable renderable) {
//		this.setSymbol(symbol);
//		this.setRenderable(renderable);
//	}

	public Symbol getSymbol() {
		return symbol;
	}

	public void setSymbol(Symbol symbol) {
		this.symbol = symbol;
	}

	@Override
	public void draw(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
//		if(renderable!=null) renderable.draw(event, engine, this);
		
	}

	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
//		if(renderable!=null) renderable.handleEvent(event);
	}

	@Override
	public Symbol getSymbol(Feature feature) {
		// TODO Auto-generated method stub
		return getSymbol();
	}

	@Override
	public Symbol getSymbol(Geometry geometry, Map<String, Object> attributes) {
		// TODO Auto-generated method stub
		return getSymbol();
	}
}
