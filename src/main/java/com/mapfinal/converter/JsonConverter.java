package com.mapfinal.converter;

import java.util.List;
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
import com.mapfinal.map.FeatureList;

public class JsonConverter implements DataConverter<String, Geometry> {

	@Override
	public Geometry convert(String paramS) {
		// TODO Auto-generated method stub
		JSONObject geometry = JSON.parseObject(paramS);
		String type = geometry.getString("type");
		if("FeatureCollection".equalsIgnoreCase(type)) {
			JSONArray features = geometry.getJSONArray("features");
			if(features!=null && features.size() > 0) {
				Geometry[] geoms = new Geometry[features.size()];
				for (int i=0; i<features.size(); i++) {
					Geometry geo = parseGeometry(features.getJSONObject(i).getJSONObject("geometry"));
					geoms[i] = geo;
				}
				return GeoKit.getGeometryFactory().createGeometryCollection(geoms);
			}
			return null;
		} else if("Feature".equalsIgnoreCase(type)) {
			return parseGeometry(geometry.getJSONObject("geometry"));
		} else {
			return parseGeometry(geometry);
		}
	}

	public Geometry parseGeometry(JSONObject geometry) {
		if(geometry==null) {
			return null;
		}
		String geotype = geometry.getString("type");
		if("FeatureCollection".equalsIgnoreCase(geotype)) {
			JSONArray features = geometry.getJSONArray("features");
			if(features!=null && features.size() > 0) {
				Geometry[] geoms = new Geometry[features.size()];
				for (int i=0; i<features.size(); i++) {
					Geometry geo = parseGeometry(features.getJSONObject(i).getJSONObject("geometry"));
					geoms[i] = geo;
				}
				return GeoKit.getGeometryFactory().createGeometryCollection(geoms);
			}
			return null;
		}
		if("Feature".equalsIgnoreCase(geotype)) {
			return parseGeometry(geometry.getJSONObject("geometry"));
		}
		
		if("GeometryCollection".equalsIgnoreCase(geotype)) {
			JSONArray geometries = geometry.getJSONArray("geometries");
			int len = geometries.size();
			Geometry[] geometries_array = new Geometry[len];
			for (int i = 0; i < len; i++) {
				Geometry geometry_obj = parseGeometry(geometries.getJSONObject(i));
				geometries_array[i] = geometry_obj;
			}
			return GeoKit.createGeometryCollect(geometries_array);
		}
		
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
	
	
	public FeatureList<String> parseFeatureCollection(JSONObject featureCollection) {
		JSONArray features = featureCollection.getJSONArray("features");
		if(features!=null && features.size() > 0) {
			FeatureList<String> featureList = new FeatureList<String>(features.size());
			for (int i=0; i<features.size(); i++) {
				Feature<String> feature = parseFeature(features.getJSONObject(i));
				featureList.addFeature(feature);
			}
			if(featureCollection.containsKey("crs")) {
				if("name".equals(featureCollection.getJSONObject("crs").getString("type"))) {
					JSONObject crs = featureCollection.getJSONObject("crs");
					String crsName = crs.getJSONObject("properties").getString("name");
					if(StringKit.isNotBlank(crsName)) {
						if(crsName.startsWith("urn:ogc:def:crs:EPSG::")) {
							String epsg = "EPSG:" + crsName.substring(crsName.indexOf("::")+2);
							featureList.setSpatialReference(new SpatialReference(epsg));
						} else if(crsName.startsWith("EPSG:")) {
							featureList.setSpatialReference(new SpatialReference(crsName));
						} else if(crsName.endsWith("CRS84")) {
							featureList.setSpatialReference(new SpatialReference("EPSG:4326"));
						} 
					}
				}
			}
			return featureList;
		} else {
			Feature<String> feature = parseFeature(featureCollection);
			if(feature!=null) {
				FeatureList<String> featureList = new FeatureList<String>(1);
				featureList.addFeature(feature);
				return featureList;
			}
		}
		return null;
	}
	

	public Feature<String> parseFeature(JSONObject feature) {
		String type = feature.getString("type");
		if("GeometryCollection".equalsIgnoreCase(type)) {
			Geometry geometry = parseGeometry(feature);
			if(geometry==null) {
				return null;
			}
			Feature<String> result = new Feature<String>(geometry, null);
			result.setType(type);
			return result;
		}
		
		Map<String, Object> properties = (Map) feature.get("properties");
		Geometry geometry = parseGeometry(feature.getJSONObject("geometry"));
		if(geometry==null) {
			return null;
		}
		Feature<String> result = new Feature<String>(geometry, properties);
		result.setId(feature.getString("id"));
		return result;
	}

	public FeatureClass<String> parseFeatureClass(JSONArray featureCollection) {
		if (featureCollection == null)
			return null;
		int len = featureCollection.size();
		FeatureClass<String> features = new FeatureClass<String>(len);
		for (int i = 0; i < len; i++) {
			JSONObject fjson = featureCollection.getJSONObject(i);
			Feature<String> feature = parseFeature(fjson);
			feature.setId(StringKit.isBlank(feature.getId()) ? String.valueOf(i) : feature.getId());
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
	
	public JSONObject parseJSONObject(Geometry geometry) {
		if(geometry==null) return null;
		String geotype = geometry.getGeometryType();
		JSONObject geom = new JSONObject();
		if ("Point".equalsIgnoreCase(geotype)) {
			geom.put("type", "Point");
		} else if ("MultiPoint".equalsIgnoreCase(geotype)) {
			geom.put("type", "MultiPoint");
		} else if ("LineString".equalsIgnoreCase(geotype)) {
			geom.put("type", "LineString");
		} else if ("MultiLineString".equalsIgnoreCase(geotype)) {
			geom.put("type", "MultiLineString");
		} else if ("Polygon".equalsIgnoreCase(geotype)) {
			geom.put("type", "Polygon");
		} else if ("MultiPolygon".equalsIgnoreCase(geotype)) {
			geom.put("type", "MultiPolygon");
		}
		JSONArray coords = parseJSONArray(geometry);
		geom.put("coordinates", coords);
		return geom;
	}
	
	public JSONArray parseJSONArray(Coordinate coordinate) {
		JSONArray coords = new JSONArray();
		coords.add(coordinate.getX());
		coords.add(coordinate.getY());
		return coords;
	}
	
	public JSONArray parseJSONArray(Geometry geometry) {
		if(geometry==null) return null;
		String geotype = geometry.getGeometryType();
		System.out.println("geotype: " + geotype);
		if ("Point".equalsIgnoreCase(geotype)) {
			Coordinate coordinate = geometry.getCoordinate();
			return parseJSONArray(coordinate);
		} else if ("MultiPoint".equalsIgnoreCase(geotype)) {
//			Coordinate[] coordinates = geometry.getCoordinates();
//			JSONArray coords = new JSONArray();
//			for(int i=0; i<coordinates.length; i++) {
//				JSONArray cd = parseJSONArray(coordinates[i]);
//				coords.add(cd);
//			}
//			return coords;
			int gn = geometry.getNumGeometries();
			JSONArray coords = new JSONArray();
			for(int n=0; n<gn; n++) {
				Geometry geo = geometry.getGeometryN(n);
				JSONArray ja = parseJSONArray(geo);
				coords.add(ja);
			}
			return coords;
		} else if ("LineString".equalsIgnoreCase(geotype) || "LinearRing".equalsIgnoreCase(geotype)) {
			Coordinate[] coordinates = geometry.getCoordinates();
			JSONArray coords = new JSONArray();
			for(int i=0; i<coordinates.length; i++) {
				JSONArray cd = parseJSONArray(coordinates[i]);
				coords.add(cd);
			}
			return coords;
		} else if ("MultiLineString".equalsIgnoreCase(geotype)) {
			int gn = geometry.getNumGeometries();
			JSONArray coords = new JSONArray();
			for(int n=0; n<gn; n++) {
				Geometry geo = geometry.getGeometryN(n);
				JSONArray ja = parseJSONArray(geo);
				coords.add(ja);
			}
			return coords;
		} else if ("Polygon".equalsIgnoreCase(geotype)) {
			Polygon polygon = (Polygon) geometry;
			JSONArray coords = new JSONArray();
			LineString shell = polygon.getExteriorRing();
			coords.add(parseJSONArray(shell));
			int hs = polygon.getNumInteriorRing();
			for (int i = 0; i < hs; i++) {
				LineString hole = polygon.getInteriorRingN(i);
				coords.add(parseJSONArray(hole));
			}
			return coords;
		} else if ("MultiPolygon".equalsIgnoreCase(geotype)) {
			int gn = geometry.getNumGeometries();
			JSONArray coords = new JSONArray();
			for(int n=0; n<gn; n++) {
				Geometry geo = geometry.getGeometryN(n);
				JSONArray ja = parseJSONArray(geo);
				coords.add(ja);
			}
			return coords;
		}
		return null;
	}
	
	public String parseJson(Geometry geometry) {
		JSONObject obj = parseJSONObject(geometry);
		return obj.toJSONString();
	}

}
