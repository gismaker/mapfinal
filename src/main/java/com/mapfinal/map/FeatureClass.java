package com.mapfinal.map;

import java.util.Iterator;
import java.util.List;

import org.locationtech.jts.geom.Envelope;

import com.mapfinal.cache.Cache;
import com.mapfinal.cache.impl.MapCacheImpl;
import com.mapfinal.converter.SpatialReference;
import com.mapfinal.kit.StringKit;
import com.mapfinal.resource.Data;

/**
 * 要素类：指具有相同的几何特征的要素集合，比如点的集合，表现为shapefile或者是Geodatabase中的feature class。
 * 
 * @author yangyong
 *
 * @param <K>
 */
public class FeatureClass<K> implements Data, Cloneable {

	private String name;
	/**
	 * 字段信息
	 */
	private List<Field> fields;
	/**
	 * 要素集合
	 */
	private Cache<K, Feature<K>> features;
	/**
	 * 坐标系统
	 */
	private SpatialReference spatialReference = null;
	/**
	 * 外接矩形
	 */
	private Envelope envelope;
	/**
	 * 几何类型
	 */
	private String geometryType;

	public FeatureClass() {
		// TODO Auto-generated constructor stub
		this.features = new MapCacheImpl<K, Feature<K>>();
	}
	
	public FeatureClass(int size) {
		// TODO Auto-generated constructor stub
		this.features = new MapCacheImpl<K, Feature<K>>(size);
	}

	public FeatureClass(List<Field> fields) {
		this.fields = fields;
		this.features = new MapCacheImpl<K, Feature<K>>();
	}
	
	
	@Override
	public FeatureClass<K> clone() {
//		FeatureClass<K> result = new FeatureClass<>();
//		result.setEnvelope(getEnvelope());
//		result.setGeometryType(getGeometryType());
//		result.setFeatures(getFeatures());
//		result.setFields(getFields());
//		result.setName(getName());
//		result.setSpatialReference(getSpatialReference());
//		return result;
		FeatureClass<K> o = null;
        try {
            o = (FeatureClass<K>) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
	}
	
	/**
	 * 增加要素
	 * @param feature
	 */
	public void addFeature(Feature<K> feature) {
		if(feature.getEnvelope()!=null) {
			if(this.envelope==null) {
				this.envelope = new Envelope(feature.getEnvelope());
			} else {
				this.envelope.intersection(feature.getEnvelope());
			}
		}
		features.put(feature.getId(), feature);
	}

	/**
	 * 增加要素
	 * @param id
	 * @param feature
	 */
	public void putFeature(K id, Feature<K> feature) {
		if(feature.getEnvelope()!=null) {
			if(this.envelope==null) {
				this.envelope = new Envelope(feature.getEnvelope());
			} else {
				this.envelope.intersection(feature.getEnvelope());
			}
		}
		features.put(id, feature);
	}

	/**
	 * 获取要素
	 * @param id
	 * @return
	 */
	public Feature<K> getFeature(K id) {
		return features.get(id);
	}
	
	/**
	 * 获取要素集合
	 * @param ids
	 * @return
	 */
	public FeatureClass<K> getFeatures(K[] ids) {
		// TODO Auto-generated method stub
		if(ids.length < 1) return null;
		FeatureClass<K> featureResult = new FeatureClass<K>(fields);
		featureResult.setGeometryType(geometryType);
		featureResult.setSpatialReference(spatialReference);
		for (K id : ids) {
			Feature<K> feature = getFeature(id);
			featureResult.addFeature(feature);
		}
		return featureResult;
	}
	
	public void updateFeature(Feature<K> feature) {
		// TODO Auto-generated method stub
		putFeature(feature.getId(), feature);
	}

	public void updateFeatures(List<Feature<K>> features) {
		// TODO Auto-generated method stub
		for (Feature<K> feature : features) {
			putFeature(feature.getId(), feature);
		}
	}
	
	public boolean removeFeature(Feature<K> feature) {
		return features.remove(feature.getId());
	}

	public boolean removeFeature(K key) {
		return features.remove(key);
	}
	
	public void clearFeature() {
		features.clear();
	}
	
	public long size() {
		return features.size();
	}

	/**
	 * 获取要素数量
	 * 
	 * @return
	 */
	public long featureCount() {
		return features.size();
	}
	
	public Cache<K, Feature<K>> getFeatures() {
		return features;
	}

	public void setFeatures(Cache<K, Feature<K>> features) {
		this.features = features;
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

	/**
	 * 获取属性字段
	 * 
	 * @return
	 */
	public List<Field> getFields() {
		return fields;
	}

	/**
	 * 获取迭代器
	 * 
	 * @return
	 */
	public Iterator<Field> iterator() {
		return fields.iterator();
	}

	/**
	 * 设定属性字段
	 * 
	 * @param fields
	 */
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	/**
	 * 获取坐标系统
	 * 
	 * @return
	 */
	public SpatialReference getSpatialReference() {
		return spatialReference;
	}

	/**
	 * 设定坐标系统
	 * 
	 * @param spatialReference
	 */
	public void setSpatialReference(SpatialReference spatialReference) {
		this.spatialReference = spatialReference;
	}

	public Envelope getEnvelope() {
		return envelope;
	}

	public void setEnvelope(Envelope envelope) {
		this.envelope = envelope;
	}

	public String getGeometryType() {
		return geometryType;
	}

	public void setGeometryType(String geometryType) {
		this.geometryType = geometryType;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
