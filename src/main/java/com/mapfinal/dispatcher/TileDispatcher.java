package com.mapfinal.dispatcher;


import com.mapfinal.event.Event;
import com.mapfinal.map.GeoImage;
import com.mapfinal.map.MapContext;
import com.mapfinal.render.RenderEngine;
import com.mapfinal.render.Renderer;
import com.mapfinal.resource.tile.TileCollection;
import com.mapfinal.resource.tile.TileResourceDispatcher;

public class TileDispatcher extends Dispatcher {

	private int zoom = -1;
	private Event event;
	private RenderEngine engine;
	private int minZoom = 0;
	private int maxZoom = 18;
	
	public TileDispatcher(SpatialIndexer indexer, TileResourceDispatcher resource) {
		super(indexer, resource);
	}
	
	@Override
	public void resultAction(SpatialIndexObject sio) {
		// TODO Auto-generated method stub
		TileResourceDispatcher tro = (TileResourceDispatcher) getResource();
		GeoImage feature = (GeoImage) tro.read(sio);
		if(feature!=null) {
			//System.out.println("tile render: " + feature.getId());
			//tile.onRender(event, engine);
			//feature.setEnvelope(sio.getEnvelope());
			engine.renderImageFeature(null, event.get("map"), feature);
		}
	}
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
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
		TileResourceDispatcher resource = (TileResourceDispatcher) getResource();
		query(event.set("type", resource.getTmsType()).set("name", resource.getName()), context.getSceneEnvelope(), this);
		//System.out.println("[TileDispatcher]" + resource.getTileCache().print());
	}

	public void onEvent(Event event) {
		//TileCollection tro = (TileCollection) getResource();
		//List<String> keys = tro.getTileCache().getKeys();
		//for (String key : keys) {
		//}
	}
	
//	public ImageFeature getImageFeature(String featureId) {
//		TileCollection tro = (TileCollection) getResource();
//		return tro.getTileCache().get(featureId);
//	}
//
//	public void removeImageFeature(String featureId) {
//		TileCollection tro = (TileCollection) getResource();
//		tro.getTileCache().remove(featureId);
//	}
//	
//	public List<String> getFeatureIds() {
//		TileCollection tro = (TileCollection) getResource();
//		return tro.getTileCache().keys();
//	}

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
