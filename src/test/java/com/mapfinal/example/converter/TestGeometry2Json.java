package com.mapfinal.example.converter;

import org.locationtech.jts.geom.Geometry;

import com.mapfinal.converter.JsonConverter;

public class TestGeometry2Json {

	public static void main(String[] args) {
		//String geojson = "{\"type\": \"Feature\",\"properties\": {},\"geometry\": {\"type\": \"LineString\",\"coordinates\": [[105.6005859375, 30.65681556429287],[107.95166015624999, 31.98944183792288],[109.3798828125, 30.031055426540206],[107.7978515625, 29.935895213372444]]}}";
		String geojson = "{\"type\": \"Feature\",\"properties\": {},\"geometry\": {\"type\": \"MultiPolygon\",\"coordinates\": [[[[109.2041015625, 30.088107753367257],[115.02685546875, 30.088107753367257],[115.02685546875, 32.7872745269555],[109.2041015625, 32.7872745269555],[109.2041015625, 30.088107753367257]]],[[[112.9833984375, 26.82407078047018],[116.69677734375, 26.82407078047018],[116.69677734375, 29.036960648558267],[112.9833984375, 29.036960648558267],[112.9833984375, 26.82407078047018]]]]}}";
		JsonConverter jc = new JsonConverter();
		Geometry geom = jc.convert(geojson);
		
		System.out.println("geom child: " + geom.getNumGeometries());
		System.out.println("geom coord: " + geom.getNumPoints());
		for(int i=0; i<geom.getNumGeometries(); i++) {
			Geometry gn = geom.getGeometryN(i);
			System.out.println("gn coord: " + gn.getNumPoints());
		}
		
		String json = jc.parseJson(geom);
		
		System.out.println(json);
	}
}
