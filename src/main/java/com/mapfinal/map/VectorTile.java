package com.mapfinal.map;

import org.locationtech.jts.geom.Envelope;
import com.mapfinal.converter.SpatialReference;
import com.mapfinal.converter.scene.SphericalMercatorProjection;

/**
 * 矢量切片基本信息
 * @author yangyong
 *
 */
public class VectorTile {

	private String name;
	private int z = 0;
	private int x = 0;
	private int y = 0;
	private int width = 256;
	private int height = 256;
	private Envelope envelope;
	private SpatialReference spatialReference;
	
	public VectorTile(String name) {
		// TODO Auto-generated constructor stub
		double d = SphericalMercatorProjection.MAX_DIMENSION;
		envelope =  new Envelope(-d, d, -d, d);
		setSpatialReference(new SpatialReference("EPSG:3857"));
		this.setName(name);
	}
	
	public VectorTile(String name, int x, int y, int z, Envelope envelope) {
		// TODO Auto-generated constructor stub
		this.setName(name);
		this.x = x; 
		this.y = y;
		this.z = z;
		this.envelope = envelope;
	}
	
	public String getId() {
		return name + "_" + z + "_" + x + "_" + y;
	}
	
	public String getIntactUrl(String url) {
		url = url.replace("{z}", String.valueOf(getZ()));
		url = url.replace("{x}", String.valueOf(getX()));
		url = url.replace("{y}", String.valueOf(getY()));
		return url;
	}

	public void set(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	public Envelope getEnvelope() {
		return envelope;
	}

	public void setEnvelope(Envelope envelope) {
		this.envelope = envelope;
	}

	public SpatialReference getSpatialReference() {
		return spatialReference;
	}

	public void setSpatialReference(SpatialReference spatialReference) {
		this.spatialReference = spatialReference;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
