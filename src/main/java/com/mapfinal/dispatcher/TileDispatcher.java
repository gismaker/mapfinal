package com.mapfinal.dispatcher;

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
			engine.renderImageFeature(null, event.get("map"), feature);
			feature.destroy();
		}
		feature = null;
	}
	
	@SuppressWarnings("rawtypes")
	public void draw(Event event, RenderEngine engine, Renderer renderer) {
		if(!event.isRender()) return;
		this.event = event;
		this.engine = engine;
		MapContext context = event.get("map");
		TileResourceDispatcher resource = (TileResourceDispatcher) getResource();
		query(event.set("type", resource.getTmsType()).set("name", resource.getName()), context.getSceneEnvelope(), this);
		TileResource tileResource = (TileResource) resource;
		tileResource.setCurrentTileNumberOnScreen(this.getSioNumber());
		//System.out.println("[TileDispatcher]" + resource.getTileCache().print());
	}

	public boolean handleEvent(Event event) {
		return false;
	}
	
}
