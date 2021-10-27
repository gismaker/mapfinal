package com.mapfinal.render;

import com.mapfinal.map.MapContext;

/**
 * 标注引擎
 * @author yangyong
 *
 */
public interface LabelEngine {
	
	/**
	 * 是否加入渲染
	 * @param context
	 * @param engine
	 * @param label
	 * @return
	 */
	boolean renderable(MapContext context, RenderEngine engine, Label label);
	
	/**
	 * 清空渲染占位
	 */
	void clear();
}
