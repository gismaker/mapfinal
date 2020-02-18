package com.mapfinal.event.listener;

import com.mapfinal.event.Event;
import com.mapfinal.event.EventListener;

public interface MapEventListener extends EventListener  {

	public void onEvent(Event event);
	
	boolean onDoubleTap(Event point);

	boolean onDragPointerMove(Event fromTo);

	boolean onDragPointerUp(Event fromTo);

	void onLongPress(Event point);

	void onMultiPointersSingleTap(Event event);

	boolean onPinchPointersDown(Event event);

	boolean onPinchPointersMove(Event event);

	boolean onPinchPointersUp(Event event);

	boolean onTouch(Event event);

	boolean onSingleTap(Event point);
}
