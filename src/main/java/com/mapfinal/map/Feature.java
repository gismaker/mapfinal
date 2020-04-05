package com.mapfinal.map;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.mapfinal.dispatcher.SpatialIndexObject;

import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;

/**
 * 要素：管理几何及属性的对象
 * @author yangyong
 *
 * @param <K>
 */
public class Feature<K> implements GeoElement {

	private K id;
	/**
	 * 图形对象
	 */
	private Geometry geometry;
	/**
	 * 索引对象
	 */
	private SpatialIndexObject spatialIndexObject;
	/**
	 * 属性信息
	 */
	private Map<String, Object> attributes;
	/**
	 * 包围盒
	 */
	private Envelope envelope;
	/**
	 * 最后一次渲染时间
	 */
	private long activeTime;
	

	public Feature(K id, SpatialIndexObject spatialIndexObject, Geometry geometry) {
		this.id = id;
		this.spatialIndexObject = spatialIndexObject;
		this.geometry = geometry;
	}

	/**
	 * 销毁
	 */
	public void destroy() {
		envelope = null;
		if(attributes!=null) attributes.clear();
		attributes = null;
		spatialIndexObject = null;
		id = null;
	}

	/**
	 * 获取要素的所有属性信息，以Map键值对的方式返回
	 * @param fieldName
	 * @return
	 */
	public <M> M getAttribute(String fieldName) {
		// TODO Auto-generated method stub
		return attributes != null ? (M) attributes.get(fieldName) : null;
	}

	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return attributes;
	}
	
	public void initAttributes() {
		if(attributes==null) {
			attributes = new ConcurrentHashMap<String, Object>();
		}
	}
	
	public void putAttribute(String name, Object value) {
		initAttributes();
		attributes.put(name, value);
	}

	/**
	 * 外接矩形是否相交或包含
	 * @param outEnvelope
	 * @return
	 */
	public boolean envelopeIntersects(Envelope outEnvelope) {
		if (envelope != null) {
			return envelope.intersects(outEnvelope);
		}
		return false;
	}
	
	/**
	 * 获取要素的唯一id编号
	 * @return
	 */
	public K getId() {
		return id;
	}

	public void setId(K id) {
		this.id = id;
	}

	/**
	 * 空间索引对象
	 * @return
	 */
	public SpatialIndexObject getSpatialIndexObject() {
		return spatialIndexObject;
	}

	public void setSpatialIndexObject(SpatialIndexObject spatialIndexObject) {
		this.spatialIndexObject = spatialIndexObject;
	}

	/**
	 * 外接矩形，包围盒
	 * @return
	 */
	public Envelope getEnvelope() {
		return envelope;
	}

	public void setEnvelope(Envelope envelope) {
		this.envelope = envelope;
	}

	/**
	 * 活跃时间，上一次调用时间
	 * @return
	 */
	public long getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(long activeTime) {
		this.activeTime = activeTime;
	}
	
	/**
	 * 根据输入的字段名称获取属性值
	 * @return
	 */
	@Override
	public Geometry getGeometry() {
		return geometry;
	}

	@Override
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}
}
