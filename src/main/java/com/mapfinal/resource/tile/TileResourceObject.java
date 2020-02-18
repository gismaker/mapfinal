package com.mapfinal.resource.tile;

import java.io.File;

import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.event.Event;
import com.mapfinal.map.ImageFeature;
import com.mapfinal.map.Tile;
import com.mapfinal.resource.Resource;
import com.mapfinal.resource.Resource.CacheType;
import com.mapfinal.resource.ResourceObject;

/**
 * Tile切片资源管理对象
 * @author yangyong
 */
public class TileResourceObject extends ResourceObject {

	private int tmsType = Tile.TMS_LT;
	private Resource.FileType fileType;
	private Resource.ImageType imageType = Resource.ImageType.png;
	
	public TileResourceObject(String name, String url, Resource.FileType fileType) {
		super(name, url, Resource.Type.tile);
		// TODO Auto-generated constructor stub
		this.setReader(new TileReader(this));
		this.setWriter(new TileWriter(this));
		this.setFileType(fileType);
		this.setCache(new TileCache(this, CacheType.LRU));
	}
	
	@Override
	public void renderBefore(Event event) {
		// TODO Auto-generated method stub
		super.renderBefore(event);
		getTileCache().clear();
	}
	
	public ImageFeature curreatFeature(SpatialIndexObject sio) {
		ImageFeature feature =  getTileCache().get(sio.getId());
		if(feature==null && getReader()!=null) {
			feature = (ImageFeature) getReader().read(sio);
			if(feature!=null) {
				getTileCache().put(feature.getId(), feature);
			}
		}
		return feature;
	}
	
	public ImageFeature loadFeature(SpatialIndexObject sio) {
		ImageFeature feature =  null;
		if(getReader()!=null) {
			feature = (ImageFeature) getReader().read(sio);
		}
		if(feature!=null) {
			getTileCache().put(feature.getId(), feature);
		}
		return feature;
	}
	
	public TileCache getTileCache() {
		return (TileCache) getCache();
	}
	
	public Object getImage(String url) {
		return getTileCache().getImage(url);
	}
	
	public int getTmsType() {
		return tmsType;
	}

	public void setTmsType(int tmsType) {
		this.tmsType = tmsType;
	}

	public Resource.ImageType getImageType() {
		return imageType;
	}

	public void setImageType(Resource.ImageType imageType) {
		this.imageType = imageType;
	}

	/**
	 * 生成图像名称
	 * 
	 * @param id
	 * @param folder
	 * @param imageType
	 * @return
	 */
	public String imageFileName(String imageName, String mainCacheFolder) {
		return mainCacheFolder + File.separator + getCacheFolder() + File.separator + imageName + "." + imageType.toString();
	}
	
	public Resource.FileType getFileType() {
		return fileType;
	}

	public void setFileType(Resource.FileType fileType) {
		this.fileType = fileType;
	}
}
