package com.mapfinal.event.listener;

import com.mapfinal.event.Event;
import com.mapfinal.event.EventListener;

public interface MapStatusChangedListener extends EventListener {
	public void onEvent(Event event);
}
