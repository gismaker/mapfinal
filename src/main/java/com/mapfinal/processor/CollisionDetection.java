package com.mapfinal.processor;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Polygon;

/**
 * 碰撞检测
 * @author yangyong
 * @param <M>
 */
public class CollisionDetection {
	
	/**
	 * 是否碰撞
	 * @param rect0
	 * @param rect1
	 * @return
	 */
	public boolean isCollision(Geometry geom0, Geometry geom1) {
		return  geom0.intersects(geom1) || geom0.contains(geom1);
	}
	
	public boolean isCollision(Geometry geom0, Geometry geom1, double distance) {
		return isCollision(geom0, geom1) || geom0.isWithinDistance(geom1, distance);
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
	
	public boolean isCollision(Polygon polygon0, Polygon polygon1, double distance) {
		return isCollision(polygon0, polygon1) || polygon0.isWithinDistance(polygon1, distance);
	}
	
	/**
	 * 线是否碰撞
	 * @param rect0
	 * @param rect1
	 * @return
	 */
	public boolean isCollision(LineString line0, LineString line1) {
		return  line0.intersects(line1) || line0.contains(line1);
	}
	
	public boolean isCollision(LineString line0, LineString line1, double distance) {
		return isCollision(line0, line1) || line0.isWithinDistance(line1, distance);
	}
	
}
