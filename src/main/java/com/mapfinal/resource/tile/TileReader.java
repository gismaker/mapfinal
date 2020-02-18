package com.mapfinal.resource.tile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mapfinal.converter.CRS;
import com.mapfinal.dispatcher.Dispatcher;
import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.dispatcher.TileDispatcher;
import com.mapfinal.dispatcher.indexer.TileMercatorIndexer;
import com.mapfinal.map.GeoElement;
import com.mapfinal.map.ImageFeature;
import com.mapfinal.map.Tile;
import com.mapfinal.resource.ResourceReader;

public class TileReader implements ResourceReader {

	private TileResourceObject resource;

	public TileReader(TileResourceObject resource) {
		this.resource = resource;
	}

	@Override
	public Dispatcher connection() {
		// TODO Auto-generated method stub
		return new TileDispatcher(new TileMercatorIndexer(), resource);
	}

	@Override
	public Map<String, GeoElement> read(List<SpatialIndexObject> objs) {
		// TODO Auto-generated method stub
		if (objs == null || objs.size() < 1)
			return null;
		Map<String, GeoElement> features = new HashMap<String, GeoElement>(objs.size());
		for (SpatialIndexObject spatialIndexObject : objs) {
			GeoElement f = read(spatialIndexObject);
			features.put(spatialIndexObject.getId(), f);
		}
		return features;
	}

	@Override
	public GeoElement read(SpatialIndexObject obj) {
		// TODO Auto-generated method stub
		String url = resource.getUrl();
		Tile tile = (Tile) obj.getOption("tile");
		url = tile.getIntactUrl(url);
//		String type = resource.getType();
		resource.getTileCache().getImageCache().get(url);
//		if (Tile.TILE_FILE.equals(type)) {
//			Mapfinal.me().getImageCache().getImage(tile.getId(), resource.getCacheFolder(), url,
//					ImageCacheHandle.Type.file);
//		} else if (Tile.TILE_WEB.equals(type)) {
//			Mapfinal.me().getImageCache().getImage(tile.getId(), resource.getCacheFolder(), url,
//					ImageCacheHandle.Type.http);
//		}
		return new ImageFeature(url, resource.getImageType(), tile);
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public CRS getCRS() {
		// TODO Auto-generated method stub
		return null;
	}
}
