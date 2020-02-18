package com.mapfinal.geometry;

/**
 * GPS定位信息
 * @author yangyong
 *
 */
public interface Location {
	/**
	 * 纬度
	 * 
	 * @return
	 */
	public double getLatitude();

	/**
	 * 经度
	 * 
	 * @return
	 */
	public double getLongitude();

	/**
	 * 速度
	 * 
	 * @return
	 */
	public double getSpeed();

}
