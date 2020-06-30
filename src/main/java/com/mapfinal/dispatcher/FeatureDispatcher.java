package com.mapfinal.dispatcher;

import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.index.ItemVisitor;

import com.mapfinal.converter.scene.ScenePoint;
import com.mapfinal.event.Event;
import com.mapfinal.geometry.GeoKit;
import com.mapfinal.geometry.Latlng;
import com.mapfinal.geometry.ScreenPoint;
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
			engine.renderFeature(event, null, feature);
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
	
	public boolean handleEvent(Event event) {
		if(event.isAction("mouseClick")) {
			MapContext context = event.get("map");
			//用户点击的像素坐标
			ScreenPoint sp = event.get("screenPoint");
			Latlng latlng = context.pointToLatLng(ScenePoint.by(sp));
			Point point = GeoKit.createPoint(latlng);
			query(event, new Envelope(latlng), new ItemVisitor() {
				@Override
				public void visitItem(Object item) {
					if (item instanceof SpatialIndexObject) {
						SpatialIndexObject sio = (SpatialIndexObject) item;
						boolean bintersects = context.getSceneEnvelope().intersects(sio.getEnvelope());
						if(!bintersects) return;
						Feature feature = (Feature) getResource().read(sio);
						if(feature!=null) {
							boolean flag = feature.intersects(point);
							if(flag) {
								event.put("featureSelected", feature);
								return;
							}
						}
					}
				}
			});
			if(event.get("featureSelected")!=null)  {
				return true;
			}
		}
		return false;
	}


	public Event getEvent() {
		return event;
	}

	public RenderEngine getEngine() {
		return engine;
	}
}
