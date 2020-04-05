package com.mapfinal.map;

import java.util.List;
import java.util.Map;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;

public class VectorTileFeature implements GeoElement {

	private VectorTile vectorTile;
	private Envelope envelope;
	private List<Feature> features;
	/**
	 * 最后一次渲染时间
	 */
	private long activeTime;

	public VectorTileFeature(VectorTile vectorTile, Envelope envelope) {
		this.envelope = envelope;
		this.vectorTile = vectorTile;
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
		return null;
	}

	@Override
	public Geometry getGeometry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGeometry(Geometry geometry) {
		// TODO Auto-generated method stub
	}

	public String getId() {
		return vectorTile.getId();
	}

	public Envelope getEnvelope() {
		return envelope;
	}

	public void setEnvelope(Envelope envelope) {
		this.envelope = envelope;
	}

	public VectorTile getVectorTile() {
		return vectorTile;
	}

	public void setVectorTile(VectorTile vectorTile) {
		this.vectorTile = vectorTile;
	}

	public long getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(long activeTime) {
		this.activeTime = activeTime;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
