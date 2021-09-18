package com.mapfinal.converter;

/**
 * 坐标详解与PROJ.4使用说明
 * https://blog.csdn.net/sf2gis2/article/details/50686811
 * @author yangyong
 *
 */
public class CRS {

	public static final String PROJ4 = "proj4";
	public static final String OGRCS = "ogrcs";
	public static final String WKT2PROJ4 = "wkt2proj4";
	
	private String name;
	private String type = PROJ4;
	private String[] param;
	
	public CRS(String name, String[] param) {
		this.name = name;
		this.param = param;
	}
	
	public CRS(String name, String[] param, String type) {
		this.name = name;
		this.param = param;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String[] getParam() {
		return param;
	}
	public void setParam(String[] param) {
		this.param = param;
	}
}
