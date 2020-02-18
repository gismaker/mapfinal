package com.mapfinal.resource;

import java.util.List;

import com.mapfinal.dispatcher.QueryParameter;
import com.mapfinal.map.Feature;
import com.mapfinal.map.FeatureResult;
import com.mapfinal.resource.ResourceMapCache;

public class FeatureResourceCache extends ResourceMapCache<Long, Feature> {

	public FeatureResourceCache() {
		// TODO Auto-generated constructor stub
	}

	public long addFeature(Feature feature) {
		// TODO Auto-generated method stub
		put(Long.valueOf(feature.getId()), feature);
		return feature.getId();
	}

	public long[] addFeatures(List<Feature> features) {
		// TODO Auto-generated method stub
		if(features.size() < 1) return null;
		long[] fids = new long[features.size()];
		for (int i=0; i<features.size(); i++) {
			long id = addFeature(features.get(i));
			fids[i] = id;
		}
		return fids;
	}

	public void deleteFeature(long featureId) {
		// TODO Auto-generated method stub
		Feature feature = getFeature(featureId);
		if (feature != null)
			feature.destroy();
		remove(Long.valueOf(featureId));
	}

	public void deleteFeatures(long[] featureIds) {
		// TODO Auto-generated method stub
		for (long id : featureIds) {
			deleteFeature(id);
		}
	}

	public Feature getFeature(long id) {
		// TODO Auto-generated method stub
		return get(Long.valueOf(id));
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

	public void close() {
		// TODO Auto-generated method stub
		for (Long fid : keys()) {
			deleteFeature(fid);
		}
		clear();
	}
}
