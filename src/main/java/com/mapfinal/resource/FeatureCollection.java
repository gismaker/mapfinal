package com.mapfinal.resource;

import java.util.List;

import org.locationtech.jts.geom.Envelope;

import com.mapfinal.cache.Cache;
import com.mapfinal.cache.impl.MapCacheImpl;
import com.mapfinal.converter.SpatialReference;
import com.mapfinal.dispatcher.QueryParameter;
import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.kit.StringKit;
import com.mapfinal.map.Feature;
import com.mapfinal.map.FeatureResult;
import com.mapfinal.map.Field;

/**
 * 空间数据资源对象： 资源唯一性，同一个资源可被多个layer调用，name是主键
 * <br>资源对象各自管理自己的资源、缓存和存储。
 * @author yangyong
 *
 */
public abstract class FeatureCollection<V extends FeatureResource<K>, K> implements ResourceCollection<V, K>, ResourceDispatcher<V> {

	//名称，唯一键
	protected String name;
	//文件路径 或 网络地址
	protected String url;
	//ResourceObject外接矩形
	protected Envelope envelope;
	//空间参考坐标系
	protected SpatialReference spatialReference;
	//ResourceObject被调用次数
	protected int reference = 0;
	
	protected Cache<K, V> features;
	protected List<Field> fields;
	protected String geometryType;
	
	//缓存文件夹
	//private String cacheFolder = "common";
	
	@Override
	public long getMemorySize() {
		// TODO Auto-generated method stub
//		long memorySize = 0;
//		for (Long fid : features.keys()) {
//			FeatureResource f = get(fid);
//			memorySize = inst.getObjectSize(obj);
//		}
		return 0L;
	}

	@Override
	public V get(K key) {
		// TODO Auto-generated method stub
		return features.get(key);
	}

	@Override
	public void put(K key, V value) {
		// TODO Auto-generated method stub
		features.put(key, value);
		value.setCollection(this);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return features.size();
	}

	@Override
	public boolean remove(K key) {
		// TODO Auto-generated method stub
		//get(key).setCollection(null);
		return features.remove(key);
	}
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		features.clear();
	}

	@Override
	public List<K> keys() {
		// TODO Auto-generated method stub
		return features.keys();
	}

	public void close() {
		// TODO Auto-generated method stub
		for (K fid : keys()) {
			remove(fid);
		}
		clear();
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
	
//	public abstract V current(SpatialIndexObject sio);
	
//	public void addFeature(V feature) {
//		put(feature.getId(), feature);
//	}

	public void deleteFeature(K featureId) {
		// TODO Auto-generated method stub
		remove(featureId);
	}

	public void deleteFeatures(K[] featureIds) {
		// TODO Auto-generated method stub
		for (K fid : featureIds) {
			deleteFeature(fid);
		}
	}

	public Feature getFeature(K id) {
		// TODO Auto-generated method stub
		return get(id);
	}
	
	public FeatureResult getFeatures(K[] ids) {
		// TODO Auto-generated method stub
		if(ids.length < 1) return null;
		FeatureResult featureResult = new FeatureResult(fields);
		for (K id : ids) {
			Feature feature = get(id);
			featureResult.addFeature(feature);
		}
		return featureResult;
	}
	
	public FeatureResult queryFeatures(QueryParameter query) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateFeature(V feature) {
		// TODO Auto-generated method stub
		put(feature.getId(), feature);
	}

	public void updateFeatures(List<V> features) {
		// TODO Auto-generated method stub
		for (V feature : features) {
			put(feature.getId(), feature);
		}
	}

	////////////////////////////////////////////////

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Envelope getEnvelope() {
		return envelope;
	}

	public void setEnvelope(Envelope envelope) {
		this.envelope = envelope;
	}

	public SpatialReference getSpatialReference() {
		return spatialReference;
	}

	public void setSpatialReference(SpatialReference spatialReference) {
		this.spatialReference = spatialReference;
	}

	public int getReference() {
		return reference;
	}

	public void setReference(int reference) {
		this.reference = reference;
	}

	public Cache<K, V> getFeatures() {
		if(features==null) {
			features = new MapCacheImpl<>();
		}
		return features;
	}

	public void setFeatures(Cache<K, V> features) {
		this.features = features;
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
}
