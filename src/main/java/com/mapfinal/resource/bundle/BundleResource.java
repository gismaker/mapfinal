package com.mapfinal.resource.bundle;

import java.util.List;

import com.mapfinal.cache.Cache;
import com.mapfinal.cache.impl.ScreenLruCacheImpl;
import com.mapfinal.converter.CRS;
import com.mapfinal.converter.SpatialReference;
import com.mapfinal.dispatcher.Dispatcher;
import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.dispatcher.TileDispatcher;
import com.mapfinal.dispatcher.indexer.TileMercatorIndexer;
import com.mapfinal.map.Tile;
import com.mapfinal.resource.Resource;
import com.mapfinal.resource.tile.TileResourceDispatcher;

public class BundleResource extends TileResourceDispatcher<BundleFeature> implements Resource<BundleFeature> {

	private String name;
	private Cache<String, BundleFeature> cache;
	private int cacheScreenNum = 2;
	private int screenTileNumber = 15;
	private String url;
	private int tmsType = Tile.TMS_LT;
	private SpatialReference spatialReference = SpatialReference.mercator();
	
	public BundleResource(String name, String url) {
		this.name = name;
		this.url = url;
		this.screenTileNumber = 15;
		int cacheSize = cacheScreenNum * screenTileNumber;
		cache = new ScreenLruCacheImpl<>(cacheSize);
	}
	
	@Override
	public void prepare() {
		// TODO Auto-generated method stub
	}

	@Override
	public BundleFeature read() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writer(BundleFeature data) {
		// TODO Auto-generated method stub
		data.writer();
	}
	
	@Override
	public Dispatcher connection() {
		// TODO Auto-generated method stub
		return new TileDispatcher(new TileMercatorIndexer(), this);
	}
	@Override
	public BundleFeature read(SpatialIndexObject sio) {
		BundleFeature feature =  getFromCache(sio.getId());
		if(feature==null) {
			Tile tile = (Tile) sio.getOption("tile");
			String tileUrl = tile.getIntactUrl(this.url);
			feature = new BundleFeature(tileUrl, tile);
			if(feature!=null) {
				feature.setCollectionKey(this.name);
				putToCache(sio.getId(), feature);
			}
		}
		return feature;
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
	
	public BundleFeature getFeature(Tile tile) {
		BundleFeature feature =  getFromCache(tile.getId());
		if(feature==null) {
			String tileUrl = tile.getIntactUrl(this.url);
			feature = new BundleFeature(tileUrl, tile);
			if(feature!=null) {
				putToCache(tile.getId(), feature);
			}
		}
		return feature;
	}
	
	protected BundleFeature getFromCache(String tileHash) {
		return cache.get(tileHash);
	}
	
	protected void putToCache(String tileHash, BundleFeature tileFeature) {
		put(tileHash, tileFeature);
	}
	
	
	public void setCurrentTileNumberOnScreen(int numTile) {
		if(numTile < 1 || this.screenTileNumber == numTile) return;
		this.screenTileNumber = numTile;
		int cacheSize = cacheScreenNum * screenTileNumber;
		if(this.cache instanceof ScreenLruCacheImpl) {
			ScreenLruCacheImpl slc = (ScreenLruCacheImpl) this.cache;
			slc.resize(cacheSize);
		}
	}
	
	public void setCacheScreenNum(int cacheScreenNum) {
		if(this.screenTileNumber == cacheScreenNum) return;
		this.cacheScreenNum = cacheScreenNum;
		//重设置缓存大小，待完善
		int cacheSize = cacheScreenNum * screenTileNumber;
		if(this.cache instanceof ScreenLruCacheImpl) {
			ScreenLruCacheImpl slc = (ScreenLruCacheImpl) this.cache;
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


	public int getCacheScreenNum() {
		return cacheScreenNum;
	}

	@Override
	public void close() {
		// undo
	}

	public BundleFeature get(String tileHash) {
		return cache.get(tileHash);
	}

	public void put(String tileHash, BundleFeature tileFeature) {
		cache.put(tileHash, tileFeature);
	}

	public int size() {
		// TODO Auto-generated method stub
		return cache.size();
	}
	
	public void clear() {
		cache.clear();
    }

	public boolean remove(String key) {
		// TODO Auto-generated method stub
		return cache.remove(key);
	}

	public List<String> keys() {
		// TODO Auto-generated method stub
		return cache.keys();
	}

	public SpatialReference getSpatialReference() {
		return spatialReference;
	}

	public void setSpatialReference(SpatialReference spatialReference) {
		this.spatialReference = spatialReference;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		clear();
	}

}
