package com.mapfinal.dispatcher;

import java.util.HashMap;
import java.util.Map;

import com.mapfinal.kit.StringKit;
import org.locationtech.jts.geom.Envelope;

public class SpatialIndexObject {

	private String id;
	/**
	 * 数据类型
	 */
	private String dataType;
	/**
	 * 图形类型
	 */
	private String geometryType;
	private Envelope envelope;
	private Map<String, Object> optionMap;
	
	public SpatialIndexObject(String id, String dataType, String geometryType, Envelope envelop) {
		this.id = StringKit.isBlank(id) ? StringKit.uuid() : id;
		this.dataType = dataType;
		this.geometryType = geometryType;
		this.envelope = envelop;
	}
	
	public Object getOption(String name) {
		return optionMap != null ? this.optionMap.get(name) : null;
	}
	
	public void setOption(String key, Object option) {
		if(optionMap==null) {
			optionMap = new HashMap<String, Object>();
		}
		optionMap.put(key, option);
	}

	@Override
	public boolean equals(Object paramObject) {
		if(paramObject instanceof SpatialIndexObject) {
			SpatialIndexObject sio = ((SpatialIndexObject) paramObject);
			if(id.equals(sio.getId()) && dataType.equals(sio.getDataType())) {
				return true;
			} else {
				return false;
			}
		} else {
			return super.equals(paramObject);
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Object> getOptionMap() {
		return optionMap;
	}

	public void setOptionMap(Map<String, Object> optionMap) {
		this.optionMap = optionMap;
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

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
}
