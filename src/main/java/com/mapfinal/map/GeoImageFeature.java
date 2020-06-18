package com.mapfinal.map;

import java.util.Map;

import com.mapfinal.converter.SpatialReference;
import com.mapfinal.resource.image.Image;
import com.mapfinal.resource.image.LocalImage;
import com.mapfinal.resource.image.RemoteImage;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;

public class GeoImageFeature<M> implements GeoImage<M> {
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
	/**
	 * 是否矩形图片
	 */
	private boolean isRectImage = true;
	/**
	 * 图片
	 */
	private Image<M> picture;

	public GeoImageFeature(String url, Geometry geometry) {
		if(url.startsWith("http")) {
			this.picture = new RemoteImage<M>(url, url);
		} else {
			this.picture = new LocalImage<M>(url, url);
		}
		this.geometry = geometry;
		this.envelope = geometry.getEnvelopeInternal();
		this.spatialReference = SpatialReference.wgs84();
		if(this.geometry!=null) isRectImage = false;
	}
	
	public GeoImageFeature(String url, Envelope envelope) {
		if(url.startsWith("http")) {
			this.picture = new RemoteImage<M>(url, url);
		} else {
			this.picture = new LocalImage<M>(url, url);
		}
		this.envelope = envelope;
		this.spatialReference = SpatialReference.wgs84();
	}
	
	public GeoImageFeature(String name, String url, M image, Geometry geometry) {
		this.picture = new Image<M>(name, url, image);
		this.geometry = geometry;
		this.envelope = geometry.getEnvelopeInternal();
		this.spatialReference = SpatialReference.wgs84();
		if(this.geometry!=null) isRectImage = false;
	}

	public GeoImageFeature(String name, String url, M image, Envelope envelope) {
		this.picture = new Image<M>(name, url, image);
		this.envelope = envelope;
		this.spatialReference = SpatialReference.wgs84();
	}
	
	public M getImage() {
		return picture!=null ? picture.getData() : null;
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
		if(this.geometry!=null) isRectImage = false;
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

	@Override
	public boolean isRectImage() {
		return isRectImage;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		picture.destroy();
	}
}
