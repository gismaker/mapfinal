package com.mapfinal.dispatcher;

import java.util.List;

import com.mapfinal.event.Event;
import com.mapfinal.map.ImageFeature;
import com.mapfinal.map.MapContext;
import com.mapfinal.render.RenderEngine;
import com.mapfinal.render.Renderer;
import com.mapfinal.resource.tile.TileResourceObject;

public class TileDispatcher extends Dispatcher {

	private int zoom = -1;
	private Event event;
	private RenderEngine engine;
	private int minZoom = 0;
	private int maxZoom = 18;
	
	public TileDispatcher(SpatialIndexer indexer, TileResourceObject resource) {
		super(indexer, resource);
	}
	
	@Override
	public void resultAction(SpatialIndexObject sio) {
		// TODO Auto-generated method stub
		TileResourceObject tro = (TileResourceObject) getResource();
		ImageFeature feature = tro.curreatFeature(sio);
		if(feature!=null) {
			//System.out.println("tile render: " + feature.getId());
			//tile.onRender(event, engine);
			feature.setEnvelope(sio.getEnvelope());
			engine.renderImageFeature(null, event.get("map"), feature);
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
		}
		TileResourceObject resource = (TileResourceObject) getResource();
		query(event.set("type", resource.getTmsType()).set("name", resource.getCacheFolder()), context.getSceneEnvelope(), this);
		System.out.println("[TileDispatcher]" + resource.getTileCache().print());
	}

	public void onEvent(Event event) {
		//TileResourceObject tro = (TileResourceObject) getResource();
		//List<String> keys = tro.getTileCache().getKeys();
		//for (String key : keys) {
		//}
	}
	
	public ImageFeature getImageFeature(String featureId) {
		TileResourceObject tro = (TileResourceObject) getResource();
		return tro.getTileCache().get(featureId);
	}

	public void removeImageFeature(String featureId) {
		TileResourceObject tro = (TileResourceObject) getResource();
		tro.getTileCache().remove(featureId);
	}
	
	public List<String> getFeatureIds() {
		TileResourceObject tro = (TileResourceObject) getResource();
		return tro.getTileCache().keys();
	}

	public int getMinZoom() {
		return minZoom;
	}

	public void setMinZoom(int minZoom) {
		this.minZoom = minZoom;
	}

	public int getMaxZoom() {
		return maxZoom;
	}

	public void setMaxZoom(int maxZoom) {
		this.maxZoom = maxZoom;
	}

	
}
