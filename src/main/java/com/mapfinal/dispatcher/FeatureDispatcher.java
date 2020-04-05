package com.mapfinal.dispatcher;

import com.mapfinal.event.Event;
import com.mapfinal.map.Feature;
import com.mapfinal.map.MapContext;
import com.mapfinal.render.RenderEngine;
import com.mapfinal.render.Renderer;
import com.mapfinal.resource.ResourceDispatcher;

public class FeatureDispatcher extends Dispatcher {
	private Event event;
	private RenderEngine engine;
	
	public FeatureDispatcher(SpatialIndexer indexer, ResourceDispatcher resource) {
		super(indexer, resource);
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public void resultAction(SpatialIndexObject sio) {
		// TODO Auto-generated method stub
		MapContext context = event.get("map");
		boolean bintersects = context.getSceneEnvelope().intersects(sio.getEnvelope());
		if(!bintersects) return;
//		if(context.getZoom() > 13) {
//			System.out.println("[FeatureDispatcher] id: " + sio.getId() + ", env: " + sio.getEnvelope().toString());
//		}
		//System.out.println("[FeatureDispatcher] resultAction id: " + id);
		Feature feature = (Feature) getResource().read(sio);
		if(feature!=null) {
			//System.out.println("[FeatureDispatcher] resultAction render id: " + id);
			Renderer renderer = event.get("renderer");
			//QueryParameter query = event.get("queryParameter");
			engine.renderFeature(renderer, context, feature);
		}
	}
	
	public void draw(Event event, RenderEngine engine, Renderer renderer) {
		this.event = event;
		this.engine = engine;
		MapContext context = event.get("map");
		//long start = System.currentTimeMillis();
		query(event, context.getSceneEnvelope(), this);
		//System.out.println("[FeatureDispatcher] size: " + size + ", scene: " + context.getSceneEnvelope().toString());
		//System.out.println("[FeatureDispatcher] draw times: " + (System.currentTimeMillis() - start));
	}


	public Event getEvent() {
		return event;
	}

	public RenderEngine getEngine() {
		return engine;
	}
}
