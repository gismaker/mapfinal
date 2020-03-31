package com.mapfinal.resource.tile;

import java.util.Map;

import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;

import com.mapfinal.converter.SpatialReference;
import com.mapfinal.event.Event;
import com.mapfinal.map.ImageFeature;
import com.mapfinal.map.Tile;
import com.mapfinal.resource.Resource;
import com.mapfinal.resource.image.Image;
import com.mapfinal.resource.image.LocalImage;

public class TileFeature<M> extends ImageFeature<M> implements Resource {
	
	//名称
	private String name;
	//文件路径 或 网络地址，唯一键
	private String url;
	//ResourceObject外接矩形
	//private Envelope envelope;
	//ResourceObject被调用次数
	private int reference = 0;
		
	protected Image<M> image;
	protected Tile tile;
	
	public TileFeature(String url, Tile tile) {
		this.name = tile.getId();
		this.url = url;
		this.tile = tile;
		this.image = new LocalImage<>(tile.getId(), url);
	}
	
	public TileFeature(String url, Tile tile, Image image) {
		this.name = tile.getId();
		this.url = url;
		this.tile = tile;
		this.image = image;
	}
	
	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		if(image != null) image.prepare();
	}

	@Override
	public void read() {
		// TODO Auto-generated method stub
		if(image != null) image.read();
	}

	@Override
	public void writer() {
		// TODO Auto-generated method stub
		if(image != null) image.writer();
	}

	public M getImage() {
		// TODO Auto-generated method stub
		return image.getData();
	}
	
	public Image<M> getImageResource() {
		return image;
	}

	public int getWidth() {
		return image != null ? image.getWidth() : 0;
	}

	public int getHeight() {
		return image != null ? image.getHeight() : 0;
	}

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
	
	@Override
	public void execute(Event event) {
		// TODO Auto-generated method stub
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getReference() {
		return reference;
	}

	public void setReference(int reference) {
		this.reference = reference;
	}
}
