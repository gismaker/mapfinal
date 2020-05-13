package com.mapfinal.map.layer;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;

import com.mapfinal.event.Event;
import com.mapfinal.map.AbstractLayer;
import com.mapfinal.map.Graphic;
import com.mapfinal.render.RenderEngine;

/**
 * https://blog.csdn.net/qq_26222859/article/details/48677373
 * @author yangyong
 *
 */
public class GraphicsLayer extends AbstractLayer {

	private List<Graphic> graphics;
	private String geoType;//图形类型，点，线，面
	
	public GraphicsLayer() {
		graphics = new ArrayList<Graphic>();
	}
	
	@Override
	public Envelope getEnvelope() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		for (int i = 0; i < graphics.size(); i++) {
			Graphic g = graphics.get(i);
			
		}
	}

	@Override
	public boolean handleEvent(Event event) {
		// TODO Auto-generated method stub
		if("graphics:selected".equals(event.getAction())) {
			//选择事件
			Coordinate coordinate = event.get("point");
			for (int i = 0; i < graphics.size(); i++) {
				Graphic g = graphics.get(i);
				boolean flag = g.contains(coordinate);
				if(flag) {
					g.setEditMode(true);
					break;
				}
			}
			
		}
		return false;
	}
	
	/**
	 * 增加一个graphic
	 * @param graphic
	 */
	public void add(Graphic graphic) {
		graphics.add(graphic);
	}
		
	/**
	 * 清除所有graphics
	 */
	public void clear() {
		graphics.clear();
	}
	
	/**
	 * 将一个特定的graphic移到顶部
	 * @param graphic
	 */
	public void moveToTop(Graphic graphic) {
		graphics.remove(graphic);
		graphics.add(0, graphic);
	}
	
	/**
	 * 删除指定的graphic
	 * @param graphic
	 */
	public void remove(Graphic graphic) {
		graphics.remove(graphic);
	}

}
