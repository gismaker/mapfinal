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
import com.mapfinal.resource.Resource;

public class TileResource extends TileResourceDispatcher<TileFeature> implements Resource<TileData> {

	private String name;
	private Cache<String, TileData> dataCache;
	private int cacheScreenNum = 2;
	private int screenTileNumber = 15;
	private String url;
	private int tmsType = Tile.TMS_LT;
	private FileType fileType = FileType.file;
	private SpatialReference spatialReference = SpatialReference.mercator();
	//分布式节点， 待完善
	private String[] subdomains;
	
	public TileResource(String name, String url, FileType type) {
		this.name = name;
		this.url = url;
		this.fileType = type;
		this.screenTileNumber = 15;
		int cacheSize = cacheScreenNum * screenTileNumber;
		dataCache = new ScreenLruCacheImpl<String, TileData>(cacheSize);
	}
	
	@Override
	public void prepare() {
		// TODO Auto-generated method stub
	}

	@Override
	public TileData read() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writer(TileData data) {
		// TODO Auto-generated method stub
		data.writer();
	}
	
	public TileFeature createFeature(String url, Tile tile, boolean renderOnCache) {
		TileData resource = getResource(url, tile, renderOnCache);
		TileFeature feature = new TileFeature(resource, tile);
		return feature;
	}
	
	public TileData getResource(String url, Tile tile, boolean renderOnCache) {
		TileData resource = get(tile.getImageId());
		if(resource==null) {
			resource = new TileData(url, tile, fileType, renderOnCache);
			//resource.setCollection(this);
			if(!renderOnCache) put(tile.getImageId(), resource);
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
	
	public void setCurrentTileNumberOnScreen(int numTile) {
		if(numTile < 1 || this.screenTileNumber == numTile) return;
		this.screenTileNumber = numTile;
		int cacheSize = cacheScreenNum * screenTileNumber;
		if(this.dataCache instanceof ScreenLruCacheImpl) {
			ScreenLruCacheImpl slc = (ScreenLruCacheImpl) this.dataCache;
			slc.resize(cacheSize);
		}
	}

	public void setCacheScreenNum(int cacheScreenNum) {
		if(this.screenTileNumber == cacheScreenNum) return;
		this.cacheScreenNum = cacheScreenNum;
		//重设置缓存大小，待完善
		int cacheSize = cacheScreenNum * screenTileNumber;
		if(this.dataCache instanceof ScreenLruCacheImpl) {
			ScreenLruCacheImpl slc = (ScreenLruCacheImpl) this.dataCache;
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
		
		//加入String[] subdomains
		boolean renderOnCache = false;
		if("renderOnCache".equals(sio.getOption("rendertype"))) {
			renderOnCache = true;
		}
		TileFeature feature = createFeature(tileUrl, tile, renderOnCache);
//		System.out.println("[TileCollection] dataCache: " + dataCache.size());
		return feature;
	}

	@Override
	public void close() {
		// undo
	}

	public TileData get(String tileHash) {
		return dataCache.get(tileHash);
	}

	public void put(String tileHash, TileData tileData) {
		dataCache.put(tileHash, tileData);
	}

	public int size() {
		// TODO Auto-generated method stub
		return dataCache.size();
	}

	public void clear() {
		dataCache.clear();
    }
	
	public boolean remove(String key) {
		// TODO Auto-generated method stub
		return dataCache.remove(key);
	}

	public List<String> keys() {
		// TODO Auto-generated method stub
		return dataCache.keys();
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

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		dataCache.clear();
	}

	
}
