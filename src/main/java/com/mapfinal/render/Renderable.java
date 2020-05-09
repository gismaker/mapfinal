package com.mapfinal.render;

import com.mapfinal.event.Event;

/**
 * 渲染方法
 * @author yangyong
 *
 */
public interface Renderable {
	void draw(Event event, RenderEngine engine, Renderer renderer);
	void handleEvent(Event event);
}
 