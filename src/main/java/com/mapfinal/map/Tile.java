package com.mapfinal.map;

import com.mapfinal.converter.SpatialReference;
import com.mapfinal.converter.scene.SphericalMercatorProjection;
import org.locationtech.jts.geom.Envelope;

/**
 * 瓦片属性
 * @author yangyong
 *
 */
public class Tile {

	public static final int TMS_LT = 0;
	public static final int TMS_LB = 1;
	
	private String name;
	private int z = 0;
	private int x = 0;
	private int y = 0;
	private Envelope envelope;
	private SpatialReference spatialReference = SpatialReference.mercator();
	
	public Tile(String name) {
		// TODO Auto-generated constructor stub
		double d = SphericalMercatorProjection.MAX_DIMENSION;
		envelope =  new Envelope(-d, d, -d, d);
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
	
	public Tile(String name, int x, int y, int z) {
		// TODO Auto-generated constructor stub
		this.setName(name);
		this.x = x; 
		this.y = y;
		this.z = z;
		double fullBoundsMinX = -SphericalMercatorProjection.MAX_DIMENSION;
        double fullBoundsMinY = -SphericalMercatorProjection.MAX_DIMENSION;
        int tilesInMapOneDimension = 1 << z;
        double halfTilesInMapOneDimension = tilesInMapOneDimension * 0.5;
        double tilesSizeOneDimension = SphericalMercatorProjection.MAX_DIMENSION / halfTilesInMapOneDimension;
        double minX = fullBoundsMinX + x * tilesSizeOneDimension;
        double minY = fullBoundsMinY + y * tilesSizeOneDimension;
        Envelope tileEnv = new Envelope(
                minX,
                minX + tilesSizeOneDimension,
                minY,
                minY + tilesSizeOneDimension);
        this.envelope = tileEnv;
	}
	
	public String getId() {
		return name.replaceAll("_", "-") + "_" + z + "_" + x + "_" + y 
				+ "_" + envelope.getMinX() + "_" + envelope.getMaxX() 
				+ "_" + envelope.getMinY() + "_" + envelope.getMaxY();
	}
	
	public String getImageId() {
		return name.replaceAll("_", "-") + "_" + z + "_" + x + "_" + y ;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SpatialReference getSpatialReference() {
		return spatialReference;
	}

	public void setSpatialReference(SpatialReference spatialReference) {
		this.spatialReference = spatialReference;
	}
}
