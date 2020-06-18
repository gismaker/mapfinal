package com.mapfinal.resource.tile;

import java.util.Map;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;

import com.mapfinal.converter.SpatialReference;
import com.mapfinal.map.GeoImage;
import com.mapfinal.map.Tile;

/**
 * 瓦片要素
 * @author yangyong
 *
 * @param <M>
 */
public class TileFeature<M> implements GeoImage<M> {

	private Tile tile;
	private TileData<M> data;

	public TileFeature(TileData<M> data, Tile tile) {
		this.data = data.reference();
		this.tile = tile;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return tile.getId();
	}

	@Override
	public M getImage() {
		// TODO Auto-generated method stub
		return data!=null ? data.getImageData() : null;
	}

	public SpatialReference getSpatialReference() {
		return tile!=null ? tile.getSpatialReference() : null;
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
		if(data!=null) {
			data.referenceRelease();
		}
	}

	@Override
	public boolean isRectImage() {
		// TODO Auto-generated method stub
		return true;
	}
}
