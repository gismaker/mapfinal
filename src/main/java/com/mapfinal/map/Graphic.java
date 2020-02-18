package com.mapfinal.map;

import java.util.Map;

import com.mapfinal.render.style.Symbol;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;

/**
 * Graphic类用于表示一个地物，可以包含地物的几何信息、属性信息、绘制样式。
 * @author yangyong
 */
public class Graphic implements GeoElement {

	private Long id;
	private Geometry geometry;
	private Symbol symbol;
	private Map<String, Object> attributes;
	private int zIndex;
	
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

}
