package com.mapfinal.event.listener;

import com.mapfinal.event.Event;
import com.mapfinal.event.EventListener;

public interface MapEventListener extends EventListener  {

	public boolean onEvent(Event event);

	/**
	 * 单击
	 * @param point
	 * @return
	 */
	boolean onSingleTap(Event point);
	/**
	 * 双击
	 * @param point
	 * @return
	 */
	boolean onDoubleTap(Event point);

	/**
	 * 触摸
	 * @param event
	 * @return
	 */
	boolean onTouch(Event event);
	/**
	 * 拖动
	 * @param fromTo
	 * @return
	 */
	boolean onDragPointerMove(Event fromTo);

	/**
	 * 拖动抬起
	 * @param fromTo
	 * @return
	 */
	boolean onDragPointerUp(Event fromTo);

	/**
	 * 长按
	 * @param point
	 */
	void onLongPress(Event point);

	/**
	 * 多指点击
	 * @param event
	 */
	void onMultiPointersSingleTap(Event event);

	/**
	 * 多指按下
	 * @param event
	 * @return
	 */
	boolean onPinchPointersDown(Event event);

	/**
	 * 多指移动
	 * @param event
	 * @return
	 */
	boolean onPinchPointersMove(Event event);

	/**
	 * 多指抬起
	 * @param event
	 * @return
	 */
	boolean onPinchPointersUp(Event event);

	
}
