package com.mapfinal.resource.tile;

import java.util.List;

import com.mapfinal.cache.Cache;
import com.mapfinal.cache.impl.ScreenLruCacheImpl;
import com.mapfinal.converter.CRS;
import com.mapfinal.dispatcher.Dispatcher;
import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.dispatcher.TileDispatcher;
import com.mapfinal.dispatcher.indexer.TileMercatorIndexer;
import com.mapfinal.map.Tile;
import com.mapfinal.resource.Resource.FileType;
import com.mapfinal.resource.ResourceCollection;
import com.mapfinal.resource.ResourceDispatcher;

public class TileCollection extends TileResourceDispatcher<TileFeature> implements ResourceCollection<TileFeature, String> {

	private String name;
	private Cache<String, TileFeature> tileCache;
	private int cacheScreenNum = 2;
	private String url;
	private int tmsType = Tile.TMS_LT;
	//分布式节点， 待完善
	private String[] subdomains;
	
	public TileCollection(String name, String url, FileType type) {
		this.name = name;
		this.url = url;
		int cacheSize = 30;
		tileCache = new ScreenLruCacheImpl<>(cacheSize);
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
	
	public TileFeature current(SpatialIndexObject sio) {
		TileFeature feature =  getFromCache(sio.getId());
		if(feature==null) {
			Tile tile = (Tile) sio.getOption("tile");
			String tileUrl = tile.getIntactUrl(this.url);
			feature = new TileFeature(tileUrl, tile);
			if(feature!=null) {
				putToCache(sio.getId(), feature);
			}
		}
		return feature;
	}
	
	public TileFeature loadFeature(SpatialIndexObject sio) {
		TileFeature feature =  null;
		Tile tile = (Tile) sio.getOption("tile");
		String tileUrl = tile.getIntactUrl(this.url);
		feature = new TileFeature(tileUrl, tile);
		if(feature!=null) {
			putToCache(sio.getId(), feature);
		}
		return feature;
	}
		
	public TileFeature getFeature(Tile tile) {
		TileFeature feature =  getFromCache(tile.getId());
		if(feature==null) {
			String tileUrl = tile.getIntactUrl(this.url);
			feature = new TileFeature(tileUrl, tile);
			if(feature!=null) {
				putToCache(tile.getId(), feature);
			}
		}
		return feature;
	}
	
	protected TileFeature getFromCache(String tileHash) {
		return tileCache.get(tileHash);
	}
	
	protected void putToCache(String tileHash, TileFeature tileFeature) {
		tileCache.put(tileHash, tileFeature);
	}
	
	@Override
	public void clear() {
		tileCache.clear();
    }

	public void setCacheScreenNum(int cacheScreenNum) {
		this.cacheScreenNum = cacheScreenNum;
		
		//重设置缓存大小，待完善
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
		return loadFeature(sio);
	}

	@Override
	public void close() {
		// undo
	}

	@Override
	public CRS getCRS() {
		return null;
	}

	@Override
	public TileFeature get(String tileHash) {
		return tileCache.get(tileHash);
	}

	@Override
	public void put(String tileHash, TileFeature tileFeature) {
		tileCache.put(tileHash, tileFeature);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return tileCache.size();
	}

	@Override
	public boolean remove(String key) {
		// TODO Auto-generated method stub
		return tileCache.remove(key);
	}

	@Override
	public List<String> keys() {
		// TODO Auto-generated method stub
		return tileCache.keys();
	}
}
