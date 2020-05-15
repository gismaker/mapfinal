package com.mapfinal.geometry;

public enum GeomType {
	POINT("Point"), MULTIPOINT("MultiPoint"), LINESTRING("LineString"), LINERING("LineRing"),
	LINESEGMENT("LineSegment"), MULTILINESTRING("MultiLineString"), POLYGON("Polygon"), MULTIPOLYGON("MultiPolygon"),
	GEOMLIST("GeometryCollection");

	private String name;

	private GeomType(String name) {
		this.name = name;
	}

	// get set 方法
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
