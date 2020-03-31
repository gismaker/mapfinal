package com.mapfinal.resource;

import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.map.Feature;

import org.locationtech.jts.geom.Geometry;

/**
 * 空间数据资源对象
 * @author yangyong
 */
public abstract class FeatureResource<K> extends Feature<K> implements Resource {
	
	//ResourceObject被调用次数
	private int reference = 0;
	//所属资源组
	private FeatureCollection collection;
		
	public FeatureResource(K id, SpatialIndexObject spatialIndexObject, Geometry geometry) {
		super(id, spatialIndexObject, geometry);
		// TODO Auto-generated constructor stub
	}

	public FeatureCollection getCollection() {
		return collection;
	}

	public void setCollection(FeatureCollection collection) {
		this.collection = collection;
	}

	public int getReference() {
		return reference;
	}

	public void setReference(int reference) {
		this.reference = reference;
	}
}
