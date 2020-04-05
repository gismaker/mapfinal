package com.mapfinal.render;

import com.mapfinal.event.Event;
import com.mapfinal.render.style.Symbol;

public class SimpleRenderer implements Renderer {

	private Symbol symbol;
	private Renderable renderable;
	
	public SimpleRenderer(Symbol symbol) {
		this.setSymbol(symbol);
	}
	
	public SimpleRenderer(Symbol symbol, Renderable renderable) {
		this.setSymbol(symbol);
		this.setRenderable(renderable);
	}

	public Symbol getSymbol() {
		return symbol;
	}

	public void setSymbol(Symbol symbol) {
		this.symbol = symbol;
	}

	@Override
	public Renderable getRenderable() {
		// TODO Auto-generated method stub
		return renderable;
	}

	@Override
	public void setRenderable(Renderable renderable) {
		this.renderable = renderable;
	}

	@Override
	public void draw(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		if(renderable!=null) renderable.draw(event, engine, this);
	}

	@Override
	public void cancelDraw(Event event) {
		// TODO Auto-generated method stub
		if(renderable!=null) renderable.cancelDraw(event);
	}
}
