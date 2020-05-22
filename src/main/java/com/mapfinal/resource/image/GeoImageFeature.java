package com.mapfinal.resource.image;

import java.util.Map;

import com.mapfinal.converter.SpatialReference;
import com.mapfinal.map.GeoImage;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;

public class GeoImageFeature<M> extends Image<M> implements GeoImage<M> {
	/**
	 * 图形对象
	 */
	private Geometry geometry = null;
	/**
	 * 坐标系统
	 */
	private SpatialReference spatialReference;
	/**
	 * 属性信息
	 */
	private Map<String, Object> attributes;
	/**
	 * 包围盒
	 */
	private Envelope envelope;

	public GeoImageFeature(String name, String url) {
		super(name, url);
	}
	
	public GeoImageFeature(String name, String url, Geometry geometry) {
		super(name, url);
		this.geometry = geometry;
		this.envelope = geometry.getEnvelopeInternal();
		this.spatialReference = SpatialReference.wgs84();
	}
	
	public GeoImageFeature(String name, String url, Envelope envelope) {
		super(name, url);
		this.envelope = envelope;
		this.spatialReference = SpatialReference.wgs84();
	}
	
	public GeoImageFeature(String name, String url, M image, Geometry geometry) {
		super(name, url, image);
		this.geometry = geometry;
		this.envelope = geometry.getEnvelopeInternal();
		this.spatialReference = SpatialReference.wgs84();
	}

	public GeoImageFeature(String name, String url, M image, Envelope envelope) {
		super(name, url, image);
		this.envelope = envelope;
		this.spatialReference = SpatialReference.wgs84();
	}
	
	public M getImage() {
		return getData();
	}

	/**
	 * 左上角
	 * 
	 * @return
	 */
	public Coordinate getTopLeft() {
		return new Coordinate(envelope.getMinX(), envelope.getMaxY());
	}

	/**
	 * 左下角
	 * 
	 * @return
	 */
	public Coordinate getBottomLeft() {
		return new Coordinate(envelope.getMinX(), envelope.getMinY());
	}

	/**
	 * 右上角
	 * 
	 * @return
	 */
	public Coordinate getTopRight() {
		return new Coordinate(envelope.getMaxX(), envelope.getMaxY());
	}

	/**
	 * 右下角
	 * 
	 * @return
	 */
	public Coordinate getBottomRight() {
		return new Coordinate(envelope.getMaxX(), envelope.getMinY());
	}

	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return attributes;
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

	public SpatialReference getSpatialReference() {
		return spatialReference;
	}

	public void setSpatialReference(SpatialReference spatialReference) {
		this.spatialReference = spatialReference;
	}

	public Envelope getEnvelope() {
		return envelope;
	}

	public void setEnvelope(Envelope envelope) {
		this.envelope = envelope;
	}
	
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

}
