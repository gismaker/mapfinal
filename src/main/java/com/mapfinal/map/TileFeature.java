package com.mapfinal.map;

import java.util.Map;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;

import com.mapfinal.converter.SpatialReference;
import com.mapfinal.resource.tile.TileCollection;
import com.mapfinal.resource.tile.TileManager;
import com.mapfinal.resource.tile.TileResource;

/**
 * 瓦片要素
 * @author yangyong
 *
 * @param <M>
 */
public class TileFeature<M> implements GeoImage<M> {

	private Tile tile;
	private String collectionKey;
	private String resourceKey;
	private long activeTime;

	public TileFeature(String collectionKey, String resourceKey, Tile tile) {
		this.collectionKey = collectionKey;
		this.resourceKey = resourceKey;
		this.tile = tile;
	}

	@Override
	public M getImage() {
		// TODO Auto-generated method stub
		TileResource<M> resource = TileManager.me().getResource(collectionKey, resourceKey);
		return resource!=null ? resource.getImage() : null;
	}

	public long getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(long activeTime) {
		this.activeTime = activeTime;
	}
	
	////////////////////////////////////////

	public SpatialReference getSpatialReference() {
		TileCollection collection = TileManager.me().getCollection(collectionKey);
		return collection!=null ? collection.getSpatialReference() : null;
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

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		TileResource<M> resource = TileManager.me().getResource(collectionKey, resourceKey);
		if(resource!=null) {
			resource.referenceRelease();
		}
	}
}
