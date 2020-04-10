package com.mapfinal.processor;

import java.util.List;

import org.locationtech.jts.geom.CoordinateSequence;

import com.mapfinal.event.Event;

/**
 * 抽稀压缩算法接口
 * 
 * @author yangyong
 *
 */
public interface GeoCompress {

	public enum Type {
		/**
		 * 按照步长剔除点
		 */
		STEP,
		/**
		 * 均匀分布提取点，适用于点集合
		 */
		DIST,
		/**
		 * 道格拉斯·普客法（DP法）压缩矢量多边形
		 */
		DP,
		/**
		 * 未知
		 */
		UNKNOWN,
		NULL		
	}
	
	//public void excute(Collection points, Callback callback);

	public List<Integer> excute(Event event, CoordinateSequence points, Double tolerance);
}
