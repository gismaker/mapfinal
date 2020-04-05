package com.mapfinal.resource;

import org.locationtech.jts.geom.Envelope;

import com.mapfinal.converter.SpatialReference;
import com.mapfinal.dispatcher.QueryParameter;
import com.mapfinal.map.Feature;
import com.mapfinal.map.FeatureClass;

/**
 * 空间数据资源对象： 资源唯一性，同一个资源可被多个layer调用，name是主键
 * <br>资源对象各自管理自己的资源、缓存和存储。
 * @author yangyong
 *
 */
public abstract class VectorResource<K> extends ResourceObject<FeatureClass<K>> implements ResourceDispatcher<Feature<K>> {
	
	public abstract Envelope getEnvelope();

	public abstract SpatialReference getSpatialReference();
	
	public abstract FeatureClass<K> queryFeatures(QueryParameter query);
}
