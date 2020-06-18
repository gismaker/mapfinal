package com.mapfinal.map;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONObject;
import com.mapfinal.converter.JsonConverter;
import com.mapfinal.converter.JsonStore;
import com.mapfinal.geometry.GeoKit;
import com.mapfinal.render.style.Symbol;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.IntersectionMatrix;
import org.locationtech.jts.geom.Point;

/**
 * 要素：管理几何及属性的对象
 * 
 * @author yangyong
 *
 * @param <K>
 */
public class Feature<K> implements Graphic, JsonStore {

	protected K id;
	/**
	 * 图形对象
	 */
	protected Geometry geometry;
	/**
	 * 属性信息
	 */
	protected Map<String, Object> attributes;
	/**
	 * 包围盒
	 */
	protected Envelope envelope;
	/**
	 * 索引对象
	 */
	//protected SpatialIndexObject spatialIndexObject;
	/**
	 * 符号化
	 */
	//protected Symbol symbol;

	public Feature() {
	}

	public Feature(K id, Geometry geometry) {
		this.id = id;
		this.geometry = geometry;
		this.envelope = geometry != null ? geometry.getEnvelopeInternal() : envelope;
	}

	public Feature(Geometry geometry, Map<String, Object> attributes) {
		this.geometry = geometry;
		this.attributes = attributes;
		this.envelope = geometry != null ? geometry.getEnvelopeInternal() : envelope;
	}

	public boolean isEmpty() {
		return geometry == null ? true : geometry.isEmpty();
	}

	public double getArea() {
		return geometry == null ? 0.0 : geometry.getArea();
	}

	public double getLength() {
		return geometry == null ? 0.0 : geometry.getLength();
	}

	public Point getCentroid() {
		return geometry == null ? null : geometry.getCentroid();
	}

	public Point getInteriorPoint() {
		return geometry == null ? null : geometry.getInteriorPoint();
	}

	public boolean contains(Coordinate coordinate) {
		if (geometry == null)
			return false;
		return geometry.contains(GeoKit.createPoint(coordinate));
	}

	public boolean intersects(Geometry g) {
		return geometry == null ? false : geometry.intersects(g);
	}

	public boolean touches(Geometry g) {
		return geometry == null ? false : geometry.touches(g);
	}

	public boolean disjoint(Geometry g) {
		return geometry == null ? false : geometry.disjoint(g);
	}

	public double distance(Geometry g) {
		return geometry == null ? 0.0 : geometry.distance(g);
	}

	public boolean within(Geometry geom, double distance) {
		return geometry == null ? false : geometry.isWithinDistance(geom, distance);
	}

	public boolean crosses(Geometry g) {
		return geometry == null ? false : geometry.crosses(g);
	}

	public boolean overlaps(Geometry g) {
		return geometry == null ? false : geometry.overlaps(g);
	}

	public boolean covers(Geometry g) {
		return geometry == null ? false : geometry.covers(g);
	}

	public boolean coveredBy(Geometry g) {
		return geometry == null ? false : geometry.coveredBy(g);
	}

	public boolean relate(Geometry g, String intersectionPattern) {
		return geometry == null ? false : geometry.relate(g, intersectionPattern);
	}

	public IntersectionMatrix relate(Geometry g) {
		return geometry == null ? null : geometry.relate(g);
	}

	/**
	 * 销毁
	 */
	public void destroy() {
		envelope = null;
		if (attributes != null)
			attributes.clear();
		attributes = null;
		id = null;
		//spatialIndexObject = null;
		geometry = null;
	}

	/**
	 * 获取要素的所有属性信息，以Map键值对的方式返回
	 * 
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
		if (attributes == null) {
			attributes = new ConcurrentHashMap<String, Object>();
		}
	}

	public void putAttr(String name, Object value) {
		initAttributes();
		attributes.put(name, value);
	}

	public <M> M getAttr(String name) {
		return attributes != null ? (M) attributes.get(name) : null;
	}

	public <M> M getAttr(String name, M defaultValue) {
		if (attributes == null || attributes.get(name) == null) {
			return defaultValue;
		}
		return (M) attributes.get(name);
	}
	
	public Object removeAttr(String name) {
		if (attributes != null) {
			return attributes.remove(name);
		}
		return null;	
	}

	/**
	 * 外接矩形是否相交或包含
	 * 
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
	 * 
	 * @return
	 */
	public K getId() {
		return id;
	}

	public void setId(K id) {
		this.id = id;
	}

	/**
	 * 外接矩形，包围盒
	 * 
	 * @return
	 */
	public Envelope getEnvelope() {
		if (envelope == null && geometry != null) {
			envelope = geometry.getEnvelopeInternal();
		}
		return envelope;
	}

	public void setEnvelope(Envelope envelope) {
		this.envelope = envelope;
	}

	/**
	 * 根据输入的字段名称获取属性值
	 * 
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
	
	/**
	 * 空间索引对象
	 * 
	 * @return
	 */
//	public SpatialIndexObject getSpatialIndexObject() {
//		return spatialIndexObject;
//	}
//
//	public void setSpatialIndexObject(SpatialIndexObject spatialIndexObject) {
//		this.spatialIndexObject = spatialIndexObject;
//	}

	@Override
	public void fromJson(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		JsonConverter jsonConverter = new JsonConverter();
		Map<String, Object> properties = (Map) jsonObject.get("properties");
		Geometry geometry = jsonConverter.parseGeometry(jsonObject.getJSONObject("geometry"));
		id = (K) jsonObject.get("id");
	}

	@Override
	public JSONObject toJson() {
		// TODO Auto-generated method stub
		return null;
	}

//	public Symbol getSymbol() {
//		return symbol;
//	}
//
//	public void setSymbol(Symbol symbol) {
//		this.symbol = symbol;
//	}
}
