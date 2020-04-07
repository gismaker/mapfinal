package com.mapfinal.map;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mapfinal.converter.JsonConverter;
import com.mapfinal.converter.JsonStore;
import com.mapfinal.render.style.Symbol;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;

/**
 * Graphic类用于表示一个地物，可以包含地物的几何信息、属性信息、绘制样式。
 * @author yangyong
 */
public class Graphic implements GeoElement, JsonStore {

	private Long id;
	private Geometry geometry;
	private Symbol symbol;
	private Map<String, Object> attributes;
	private int zIndex;
	private boolean visible = true;
	
	public Graphic(Geometry geometry, Symbol symbol){
		this.geometry = geometry;
		this.symbol = symbol;
	}
	
	public Graphic(Geometry geometry, Symbol symbol, Map<String, Object> attributes) {
		this.geometry = geometry;
		this.symbol = symbol;
		this.attributes = attributes;
	}
	
	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return attributes;
	}

	public <M> M getAttribute(String fieldName) {
		// TODO Auto-generated method stub
		return attributes!=null ? (M)attributes.get(fieldName) : null;
	}

	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public Geometry getGeometry() {
		// TODO Auto-generated method stub
		return geometry;
	}
	
	@Override
	public void setGeometry(Geometry geometry) {
		// TODO Auto-generated method stub
		this.geometry = geometry;
	}

	public Envelope getEnvelope() {
		// TODO Auto-generated method stub
		return geometry!=null ? geometry.getEnvelopeInternal() : null;
	}

	public boolean envelopeIntersects(Envelope outEnvelope) {
		// TODO Auto-generated method stub
		return outEnvelope.intersects(getEnvelope());
	}

	public Symbol getSymbol() {
		// TODO Auto-generated method stub
		return symbol;
	}

	public void destroy() {
		// TODO Auto-generated method stub
		if(attributes!=null) attributes.clear();
		attributes = null;
		geometry = null;
		symbol = null;
	}

	public int getzIndex() {
		return zIndex;
	}

	public void setzIndex(int zIndex) {
		this.zIndex = zIndex;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void show() {
		this.visible = true;
	}
	public void hide() {
		this.visible = false;
	}

	@Override
	public void fromJson(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		JsonConverter jsonConverter = new JsonConverter();
		Map<String, Object> properties = (Map) jsonObject.get("properties");
		Geometry geometry = jsonConverter.parseGeometry(jsonObject.getJSONObject("geometry"));
		id = jsonObject.getLong("id");
		symbol.fromJson(jsonObject.getJSONObject("symbol"));
		zIndex = jsonObject.getIntValue("zIndex");
		visible = jsonObject.getBooleanValue("visible");
	}

	@Override
	public JSONObject toJson() {
		// TODO Auto-generated method stub
		return (JSONObject) JSON.toJSON(this);
	}
}
