package com.mapfinal.resource.shapefile;

public abstract class ShpType {
	/* 2,4,6...直到33未指定类型为保留类型 */
	public static final int NULL_SHAPE = 0;
	public static final int POINT = 1;
	public static final int POLYLINE = 3;
	public static final int POLYGON = 5;
	public static final int MULTIPOINT = 8;
	public static final int POINT_Z = 11;
	public static final int POLYLINE_Z = 13;
	public static final int POLYGONZ = 15;
	public static final int MULTIPOINT_Z = 18;
	public static final int POINT_M = 21;
	public static final int POLYLINE_M = 23;
	public static final int POLYGON_M = 25;
	public static final int MULTIPOINT_M = 28;
	public static final int MULTIPATCH = 31;
	
	public static String shpTypeName(int shpType) {
		String res = "NULL_SHAPE";
		switch (shpType) {
		case ShpType.NULL_SHAPE:
			break;
		case ShpType.POINT:
			res = "POINT";
			break;
		case ShpType.POLYLINE: 
			res = "POLYLINE";
			break;
		case ShpType.POLYGON:
			res = "POLYGON";
			break;
		case ShpType.MULTIPOINT:
			res = "MULTIPOINT";
			break;
		case ShpType.POINT_Z:
			res = "POINT_Z";
			break;
		case ShpType.POLYLINE_Z:
			res = "POLYLINE_Z";
			break;
		case ShpType.POLYGONZ:
			res = "POLYGONZ";
			break;
		case ShpType.MULTIPOINT_Z:
			res = "MULTIPOINT_Z";
			break;
		case ShpType.POINT_M:
			res = "POINT_M";
			break;
		case ShpType.POLYLINE_M:
			res = "POLYLINE_M";
			break;
		case ShpType.POLYGON_M:
			res = "POLYGON_M";
			break;
		case ShpType.MULTIPOINT_M:
			res = "MULTIPOINT_M";
			break;
		case ShpType.MULTIPATCH:
			res = "MULTIPATCH";
			break;
		}
		return res;
	}
}
