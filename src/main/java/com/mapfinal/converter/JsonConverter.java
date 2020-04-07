package com.mapfinal.converter;

import java.util.Map;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
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
import com.mapfinal.kit.StringKit;
import com.mapfinal.map.Feature;
import com.mapfinal.map.FeatureClass;

public class JsonConverter implements DataConverter<String, Geometry> {

	@Override
	public Geometry convert(String paramS) {
		// TODO Auto-generated method stub
		JSONObject geometry = JSON.parseObject(paramS);
		return parseGeometry(geometry);
	}

	public Geometry parseGeometry(JSONObject geometry) {
		String geotype = geometry.getString("type");
		// System.out.println("[geotype] " + geotype);
		if (StringKit.isBlank(geotype))
			return null;
		JSONArray coordinates = geometry.getJSONArray("coordinates");
		if (coordinates == null)
			return null;
		if ("Point".equalsIgnoreCase(geotype)) {
			double x = coordinates.getDouble(0);
			double y = coordinates.getDouble(1);
			Coordinate c = new Coordinate(x, y);
			Point geoms = GeoKit.createPoint(c);
			// System.out.println("geometry: " + geoms.toString());
			return geoms;
		} else if ("MultiPoint".equalsIgnoreCase(geotype)) {
			Coordinate[] cArray = parseCoordinate(coordinates);
			MultiPoint geoms = GeoKit.getGeometryFactory().createMultiPointFromCoords(cArray);
			// System.out.println("geometry: " + geoms.toString());
			return geoms;
		} else if ("LineString".equalsIgnoreCase(geotype)) {
			Coordinate[] cArray = parseCoordinate(coordinates);
			LineString geoms = GeoKit.getGeometryFactory().createLineString(cArray);
			// System.out.println("geometry: " + geoms.toString());
			return geoms;
		} else if ("MultiLineString".equalsIgnoreCase(geotype)) {
			LineString[] lineStrings = parseLineString(coordinates);
			MultiLineString geoms = GeoKit.getGeometryFactory().createMultiLineString(lineStrings);
			// System.out.println("geometry: " + geoms.toString());
			return geoms;
		} else if ("Polygon".equalsIgnoreCase(geotype)) {
			Polygon geoms = parsePolygon(coordinates);
			// System.out.println("geometry: " + geoms.toString());
			return geoms;
		} else if ("MultiPolygon".equalsIgnoreCase(geotype)) {
			int len = coordinates.size();
			Polygon[] cArray = new Polygon[len];
			for (int i = 0; i < len; i++) {
				JSONArray cds = coordinates.getJSONArray(i);
				Polygon polygon = parsePolygon(cds);
				cArray[i] = polygon;
			}
			MultiPolygon geoms = GeoKit.getGeometryFactory().createMultiPolygon(cArray);
			// System.out.println("geometry: " + geoms.toString());
			return geoms;
		}
		return null;
	}

	public Feature parseFeature(JSONObject feature) {
		Map<String, Object> properties = (Map) feature.get("properties");
		Geometry geometry = parseGeometry(feature.getJSONObject("geometry"));
		return new Feature(geometry, properties);
	}

	public FeatureClass<Long> parseFeatureClass(JSONArray featureCollection) {
		if (featureCollection == null)
			return null;
		int len = featureCollection.size();
		FeatureClass<Long> features = new FeatureClass<Long>();
		for (int i = 0; i < len; i++) {
			JSONObject fjson = featureCollection.getJSONObject(i);
			Feature feature = parseFeature(fjson);
			feature.setId(Long.valueOf(i));
			features.addFeature(feature);
		}
		return features;
	}

	public Coordinate[] parseCoordinate(JSONArray coordinates) {
		int len = coordinates.size();
		Coordinate[] cArray = new Coordinate[len];
		for (int i = 0; i < len; i++) {
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
		for (int i = 0; i < len; i++) {
			JSONArray cds = coordinates.getJSONArray(i);
			Coordinate[] cArray = parseCoordinate(cds);
			lsArray[i] = GeoKit.getGeometryFactory().createLineString(cArray);
		}
		return lsArray;
	}

	public Polygon parsePolygon(JSONArray coordinates) {
		int len = coordinates.size();
		if (len < 1)
			return null;
		LinearRing shell = null;
		LinearRing[] holes = null;
		if (len > 1) {
			holes = new LinearRing[len - 1];
		}
		for (int i = 0; i < len; i++) {
			JSONArray cds = coordinates.getJSONArray(i);
			Coordinate[] cArray = parseCoordinate(cds);
			if (i == 0) {
				shell = GeoKit.getGeometryFactory().createLinearRing(cArray);
			} else {
				holes[i - 1] = GeoKit.getGeometryFactory().createLinearRing(cArray);
			}
		}
		if (len > 1) {
			return GeoKit.getGeometryFactory().createPolygon(shell, holes);
		} else {
			return GeoKit.getGeometryFactory().createPolygon(shell);
		}
	}

}
