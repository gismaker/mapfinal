package com.mapfinal.render;

import com.mapfinal.event.Event;

public interface SceneNode {

	String getName();
	boolean isDrawable();
	boolean isVisible();
	void setVisible(boolean bVisible);
	
	void onRender(Event event, RenderEngine engine);
	void onEvent(Event event);
}
