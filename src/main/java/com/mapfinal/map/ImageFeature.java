package com.mapfinal.map;

import java.util.Map;

import com.mapfinal.converter.SpatialReference;
import com.mapfinal.resource.Resource;
import com.mapfinal.resource.ResourceManager;
import com.mapfinal.resource.ResourceObject;
import com.mapfinal.resource.tile.TileResourceObject;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;

public class ImageFeature implements GeoElement {

	private String url;
	private String resourceName;
	private String imageName;// tile.getImageName
	private Resource.ImageType imageType;
	private int width;
	private int height;
	private Envelope envelope;
	private SpatialReference spatialReference;

	public ImageFeature(String url, Resource.ImageType imageType, String imageName, Envelope envelope,
			SpatialReference spatialReference) {
		// TODO Auto-generated constructor stub
		this.setImageName(imageName);
		this.url = url;
		this.setImageType(imageType);
		this.envelope = envelope;
		this.spatialReference = spatialReference;
	}

	public ImageFeature(String url, Resource.ImageType imageType, Tile tile) {
		this.url = url;
		this.imageType = imageType;
		this.imageName = tile.getImageName();
		this.envelope = tile.getEnvelope();
		this.spatialReference = tile.getSpatialReference();
	}

	public Object getImage() {
		// TODO Auto-generated method stub
		ResourceObject ro = ResourceManager.me().getResource(resourceName);
		if(ro instanceof TileResourceObject) {
			TileResourceObject tro = (TileResourceObject) ro;
			return tro.getImage(url);
		} else {
			return ResourceManager.me().getImageCache().get(url);
		}
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

	public Resource.ImageType getImageType() {
		return imageType;
	}

	public void setImageType(Resource.ImageType imageType) {
		this.imageType = imageType;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
		// TODO Auto-generated method stub
		return url;
	}

}
