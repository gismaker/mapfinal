package com.mapfinal.example.converter;

import com.mapfinal.converter.ConverterManager;
import com.mapfinal.converter.scene.SceneEPSG3857CRS;
import com.mapfinal.converter.scene.SceneEPSG4326CRS;
import com.mapfinal.converter.scene.ScenePoint;
import com.mapfinal.geometry.Latlng;
import org.locationtech.jts.geom.Coordinate;

/**
 * 测试场景坐标和地理坐标的转换
 * <br>mapfinal中的坐标包括屏幕坐标、场景坐标、地理坐标（一般为经纬度）、图层坐标（图层本身拥有的坐标系）
 * @author yangyong
 *
 */
public class TestSceneCRS {

	public static void main(String[] args) {
		SceneEPSG4326CRS crs = new SceneEPSG4326CRS();
		
		//测试生成的屏幕坐标
		ScenePoint point = crs.latLngToPoint(new Latlng(90, -180), 0);
		System.out.println("Point: " + point.toString());
		
		ScenePoint point2 = crs.latLngToPoint(new Latlng(-90, 180), 0);
		System.out.println("Point: " + point2.toString());
		
		ScenePoint point3 = crs.latLngToPoint(new Latlng(27, 123), 1);
		System.out.println("Point: " + point3.toString());
		
		ScenePoint point4 = crs.latLngToPoint(new Latlng(27, 123), 2);
		System.out.println("Point: " + point4.toString());
		
		ScenePoint point5 = crs.latLngToPoint(new Latlng(27, 123), 3);
		System.out.println("Point: " + point5.toString());
		
		System.out.println("------------------------------------------");
		//测试距离计算
		double dist = crs.distance(new Latlng(39.907, 116.390), new Latlng(39.903, 116.398));
		System.out.println("distance: " + dist);
		
		System.out.println("------------------------------------------");
		//测试切片tile的坐标转换
		SceneEPSG3857CRS crs3857 = new SceneEPSG3857CRS();
		ScenePoint sp1 = crs3857.latLngToPoint(new Latlng(90, -180), 0);
		System.out.println("sp1: " + sp1.toString());
		
		ScenePoint sp2 = crs3857.latLngToPoint(new Latlng(-90, 180), 0);
		System.out.println("sp2: " + sp2.toString());
		
		Coordinate c3 = ConverterManager.me().mercatorToWgs84(new Coordinate(2.0037508342789244E7, -3503549.8435043744));
		System.out.println("c3: " + c3.toString());
		
		Coordinate c4 = ConverterManager.me().wgs84ToMercator(new Coordinate(180, -30));
		System.out.println("c4: " + c4.toString());
		
		System.out.println("------------------------------------------");
		
		//
		ScenePoint t1 = crs3857.getProjection().project(new Latlng(27, 123));
		System.out.println("t1: " + t1.toString());
		ScenePoint t2 = crs3857.getProjection().project(new Latlng(-30, 180));
		System.out.println("t2: " + t2.toString());
		
		ScenePoint t3 = crs3857.getProjection().project(new Latlng(0, 0));
		System.out.println("t3: " + t3.toString());
	}
}
