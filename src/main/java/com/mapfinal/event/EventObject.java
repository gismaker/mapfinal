package com.mapfinal.event;

public interface EventObject {

	/**
	 * 根据事件名称生成对象的事件Action<br>
	 * 比如图层
	 * @param eventName
	 * @return
	 */
	String getEventAction(String eventName);
	void removeListener(String eventAction, EventListener listener);
	void addListener(String eventAction, EventListener listener);
	boolean sendEvent(final Event event);
}
