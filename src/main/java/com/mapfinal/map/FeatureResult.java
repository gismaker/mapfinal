package com.mapfinal.map;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FeatureResult {

	private List<Field> fields;
	private Map<Long, Feature> features;
	
	public FeatureResult(List<Field> fields) {
		this.fields = fields;
		features = new ConcurrentHashMap<Long, Feature>();
	}
	
	public void addFeature(Feature feature) {
		features.put(Long.valueOf(feature.getId()), feature);
	}

	/**
	 * 获取要素数量
	 * @return
	 */
	public long featureCount() {
		return features.size();
	}

	/**
	 * 获取属性字段
	 * @return
	 */
	public List<Field> getFields() {
		return fields;
	}

	/**
	 * 获取迭代器
	 * @return
	 */
	public Iterator<Field> iterator() {
		return fields.iterator();
	}

	/**
	 * 设定属性字段
	 * @param fields
	 */
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
	

}
