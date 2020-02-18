package com.mapfinal.dispatcher;

import java.util.Map;

import com.mapfinal.event.Event;
import com.mapfinal.kit.SyncWriteMap;
import com.mapfinal.map.ImageFeature;
import com.mapfinal.map.MapContext;
import com.mapfinal.render.RenderEngine;
import com.mapfinal.render.Renderer;
import com.mapfinal.resource.tile.TileResourceObject;

public class VectorTileDispatcher extends Dispatcher {

	private Map<String, ImageFeature> features;
	private int zoom = -1;
	private Event event;
	private RenderEngine engine;
	
	public VectorTileDispatcher(SpatialIndexer indexer, TileResourceObject resource) {
		super(indexer, resource);
	}
	
	@Override
	public void resultAction(SpatialIndexObject sio) {
		// TODO Auto-generated method stub
		ImageFeature feature = getImageFeature(sio.getId());
		//boolean isExist = feature!=null ? feature.contains(sio.getEnvelope()) : false;
		if(feature==null) {
			feature = loadImageFeature(sio);
			if(feature!=null) {
				addImageFeature(feature);
				//Mapfinal.me().getScene().update();
			}
		}
		if(feature!=null) {
			//System.out.println("tile render: " + feature.getId());
			//tile.onRender(event, engine);
			feature.setEnvelope(sio.getEnvelope());
			MapContext map = event.get("map");
			engine.renderImageFeature(null, map, feature);
		}
	}
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
	
	public ImageFeature loadImageFeature(SpatialIndexObject sio) {
		ImageFeature tile =  null;
		if(getResource()!=null && getResource().getReader()!=null) {
			tile = (ImageFeature) getResource().getReader().read(sio);
		}
		return tile;
	}
	
	public void onRender(Event event, RenderEngine engine, Renderer renderer) {
		this.event = event;
		if(!event.isRender()) return;
		this.engine = engine;
		MapContext context = event.get("map");
		int decimalZoom = (int) context.getZoom();
		if(zoom < 0 || decimalZoom!=zoom) {
			//System.out.println("[TileDispatcher] decimalZoom: " + decimalZoom + ", zoom: " + zoom);
			zoom = decimalZoom;
			if(features!=null) {
				features.clear();
			}
		}
		TileResourceObject resource = (TileResourceObject) getResource();
		query(event.set("type", resource.getTmsType()).set("name", resource.getCacheFolder()), context.getSceneEnvelope(), this);
		/*
		int decimalZoom = (int) context.getZoom();
		if(zoom < 0 || decimalZoom!=zoom) {
			System.out.println("[TileDispatcher] decimalZoom: " + decimalZoom + ", zoom: " + zoom);
			zoom = decimalZoom;
			if(features!=null) {
				features.clear();
			}
			query(context, this);
			Mapfinal.me().getScene().update();
		} else {
			if(features!=null) {
				for (Tile tile : features.values()) {
					tile.onRender(event, engine);
				}
			}
		}*/
	}

	public void onEvent(Event event) {
		if(features==null) return;
		for (ImageFeature tile : features.values()) {
		}
	}
	
	public ImageFeature getImageFeature(String tileId) {
		ImageFeature tile =  null;
		if (this.features != null) {
			tile = this.features.get(tileId);
		}
		return tile;
	}

	public void addImageFeature(ImageFeature tile) {
		if (this.features == null) {
			this.features = new SyncWriteMap<String, ImageFeature>(32, 0.25F);
		}
		this.features.put(tile.getId(), tile);
	}

	public void removeImageFeature(ImageFeature tile) {
		if (this.features != null) {
			this.features.remove(tile.getId());
		}
	}

	public void removeImageFeature(String tileId) {
		if (this.features != null) {
			ImageFeature tile = this.features.get(tileId);
			if (tile != null)
			this.features.remove(tileId);
		}
	}
	
	public Map<String, ImageFeature> getImageFeatures() {
		return features;
	}

	
}
