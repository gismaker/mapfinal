package com.mapfinal.event;

public interface EventObject {

	/**
	 * 根据事件名称生成对象的事件Action<br>
	 * 比如图层
	 * @param eventName
	 * @return
	 */
	String getEventAction(String eventName);
	/**
	 * 移除监听
	 * @param eventAction
	 * @param listener
	 */
	void removeListener(String eventAction, EventListener listener);
	/**
	 * 加入监听
	 * @param eventAction
	 * @param listener
	 */
	void addListener(String eventAction, EventListener listener);
	/**
	 * 发送监听事件
	 * @param event
	 * @return
	 */
	boolean sendEvent(final Event event);
}
