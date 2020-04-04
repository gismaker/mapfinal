package com.mapfinal.dispatcher;

import com.mapfinal.event.Event;
import com.mapfinal.map.Feature;
import com.mapfinal.map.MapContext;
import com.mapfinal.render.RenderEngine;
import com.mapfinal.render.Renderer;
import com.mapfinal.resource.FeatureCollection;

public class FeatureDispatcher extends Dispatcher {
	// 要素
	//private ConcurrentHashMap<Long, Feature> features = new ConcurrentHashMap<Long, Feature>();
	private Event event;
	private RenderEngine engine;
	//private Envelope sceneEnvelope = null;
	//private List<Field> fields;
	//private String geometryType;
	private int size = 0;
	
	public FeatureDispatcher(SpatialIndexer indexer, FeatureCollection resource) {
		super(indexer, resource);
	}
	
	public FeatureCollection getFeatureCollection() {
		return (FeatureCollection) getResource();
	} 
	
	@Override
	public void resultAction(SpatialIndexObject sio) {
		// TODO Auto-generated method stub
		MapContext context = event.get("map");
		boolean bintersects = context.getSceneEnvelope().intersects(sio.getEnvelope());
		if(!bintersects) return;
		size++;
//		if(context.getZoom() > 13) {
//			System.out.println("id: " + sio.getId() + ", env: " + sio.getEnvelope().toString());
//		}
		//System.out.println("[resultAction] id: " + id);
		Feature feature = (Feature) getFeatureCollection().read(sio);
		if(feature!=null) {
			//System.out.println("[resultAction] render id: " + id);
			Renderer renderer = event.get("renderer");
			//QueryParameter query = event.get("queryParameter");
			engine.renderFeature(renderer, context, feature);
		}
		
	}
	
	public void render(Event event, RenderEngine engine, Renderer renderer) {
		this.event = event;
		this.engine = engine;
		MapContext context = event.get("map");
		/*
		if(context.getSceneEnvelope().equals(sceneEnvelope) && features!=null) {
			//System.out.println("envelope: " + sceneEnvelope.toString());
			for (Feature feature : features.values()) {
				feature.onRender(event, engine);
			}
		} else {
			sceneEnvelope = new Envelope(context.getSceneEnvelope());
			//System.out.println("newEnv: " + sceneEnvelope.toString());
			query(sceneEnvelope, this);
		}*/
//		if(!context.getSceneEnvelope().equals(sceneEnvelope)) {
//			sceneEnvelope = new Envelope(context.getSceneEnvelope());
//			System.out.println("newEnv: " + sceneEnvelope.toString());
//			query(sceneEnvelope, this);
//		}
		size = 0;
		query(event, context.getSceneEnvelope(), this);
		//System.out.println("[FeatureDispatcher] size: " + size + ", scene: " + context.getSceneEnvelope().toString());
		//long start = System.currentTimeMillis();
		//System.out.println("query times: " + (System.currentTimeMillis() - start));
//		if(features!=null) {
//			//System.out.println("featues size: " + features.size());
//			for (Feature feature : features.values()) {
//				feature.onRender(event, engine);
//			}
//		}
		//System.out.println("draw times: " + (System.currentTimeMillis() - start));
		/*
		List<SpatialIndexObject> sios = query(sceneEnvelope);
		for (SpatialIndexObject sio : sios) {
			System.out.println("query sio: " + sio.getId() + "," + sio.getGeometryType());
		}*/
	}

	/*
	public Feature loadFeature(SpatialIndexObject sio) {
		Feature feature =  null;
		if(getResource()!=null && getResource().getReader()!=null) {
			GeoElement element = getResource().getReader().read(sio);
			if(element instanceof Feature) {
				feature = (Feature) element;
			}
		}
		return feature;
	}
	
	public Map<Long, Feature> getFeatures() {
		return features;
	}
	
	public long addFeature(Feature feature) {
		// TODO Auto-generated method stub
		features.put(Long.valueOf(feature.getId()), feature);
		return feature.getId();
	}

	public long[] addFeatures(List<Feature> features) {
		// TODO Auto-generated method stub
		if(features.size() < 1) return null;
		long[] fids = new long[features.size()];
		for (int i=0; i<features.size(); i++) {
			long id = addFeature(features.get(i));
			fids[i] = id;
		}
		return fids;
	}

	public void deleteFeature(long featureId) {
		// TODO Auto-generated method stub
		if (this.features != null) {
			Feature feature = getFeature(featureId);
			if (feature != null)
				feature.destroy();
			this.features.remove(Long.valueOf(featureId));
		}
	}

	public void deleteFeatures(long[] featureIds) {
		// TODO Auto-generated method stub
		if(this.features==null) return;
		for (long id : featureIds) {
			deleteFeature(id);
		}
	}

	public Feature getFeature(long id) {
		// TODO Auto-generated method stub
		return features.get(Long.valueOf(id));
	}

	public FeatureResult getFeatures(long[] ids) {
		// TODO Auto-generated method stub
		if(ids.length < 1) return null;
		FeatureResult featureResult = new FeatureResult(fields);
		for (long id : ids) {
			Feature feature = getFeature(id);
			featureResult.addFeature(feature);
		}
		return featureResult;
	}

	public Field getField(String fieldName) {
		// TODO Auto-generated method stub
		if(fields==null || StringKit.isBlank(fieldName)) {
			return null;
		}
		for (Field field : fields) {
			if(fieldName.equals(field.getName())) {
				return field;
			}
		}
		return null;
	}

	public List<Field> getFields() {
		// TODO Auto-generated method stub
		return fields;
	}

	public String getGeometryType() {
		// TODO Auto-generated method stub
		return geometryType;
	}

	public FeatureResult queryFeatures(QueryParameter query) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateFeature(long featureId, Feature feature) {
		// TODO Auto-generated method stub
		addFeature(feature);
	}

	public void updateFeatures(long[] featureIds, List<Feature> features) {
		// TODO Auto-generated method stub
		for (Feature feature : features) {
			addFeature(feature);
		}
	}

	public void close() {
		// TODO Auto-generated method stub
		for (Feature f : features.values()) {
			f.destroy();
		}
		features.clear();
		if(fields!=null) {
			fields.clear();
		}
		fields = null;
	}
	*/

	public Event getEvent() {
		return event;
	}

	public RenderEngine getEngine() {
		return engine;
	}
}
