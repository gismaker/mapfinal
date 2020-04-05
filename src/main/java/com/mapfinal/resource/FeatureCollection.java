package com.mapfinal.resource;

import java.util.List;

import com.mapfinal.dispatcher.QueryParameter;
import com.mapfinal.map.FeatureClass;

/**
 * 空间数据资源对象： 资源唯一性，同一个资源可被多个layer调用，name是主键
 * <br>资源对象各自管理自己的资源、缓存和存储。
 * @author yangyong
 *
 */
public abstract class FeatureCollection<K, V extends FeatureResource<K>> extends FeatureClass<K, V> implements ResourceCollection<K, V>, ResourceDispatcher<V> {

	//名称，唯一键
	protected String name;
	//文件路径 或 网络地址
	protected String url;
	//ResourceObject被调用次数
	protected int reference = 0;
	
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
		return getFeature(key);
	}

	@Override
	public void put(K key, V value) {
		// TODO Auto-generated method stub
		putFeature(key, value);
		value.setCollection(this);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return getFeatures().size();
	}

	@Override
	public boolean remove(K key) {
		// TODO Auto-generated method stub
		//get(key).setCollection(null);
		return removeFeature(key);
	}
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		clearFeature();
	}

	@Override
	public List<K> keys() {
		// TODO Auto-generated method stub
		return getFeatures().keys();
	}

	public void close() {
		// TODO Auto-generated method stub
		for (K fid : keys()) {
			remove(fid);
		}
		clear();
	}

	public FeatureClass<K, V> queryFeatures(QueryParameter query) {
		// TODO Auto-generated method stub
		return null;
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
	
	public int getReference() {
		return reference;
	}

	public void setReference(int reference) {
		this.reference = reference;
	}
}
