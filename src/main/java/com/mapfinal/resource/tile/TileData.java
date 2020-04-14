package com.mapfinal.resource.tile;

import com.mapfinal.map.Tile;
import com.mapfinal.resource.Data;
import com.mapfinal.resource.Resource.FileType;
import com.mapfinal.resource.image.Image;
import com.mapfinal.resource.image.LocalImage;

public class TileData<M> implements Data {

	// 名称
	private String name;
	// 文件路径 或 网络地址，唯一键
	private String url;
	// 被调用次数
	private int reference = 0;
	/**
	 * 图片
	 */
	private Image<M> image;
	/**
	 * 切片信息
	 */
	private Tile tile;

	public TileData(String url, Tile tile, FileType fileType) {
		this.name = tile.getImageId();
		this.url = url;
		this.setTile(tile);
		if(FileType.http == fileType) {
			this.image = new TileRemoteImage<M>(tile.getImageId(), url, tile.getName());
		} else {
			this.image = new LocalImage<M>(tile.getImageId(), url);
		}
	}

	public TileData(String url, Tile tile, Image<M> image) {
		this.name = tile.getImageId();
		this.url = url;
		this.setTile(tile);
		this.image = image;
	}
	
	public void prepare() {
		// TODO Auto-generated method stub
		if (image != null)
			image.prepare();
	}

	public void read() {
		// TODO Auto-generated method stub
		if (image != null)
			image.read();
	}

	public void writer() {
		// TODO Auto-generated method stub
		if (image != null)
			image.writer();
	}

	public M getImageData() {
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
	
	public TileData<M> reference() {
		// TODO Auto-generated method stub
		this.reference++;
		return this;
	}

	public int referenceRelease() {
		// TODO Auto-generated method stub
		this.reference--;
		//if(this.reference==0) destroy();
		return this.reference;
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		image.destroy();
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}
}
