package com.mapfinal.dispatcher;

import org.locationtech.jts.index.ItemVisitor;

import com.mapfinal.event.Event;
import com.mapfinal.map.GeoImage;
import com.mapfinal.map.MapContext;
import com.mapfinal.render.RenderEngine;
import com.mapfinal.render.Renderer;
import com.mapfinal.resource.tile.TileResource;
import com.mapfinal.resource.tile.TileResourceDispatcher;

public class TileDispatcher extends Dispatcher {

	private Event event;
	private RenderEngine engine;
	private int lastZoom = -1;
	private boolean randerCacheLayer = true;
	
	@SuppressWarnings("rawtypes")
	public TileDispatcher(SpatialIndexer indexer, TileResourceDispatcher resource) {
		super(indexer, resource);
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public void resultAction(SpatialIndexObject sio) {
		// TODO Auto-generated method stub
		TileResourceDispatcher tro = (TileResourceDispatcher) getResource();
		GeoImage feature = (GeoImage) tro.read(sio);
		if(feature!=null) {
			engine.renderImageFeature(event, null, feature);
			feature.destroy();
		}
		feature = null;
	}
	
	@SuppressWarnings("rawtypes")
	public void draw(Event event, RenderEngine engine, Renderer renderer) {
		if(!event.isRender()) return;
		this.event = event;
		this.engine = engine;
		randerCacheLayer = event.get("tile_randerCacheLayer", randerCacheLayer);
		MapContext context = event.get("map");
		if(lastZoom==-1) lastZoom = (int) context.getZoom();
		TileResourceDispatcher resource = (TileResourceDispatcher) getResource();
		int izoom = (int) context.getZoom();
		event.set("type", resource.getTmsType()).set("name", resource.getName());
		if(izoom > 0 && randerCacheLayer){
			query(event.set("zoom", izoom-1), context.getSceneEnvelope(), new ItemVisitor() {
				@Override
				public void visitItem(Object item) {
					// TODO Auto-generated method stub
					if (item instanceof SpatialIndexObject) {
						SpatialIndexObject sio = (SpatialIndexObject) item;
						sio.setOption("rendertype", "renderOnCache");
						GeoImage feature = (GeoImage) resource.read(sio);
						if(feature!=null) {
							engine.renderImageFeature(event, null, feature);
							feature.destroy();
						}
						feature = null;
					}
				}
			});
		}
		query(event.set("zoom", izoom), context.getSceneEnvelope(), this);
		TileResourceDispatcher tileResource = (TileResourceDispatcher) resource;
		tileResource.setCurrentTileNumberOnScreen(this.getSioNumber());
		//System.out.println("[TileDispatcher]" + resource.getTileCache().print());
		lastZoom = (int) context.getZoom();
	}

	public boolean handleEvent(Event event) {
		return false;
	}

	public boolean isRanderCacheLayer() {
		return randerCacheLayer;
	}

	public void setRanderCacheLayer(boolean randerCacheLayer) {
		this.randerCacheLayer = randerCacheLayer;
	}
	
}
