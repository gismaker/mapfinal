package com.mapfinal.resource;

import com.mapfinal.MapfinalObject;

/**
 * 数据源
 * @author yangyong
 *
 */
public interface DataSource extends MapfinalObject {

	/**
	 * 数据源名称
	 * @return
	 */
	String getName();
	
	/**
	 * 数据源地址
	 * @return
	 */
	String getUrl();
	
	
}
