package com.mapfinal.resource;

import java.util.List;

import com.mapfinal.dispatcher.QueryParameter;
import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.kit.StringKit;
import com.mapfinal.map.Feature;
import com.mapfinal.map.FeatureResult;
import com.mapfinal.map.Field;
import com.mapfinal.resource.Resource.Type;

public class FeatureResourceObject extends ResourceObject {

	private List<Field> fields;
	private String geometryType;
	
	public FeatureResourceObject(String name, String url, Type type) {
		super(name, url, type);
		this.setCache(new FeatureResourceCache());
	}
	
	public FeatureResourceCache getFeatureResourceCache() {
		return (FeatureResourceCache) getCache();
	}
	
	public Feature curreatFeature(SpatialIndexObject sio) {
		Long id = Long.valueOf(sio.getId());
		if(id==null) return null;
		Feature feature =  getFeatureResourceCache().get(id);
		if(feature==null && getReader()!=null) {
			feature = (Feature) getReader().read(sio);
			if(feature!=null) {
				getFeatureResourceCache().addFeature(feature);
			}
		}
		return feature;
	}
	
	public List<Long> keys() {
		return getFeatureResourceCache().keys();	
	}

	public long addFeature(Feature feature) {
		// TODO Auto-generated method stub
		return getFeatureResourceCache().addFeature(feature);
	}

	public long[] addFeatures(List<Feature> features) {
		// TODO Auto-generated method stub
		return getFeatureResourceCache().addFeatures(features);
	}

	public void deleteFeature(long featureId) {
		// TODO Auto-generated method stub
		getFeatureResourceCache().deleteFeature(featureId);
	}

	public void deleteFeatures(long[] featureIds) {
		// TODO Auto-generated method stub
		getFeatureResourceCache().deleteFeatures(featureIds);
	}

	public Feature getFeature(long id) {
		// TODO Auto-generated method stub
		return getFeatureResourceCache().getFeature(id);
	}

	public FeatureResult getFeatures(long[] ids) {
		// TODO Auto-generated method stub
		if(ids.length < 1) return null;
		FeatureResult featureResult = new FeatureResult(fields);
		for (long id : ids) {
			Feature feature = getFeature(id);
			featureResult.addFeature(feature);
		}
		return featureResult;
	}

	public Field getField(String fieldName) {
		// TODO Auto-generated method stub
		if(fields==null || StringKit.isBlank(fieldName)) {
			return null;
		}
		for (Field field : fields) {
			if(fieldName.equals(field.getName())) {
				return field;
			}
		}
		return null;
	}

	public List<Field> getFields() {
		// TODO Auto-generated method stub
		return fields;
	}
	
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public String getGeometryType() {
		// TODO Auto-generated method stub
		return geometryType;
	}

	public FeatureResult queryFeatures(QueryParameter query) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateFeature(long featureId, Feature feature) {
		// TODO Auto-generated method stub
		addFeature(feature);
	}

	public void updateFeatures(long[] featureIds, List<Feature> features) {
		// TODO Auto-generated method stub
		for (Feature feature : features) {
			addFeature(feature);
		}
	}

	public void clear() {
		// TODO Auto-generated method stub
		getFeatureResourceCache().clear();
	}
	
}
