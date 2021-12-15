package com.mapfinal.dispatcher;

import java.util.HashMap;
import java.util.Map;

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
import com.mapfinal.render.pick.PickManager;
import com.mapfinal.render.style.Symbol;
import com.mapfinal.resource.ResourceDispatcher;

public class FeatureDispatcher extends Dispatcher {
	private Event event;
	private RenderEngine engine;
	private Map<String, Feature> pickFeatures;
	
	public FeatureDispatcher(SpatialIndexer indexer, ResourceDispatcher resource) {
		super(indexer, resource);
	}
	
	public Feature getPickFeature(String featurePickName) {
		if(pickFeatures!=null) {
			return pickFeatures.get(featurePickName);
		}
		return null;
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
			int colorOrg = 0;
			if(renderer!=null && renderer.isPickMode()) {
				String name = event.get("layerName");
				String featureName = name + "@" + feature.getId().toString();
				int color = PickManager.me().getRenderColor(featureName);
				colorOrg = renderer.getPickColor();
				renderer.setPickMode(color, true);
				if(pickFeatures==null) {
					pickFeatures = new HashMap<String, Feature>();
				}
				pickFeatures.put(featureName, feature);
			}
			Symbol symbol = renderer==null ? null : renderer.getSymbol(feature);
			//QueryParameter query = event.get("queryParameter");
			engine.renderFeature(event, symbol, feature);
			if(renderer!=null && renderer.isPickMode()) {
				renderer.setPickMode(colorOrg, true);
			}
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
			System.out.println("Screen Point: " + latlng.toString());
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
								System.out.println("Listener: featureId: " + feature.getId().toString());
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
