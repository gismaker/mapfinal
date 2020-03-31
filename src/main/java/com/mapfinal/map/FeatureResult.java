package com.mapfinal.map;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FeatureResult<K> {

	private List<Field> fields;
	private Map<K, Feature<K>> features;
	
	public FeatureResult(List<Field> fields) {
		this.fields = fields;
		features = new ConcurrentHashMap<K, Feature<K>>();
	}
	
	public void addFeature(Feature<K> feature) {
		features.put(feature.getId(), feature);
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
