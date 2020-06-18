package com.mapfinal.map.layer;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;

import com.mapfinal.event.Event;
import com.mapfinal.map.AbstractLayer;
import com.mapfinal.map.Feature;
import com.mapfinal.map.FeatureList;
import com.mapfinal.render.RenderEngine;

/**
 * https://blog.csdn.net/qq_26222859/article/details/48677373
 * @author yangyong
 *
 */
public class GraphicsLayer extends AbstractLayer {

	private FeatureList<Long> graphics;
	/**
	 * 被选中的编辑对象
	 */
	private Feature<Long> editor;
	private int editIndex = -1;
	private int editMode = 0;
	
	
	public GraphicsLayer() {
		graphics = new FeatureList<Long>();
	}
	
	@Override
	public Envelope getEnvelope() {
		// TODO Auto-generated method stub
		return graphics.getEnvelope();
	}

	@Override
	public void draw(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		for (int i = 0; i < graphics.size(); i++) {
			if(i==editIndex) continue;
			Feature<Long> g = graphics.getFeature(i);
			
		}
	}

	@Override
	public boolean handleEvent(Event event) {
		// TODO Auto-generated method stub
		int count = graphics.size();
		if(count < 1) return false;
		if("graphics:selected".equals(event.getAction()) && editMode==1) {
			//选择事件
			Coordinate coordinate = event.get("point");
			for (int i = count-1; i > 0; i--) {
				Feature<Long> g = graphics.getFeature(i);
				boolean flag = g.contains(coordinate);
				if(flag) {
					editIndex = i;
					editor = g;
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 增加一个graphic
	 * @param graphic
	 */
	public void add(Feature<Long> graphic) {
		if(graphic.getId() < graphics.featureCount()) {
			graphic.setId(graphics.featureCount());
		}
		graphics.addFeature(graphic);
	}
		
	/**
	 * 清除所有graphics
	 */
	public void clear() {
		graphics.clearFeature();
	}
	
	/**
	 * 删除指定的graphic
	 * @param graphic
	 */
	public void remove(Feature<Long> graphic) {
		graphics.removeFeature(graphic);
	}

	public Feature<Long> getEditor() {
		return editor;
	}

	public void setEditor(Feature<Long> editor) {
		this.editor = editor;
	}

}
