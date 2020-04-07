package com.mapfinal.example.converter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mapfinal.geometry.GeoKit;

public class TestJson {

	public static void main(String[] args) {
		TestJson tJson = new TestJson();
		GeoKit.initGeometryFactory();
		
		String gfile = System.getProperty("user.dir") + File.separator + "cache" + File.separator + "test.json";
		String geojson = tJson.txt2String(new File(gfile));
		JSONObject geoj = JSON.parseObject(geojson);
		String type = geoj.getString("type");
		if("FeatureCollection".equalsIgnoreCase(type)) {
			
		} else if("Feature".equalsIgnoreCase(type)) {
			//JSONObject properties = geoj.getJSONObject("properties");
			Map<String, Object> properties = (Map) geoj.get("properties");
			for (Entry<String, Object> prop : properties.entrySet()) {
				System.out.println("[properties] " + prop.getKey() + ": " + prop.getValue() + ", " + prop.getValue().getClass());
			}
			JSONObject geometry = geoj.getJSONObject("geometry");
			String geotype = geometry.getString("type");
			System.out.println("[geotype] " + geotype);
			JSONArray coordinates = geometry.getJSONArray("coordinates");
			if("Point".equalsIgnoreCase(geotype)){
				double x = coordinates.getDouble(0);
				double y = coordinates.getDouble(1);
				Coordinate c = new Coordinate(x, y);
				Point point = GeoKit.createPoint(c);
				System.out.println("geometry: " + point.toString());
			} else if("MultiPoint".equalsIgnoreCase(geotype)){
				Coordinate[] cArray = tJson.parseCoordinate(coordinates);
				MultiPoint geoms = GeoKit.getGeometryFactory().createMultiPointFromCoords(cArray);
				System.out.println("geometry: " + geoms.toString());
			} else if("LineString".equalsIgnoreCase(geotype)){
				Coordinate[] cArray = tJson.parseCoordinate(coordinates);
				LineString geoms = GeoKit.getGeometryFactory().createLineString(cArray);
				System.out.println("geometry: " + geoms.toString());
			} else if("MultiLineString".equalsIgnoreCase(geotype)){
				LineString[] lineStrings = tJson.parseLineString(coordinates);
				MultiLineString geoms = GeoKit.getGeometryFactory().createMultiLineString(lineStrings);
				System.out.println("geometry: " + geoms.toString());
			} else if("Polygon".equalsIgnoreCase(geotype)){
				Polygon geoms = tJson.parsePolygon(coordinates);
				System.out.println("geometry: " + geoms.toString());
			} else if("MultiPolygon".equalsIgnoreCase(geotype)){
				int len = coordinates.size();
				Polygon[] cArray = new Polygon[len];
				for(int i=0; i<len; i++) {
					JSONArray cds = coordinates.getJSONArray(i);
					Polygon polygon = tJson.parsePolygon(cds);
					cArray[i] = polygon;
				}
				MultiPolygon geoms = GeoKit.getGeometryFactory().createMultiPolygon(cArray);
				System.out.println("geometry: " + geoms.toString());
			}
		}
		System.out.println("------------------------------------");
		Point point = GeoKit.createPoint(new Coordinate(39.98, 116.72));
		JSONObject gj = new JSONObject();
		JSONArray gc = new JSONArray();
		gc.add(point.getX());
		gc.add(point.getY());
		gj.put("type", "Point");
		gj.put("coordinates", gc);
		System.out.println(gj.toJSONString());
	}
	
	public Coordinate[] parseCoordinate(JSONArray coordinates) {
		int len = coordinates.size();
		Coordinate[] cArray = new Coordinate[len];
		for(int i=0; i<len; i++) {
			JSONArray cds = coordinates.getJSONArray(i);
			double x = cds.getDouble(0);
			double y = cds.getDouble(1);
			Coordinate c = new Coordinate(x, y);
			cArray[i] = c;
		}
		return cArray;
	}
	
	public LineString[] parseLineString(JSONArray coordinates) {
		int len = coordinates.size();
		LineString[] lsArray = new LineString[len];
		for(int i=0; i<len; i++) {
			JSONArray cds = coordinates.getJSONArray(i);
			Coordinate[] cArray = parseCoordinate(cds);
			lsArray[i] = GeoKit.getGeometryFactory().createLineString(cArray);
		}
		return lsArray;
	}
	
	public Polygon parsePolygon(JSONArray coordinates) {
		int len = coordinates.size();
		if(len < 1) return null;
		LinearRing shell = null;
		LinearRing[] holes = null;
		if(len > 1) {
			holes = new LinearRing[len-1];
		}
		for(int i=0; i<len; i++) {
			JSONArray cds = coordinates.getJSONArray(i);
			Coordinate[] cArray = parseCoordinate(cds);
			if(i==0) {
				shell = GeoKit.getGeometryFactory().createLinearRing(cArray);
			} else {
				holes[i-1] = GeoKit.getGeometryFactory().createLinearRing(cArray);
			}
		}
		if(len > 1) {
			return GeoKit.getGeometryFactory().createPolygon(shell, holes);
		} else {
			return GeoKit.getGeometryFactory().createPolygon(shell);
		}
	}

	public String txt2String(File file) {
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
			String s = null;
			while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
				result.append(System.lineSeparator() + s);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
}
