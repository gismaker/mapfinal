package com.mapfinal.resource.tile;

import java.util.Map;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;

import com.mapfinal.MapfinalObject;
import com.mapfinal.converter.SpatialReference;
import com.mapfinal.map.GeoImage;
import com.mapfinal.map.Tile;

public class TileFeature<M> implements GeoImage<M>, MapfinalObject {

	private TileResource<M> resource;
	private TileCollection collection;
	private Tile tile;
	/**
	 * 最后一次渲染时间
	 */
	private long activeTime;

	public TileFeature(TileCollection collection, TileResource<M> resource, Tile tile) {
		this.setCollection(collection);
		this.resource = resource.reference();
		this.tile = tile;
	}

	@Override
	public M getImage() {
		// TODO Auto-generated method stub
		return resource.getImage();
	}

	public long getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(long activeTime) {
		this.activeTime = activeTime;
	}
	
	////////////////////////////////////////

	public SpatialReference getSpatialReference() {
		return tile.getSpatialReference();
	}

	public Envelope getEnvelope() {
		return tile.getEnvelope();
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

	/**
	 * 左上角
	 * 
	 * @return
	 */
	public Coordinate getTopLeft() {
		return new Coordinate(getEnvelope().getMinX(), getEnvelope().getMaxY());
	}

	/**
	 * 左下角
	 * 
	 * @return
	 */
	public Coordinate getBottomLeft() {
		return new Coordinate(getEnvelope().getMinX(), getEnvelope().getMinY());
	}

	/**
	 * 右上角
	 * 
	 * @return
	 */
	public Coordinate getTopRight() {
		return new Coordinate(getEnvelope().getMaxX(), getEnvelope().getMaxY());
	}

	/**
	 * 右下角
	 * 
	 * @return
	 */
	public Coordinate getBottomRight() {
		return new Coordinate(getEnvelope().getMaxX(), getEnvelope().getMinY());
	}

	public TileCollection getCollection() {
		return collection;
	}

	public void setCollection(TileCollection collection) {
		this.collection = collection;
	}

	public TileResource<M> getResource() {
		return resource;
	}

	public void setResource(TileResource<M> resource) {
		this.resource = resource;
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		int r = resource.referenceRelease();
		if(r< 1) {
			System.out.println("[TileFeature] resource destroy : " + resource.getName());
			collection.remove(resource.getName());
			resource.destroy();
			resource=null;
		}
	}
}
