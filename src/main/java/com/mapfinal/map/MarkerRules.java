package com.mapfinal.map;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Polygon;

/**
 * 标注碰撞检测
 * @author yangyong
 *
 */
public class MarkerRules {

	protected List<Polygon> markers;
	
	public MarkerRules() {
		
	}
	
	public void add(Polygon polygon) {
		if(markers==null) {
			markers = new ArrayList<Polygon>();
		}
		markers.add(polygon);
	}
	
	/**
	 * 多边形是否碰撞
	 * @param rect0
	 * @param rect1
	 * @return
	 */
	public boolean isCollision(Polygon polygon0, Polygon polygon1) {
		return  polygon0.intersects(polygon1) || polygon0.contains(polygon1);
	}
	
	
}
