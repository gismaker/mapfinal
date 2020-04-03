package com.mapfinal.map;

import com.mapfinal.converter.SpatialReference;
import com.mapfinal.converter.scene.SphericalMercatorProjection;
import org.locationtech.jts.geom.Envelope;

public class Tile {

	public static final String TILE_FILE = "tile_file";
	public static final String TILE_WEB = "tile_web";
	
	public static final int TMS_LT = 0;
	public static final int TMS_LB = 1;
	
	private String name;
	private int z = 0;
	private int x = 0;
	private int y = 0;
	private Envelope envelope;
	private SpatialReference spatialReference;
	
	public Tile(String name) {
		// TODO Auto-generated constructor stub
		double d = SphericalMercatorProjection.MAX_DIMENSION;
		envelope =  new Envelope(-d, d, -d, d);
		setSpatialReference(new SpatialReference("EPSG:3857"));
		this.setName(name);
	}
	
	public Tile(String name, int x, int y, int z, Envelope envelope) {
		// TODO Auto-generated constructor stub
		this.setName(name);
		this.x = x; 
		this.y = y;
		this.z = z;
		this.envelope = envelope;
	}
	
	public String getId() {
		return name + "_" + z + "_" + x + "_" + y 
				+ "_" + envelope.getMinX() + "_" + envelope.getMaxX() 
				+ "_" + envelope.getMinY() + "_" + envelope.getMaxY();
	}
	
	public String getImageId() {
		return name + "_" + z + "_" + x + "_" + y ;
	}
	
	public String getImageName() {
		return z + "_" + x + "_" + y;
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
}
