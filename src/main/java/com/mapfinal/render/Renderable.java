package com.mapfinal.render;

import com.mapfinal.event.Event;

/**
 * 渲染方法
 * @author yangyong
 *
 */
public interface Renderable {
	void onRender(Event event, RenderEngine engine, Renderer renderer);
	void onEvent(Event event);
}
 