package com.mapfinal.example.geom;

import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;

import com.mapfinal.geometry.GeoKit;
import com.mapfinal.geometry.Latlng;

public class TestGeom {

	public static void main(String[] args) {
		GeoKit.initGeometryFactory();
		
		Latlng pt = new Latlng(30.56, 116);
		Point point = GeoKit.createPoint(pt);
		
		Latlng[] pts = new Latlng[6];
		pts[0] = new Latlng(30.16, 115.120);
		pts[1] = new Latlng(30.26, 115.348);
		pts[2] = new Latlng(30.36, 115.505);
		pts[3] = new Latlng(30.46, 115.732);
		pts[4] = new Latlng(30.56, 116);
		pts[5] = new Latlng(30.66, 116.652);
		
		LineString line = GeoKit.createLine(pts);
		
		boolean flag = line.intersects(point);
		System.out.println(flag);
	}
}
