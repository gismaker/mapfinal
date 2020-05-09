package com.mapfinal.render;

import com.mapfinal.event.Event;
import com.mapfinal.render.style.Symbol;

/**
 * 渲染器，管理渲染样式和渲染操作类
 * @author yangyong
 */
public interface Renderer {
	
	public static final String EVENT_CANCELDRAW = "render:handleEvent";

	public Symbol getSymbol();
	public void setSymbol(Symbol symbol);
	
	public Renderable getRenderable();
	public void setRenderable(Renderable renderable);
	
	public void draw(Event event, RenderEngine engine);
	public void handleEvent(Event event);
}
