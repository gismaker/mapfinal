package com.mapfinal.render.pick;

import java.util.HashMap;
import java.util.Map;

public class PickManager {

	private Map<String, String> pickEntities;
	private int color = 0;
	
	public void start() {
		pickEntities = new HashMap<String, String>();
		color = 0;
	}
	
	public void stop() {
		pickEntities.clear();
		pickEntities = null;
	}
	/**
	 * 获取实体对象拾取时的渲染颜色
	 * @param id
	 * @return
	 */
	public String getRenderColor(String id) {
		color++;
		return null;
	}
	
	/**
	 * 获取拾取的实体Id
	 * @param color
	 * @return
	 */
	public String getPickId(String color) {
		return null;
	}
}
