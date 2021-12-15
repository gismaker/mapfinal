package com.mapfinal.render.pick;

import java.util.HashMap;
import java.util.Map;

import com.mapfinal.kit.ColorKit;
import com.mapfinal.kit.StringKit;

public class PickManager {

	private Map<String, String> objIdMaps;
	private Map<Integer, String> pickEntities;
	private int color = ColorKit.RED;
	
	private static final PickManager me = new PickManager();
	private PickManager() {
		pickEntities = new HashMap<Integer, String>();
		objIdMaps = new HashMap<String, String>();
	}
	public static PickManager me() {
		return me;
	}
	
	public void start() {
		setColor(0);
	}
	
	public void stop() {
		pickEntities.clear();
	}
	/**
	 * 获取实体对象拾取时的渲染颜色
	 * @param id
	 * @return
	 */
	public int getRenderColor(String id) {
		setColor(getColor() + 1);
		System.out.println("pick, color: " + color + ", id: " +  id);
		pickEntities.put(color, id);
		return color;
	}
	
	/**
	 * 获取拾取的实体Id
	 * @param color
	 * @return
	 */
	public String getPickId(int color) {
		return pickEntities.get(color);
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	
	/////////////////////////////////////
	
	public boolean registerId(String idName, String className) {
		String cname = objIdMaps.get(idName);
		if(StringKit.notBlank(cname) && cname.equals(className)) {
			return false;
		} else {
			objIdMaps.put(idName, className);
			return true;
		}
	}
	
	public void unRegistreId(String idName) {
		objIdMaps.remove(idName);
	}
	
	public String getRegisterClass(String idName) {
		return objIdMaps.get(idName);
	}
}
