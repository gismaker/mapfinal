package com.mapfinal.resource.tile;

import java.util.List;

import com.mapfinal.cache.Cache;
import com.mapfinal.cache.impl.ScreenLruCacheImpl;
import com.mapfinal.converter.SpatialReference;
import com.mapfinal.dispatcher.Dispatcher;
import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.dispatcher.TileDispatcher;
import com.mapfinal.dispatcher.indexer.TileMercatorIndexer;
import com.mapfinal.map.Tile;
import com.mapfinal.map.TileFeature;
import com.mapfinal.resource.Resource.FileType;
import com.mapfinal.resource.ResourceCollection;

public class TileCollection extends TileResourceDispatcher<TileFeature> implements ResourceCollection<String, TileResource> {

	private String name;
	private Cache<String, TileResource> resourceCache;
	private int cacheScreenNum = 2;
	private int screenTileNumber = 0;
	private String url;
	private int tmsType = Tile.TMS_LT;
	private FileType fileType = FileType.file;
	private SpatialReference spatialReference = SpatialReference.mercator();
	//分布式节点， 待完善
	private String[] subdomains;
	
	public TileCollection(String name, String url, FileType type) {
		this.name = name;
		this.url = url;
		this.fileType = type;
		this.screenTileNumber = 15;
		int cacheSize = cacheScreenNum * screenTileNumber;
		resourceCache = new ScreenLruCacheImpl<String, TileResource>(cacheSize);
		TileManager.me().addCollection(name, this);
	}
	
	public TileFeature createFeature(String url, Tile tile) {
		TileResource resource = getResource(url, tile);
		TileFeature feature = new TileFeature(this.name, resource.getName(), tile);
		return feature;
	}
	
	public TileResource getResource(String url, Tile tile) {
		TileResource resource = get(tile.getImageId());
		if(resource==null) {
			resource = new TileResource(url, tile, fileType);
			//resource.setCollection(this);
			put(tile.getImageId(), resource);
		}
		return resource;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "tile";
	}

	@Override
	public long getMemorySize() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void clear() {
		resourceCache.clear();
    }
	
	public void setCurrentTileNumberOnScreen(int numTile) {
		if(numTile < 1) return;
		this.screenTileNumber = numTile;
		int cacheSize = cacheScreenNum * screenTileNumber;
		if(this.resourceCache instanceof ScreenLruCacheImpl) {
			ScreenLruCacheImpl slc = (ScreenLruCacheImpl) this.resourceCache;
			slc.resize(cacheSize);
		}
	}

	public void setCacheScreenNum(int cacheScreenNum) {
		this.cacheScreenNum = cacheScreenNum;
		//重设置缓存大小，待完善
		int cacheSize = cacheScreenNum * screenTileNumber;
		if(this.resourceCache instanceof ScreenLruCacheImpl) {
			ScreenLruCacheImpl slc = (ScreenLruCacheImpl) this.resourceCache;
			slc.resize(cacheSize);
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getTmsType() {
		return tmsType;
	}

	public void setTmsType(int tmsType) {
		this.tmsType = tmsType;
	}

	public String[] getSubdomains() {
		return subdomains;
	}

	public void setSubdomains(String[] subdomains) {
		this.subdomains = subdomains;
	}

	public int getCacheScreenNum() {
		return cacheScreenNum;
	}

	@Override
	public Dispatcher connection() {
		return new TileDispatcher(new TileMercatorIndexer(), this);
	}

	@Override
	public TileFeature read(SpatialIndexObject sio) {
		Tile tile = (Tile) sio.getOption("tile");
		String tileUrl = tile.getIntactUrl(this.url);
		TileFeature feature = createFeature(tileUrl, tile);
//		System.out.println("[TileCollection] resourceCache: " + resourceCache.size());
		return feature;
	}

	@Override
	public void close() {
		// undo
	}

	@Override
	public TileResource get(String tileHash) {
		return resourceCache.get(tileHash);
	}

	@Override
	public void put(String tileHash, TileResource tileResource) {
		resourceCache.put(tileHash, tileResource);
		tileResource.setCollection(this);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return resourceCache.size();
	}

	@Override
	public boolean remove(String key) {
		// TODO Auto-generated method stub
		return resourceCache.remove(key);
	}

	@Override
	public List<String> keys() {
		// TODO Auto-generated method stub
		return resourceCache.keys();
	}

	public FileType getFileType() {
		return fileType;
	}

	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}

	public SpatialReference getSpatialReference() {
		return spatialReference;
	}

	public void setSpatialReference(SpatialReference spatialReference) {
		this.spatialReference = spatialReference;
	}
}
