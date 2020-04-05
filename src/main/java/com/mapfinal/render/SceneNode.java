package com.mapfinal.render;

import com.mapfinal.MapfinalObject;
import com.mapfinal.event.Event;

public interface SceneNode extends MapfinalObject {

	String getName();
	boolean isDrawable();
	boolean isVisible();
	void setVisible(boolean bVisible);
	/**
	 * 渲染过程
	 * @param event
	 * @param engine
	 */
	void draw(Event event, RenderEngine engine);
	/**
	 * 事件过程
	 * @param event
	 * @return
	 */
	boolean handleEvent(Event event);
}
