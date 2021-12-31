package com.mapfinal.resource.geojson;

import java.io.File;

import org.locationtech.jts.geom.Envelope;

import com.alibaba.fastjson.JSONObject;
import com.mapfinal.converter.SpatialReference;
import com.mapfinal.dispatcher.Dispatcher;
import com.mapfinal.dispatcher.QueryParameter;
import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.event.Event;
import com.mapfinal.kit.JsonKit;
import com.mapfinal.kit.StringKit;
import com.mapfinal.map.Feature;
import com.mapfinal.map.FeatureClass;
import com.mapfinal.resource.FeatureResource;
import com.mapfinal.resource.Resource;

public class GeoJsonResource extends FeatureResource<GeoJsonFeature> {

	private JSONObject geoJsonObject;

	public GeoJsonResource(String name, String url) {
		// TODO Auto-generated constructor stub
		name = StringKit.isBlank(name) ? createName() : name;
		setName(name);
		setUrl(url);
		setType(Resource.Type.geojson.name());
		//读取json数据
		File file = new File(getUrl());
		if(file.exists()) {
	        String jsonString = JsonKit.readJsonFile(file);
	        this.geoJsonObject = JSONObject.parseObject(jsonString);
		}
	}

	public GeoJsonResource(String geojson) {
		// TODO Auto-generated constructor stub
		setName(createName());
		setUrl(null);
		setType(Resource.Type.geojson.name());
		this.geoJsonObject = JSONObject.parseObject(geojson);
	}

	public GeoJsonResource(JSONObject json) {
		// TODO Auto-generated constructor stub
		setName(createName());
		setUrl(null);
		this.geoJsonObject = json;
	}

	private String createName() {
		return "GeoJsonResource_" + StringKit.getUuid32();
	}

	@Override
	public Dispatcher connection(Event event) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Feature<GeoJsonFeature> read(SpatialIndexObject sio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEvent(Event event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void prepare(Event event) {
		// TODO Auto-generated method stub

	}

	@Override
	public FeatureClass<GeoJsonFeature> read(Event event) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writer(Event event, FeatureClass<GeoJsonFeature> data) {
		// TODO Auto-generated method stub

	}

	@Override
	public Envelope getEnvelope() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SpatialReference getSpatialReference() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FeatureClass<GeoJsonFeature> queryFeatures(QueryParameter query) {
		// TODO Auto-generated method stub
		return null;
	}

	public JSONObject getGeoJsonObject() {
		return geoJsonObject;
	}

	public void setGeoJsonObject(JSONObject geoJsonObject) {
		this.geoJsonObject = geoJsonObject;
	}

}
