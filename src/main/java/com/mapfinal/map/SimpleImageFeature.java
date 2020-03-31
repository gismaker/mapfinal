package com.mapfinal.map;

import java.util.Map;

import com.mapfinal.converter.SpatialReference;
import com.mapfinal.resource.image.Image;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;

public class SimpleImageFeature implements GeoElement {

	/**
	 * 图片
	 */
	private Image image;
	/**
	 * 图形对象
	 */
	private Geometry geometry;
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
	/**
	 * 最后一次渲染时间
	 */
	private long activeTime;

	public SimpleImageFeature(Image image, Envelope envelope, SpatialReference spatialReference) {
		// TODO Auto-generated constructor stub
		this.image = image;
		this.envelope = envelope;
		this.spatialReference = spatialReference;
	}
	
	public SimpleImageFeature(Image image, Geometry geometry, SpatialReference spatialReference) {
		// TODO Auto-generated constructor stub
		this.image = image;
		this.geometry = geometry;
		this.envelope = geometry.getEnvelopeInternal();
		this.spatialReference = spatialReference;
	}

	public SimpleImageFeature(Image image, Envelope envelope) {
		this.image = image;
		this.envelope = envelope;
		this.spatialReference = SpatialReference.wgs84();
	}
	
	public Object getImage() {
		return image.getData();
	}

	public void setImage(Image image) {
		this.image = image;
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

	public long getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(long activeTime) {
		this.activeTime = activeTime;
	}
}
