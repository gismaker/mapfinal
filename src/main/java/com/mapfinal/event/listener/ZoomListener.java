package com.mapfinal.event.listener;

import com.mapfinal.event.Event;
import com.mapfinal.event.EventListener;

public interface ZoomListener extends EventListener {

	public boolean onEvent(Event event);
}
