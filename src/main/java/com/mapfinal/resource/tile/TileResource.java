package com.mapfinal.resource.tile;

import com.mapfinal.event.Event;
import com.mapfinal.map.Tile;
import com.mapfinal.resource.Resource;
import com.mapfinal.resource.image.Image;
import com.mapfinal.resource.image.LocalImage;

public class TileResource<M> implements Resource {

	// 名称
	private String name;
	// 文件路径 或 网络地址，唯一键
	private String url;
	// ResourceObject外接矩形
	// private Envelope envelope;
	// ResourceObject被调用次数
	private int reference = 0;
	/**
	 * 图片
	 */
	protected Image<M> image;
	/**
	 * 切片信息
	 */
	protected Tile tile;

	public TileResource(String url, Tile tile, FileType fileType) {
		this.name = tile.getImageId();
		this.url = url;
		this.tile = tile;
		if(FileType.http == fileType) {
			this.image = new TileRemoteImage<M>(tile.getImageId(), url);
		} else {
			this.image = new LocalImage<M>(tile.getImageId(), url);
		}
	}

	public TileResource(String url, Tile tile, Image<M> image) {
		this.name = tile.getImageId();
		this.url = url;
		this.tile = tile;
		this.image = image;
	}
	
	@Override
	public void execute(Event event) {
		// TODO Auto-generated method stub
		
	}

	public void setCollection(TileCollection collection) {
		this.image.setCollection(collection);
	}

	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		if (image != null)
			image.prepare();
	}

	@Override
	public void read() {
		// TODO Auto-generated method stub
		if (image != null)
			image.read();
	}

	@Override
	public void writer() {
		// TODO Auto-generated method stub
		if (image != null)
			image.writer();
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
	
	public String getName() {
		return name;
	}

//	public void setName(String name) {
//		this.name = name;
//	}

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
	
	@Override
	public TileResource reference() {
		// TODO Auto-generated method stub
		this.reference++;
		return this;
	}

	@Override
	public int referenceRelease() {
		// TODO Auto-generated method stub
		this.reference--;
		if(this.reference==0) destroy();
		return this.reference;
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		image.destroy();
	}
}
