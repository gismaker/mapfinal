package com.mapfinal.map.layer;

import com.mapfinal.converter.scene.ScenePoint;
import com.mapfinal.dispatcher.FeatureDispatcher;
import com.mapfinal.event.Event;
import com.mapfinal.geometry.Latlng;
import com.mapfinal.geometry.ScreenPoint;
import com.mapfinal.map.AbstractLayer;
import com.mapfinal.map.Feature;
import com.mapfinal.map.MapContext;
import com.mapfinal.render.RenderEngine;
import com.mapfinal.render.Renderer;
import com.mapfinal.render.SimpleRenderer;
import com.mapfinal.render.pick.PickManager;
import com.mapfinal.render.style.FillSymbol;
import com.mapfinal.resource.FeatureResource;

import org.locationtech.jts.geom.Envelope;

/**
 * 参考ArcGIS FeatureLayer结合一下，特别是FeatureTable
 * https://www.cnblogs.com/arxive/p/7751880.html
 * 
 * @author yangyong
 *
 */
public class FeatureLayer extends AbstractLayer {
	/**
	 * 资源
	 */
	private FeatureResource resource;
	/**
	 * 默认通过FeatureDispatcher调度器实现
	 */
	private FeatureDispatcher dispatcher;

	public FeatureLayer(FeatureResource resource) {
		this.resource = resource;
		setName(resource.getName());
		setTitle(resource.getName());
		dispatcher = (FeatureDispatcher) resource.connection();
		System.out.println("FeatureDispatcher: " + resource.getName());
		setSpatialReference(resource.getSpatialReference());
	}

	public FeatureLayer(FeatureResource resource, Renderer renderer) {
		this(resource);
		setRenderer(renderer);
	}
	/*
	 * public void addFeature(Feature feature) { if(resource!=null) {
	 * resource.addFeature(feature); } }
	 * 
	 * public void deleteFeature(Feature feature) { if(resource!=null) {
	 * resource.deleteFeature(feature.getId()); } }
	 * 
	 * public void deleteFeature(Long featureId) { if(resource!=null) {
	 * resource.deleteFeature(featureId); } }
	 */

	@Override
	public boolean isDrawable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void draw(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		if (!isDrawable())
			return;
		if (!isVisible())
			return;
		MapContext context = event.get("map");
		int zoom = (int) context.getZoom();
		if (zoom < getMinZoom() || zoom > getMaxZoom())
			return;
		// System.out.println("feature layer render.");
		if (event.isRender()) {
			render(event, engine);
		} else if ("pick".equals(event.getAction())) {
			pick(event, engine);
		}
	}

	private void render(Event event, RenderEngine engine) {
		if (dispatcher != null) {
			event.set("renderer", getRenderer());
			dispatcher.draw(event, engine, getRenderer());
		}
	}

	@Override
	public void pick(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		if (dispatcher != null) {
			//MapContext context = event.get("map");
			//System.out.println("PolygonLayer: context: " + context.isMainThread() + "," + context.getCenter());
			int color = PickManager.me().getRenderColor(getName());
			//System.out.println("PolygonLayer: color: " + color);
			Renderer renderer = getRenderer();
			if(renderer==null) {
				renderer = new SimpleRenderer(color, true);
			} else {
				renderer.setPickMode(color, true);
			}
			event.set("renderer", renderer);
			event.set("layerName", getName());
			dispatcher.draw(event, engine, getRenderer());
			renderer.setPickMode(color, false);
		}
	}

	@Override
	public boolean handleEvent(Event event) {
		// TODO Auto-generated method stub
		if (dispatcher != null) {
			if(event.isAction("picked")) {
				String idName = event.get("picked_name");
				Feature feature = dispatcher.getPickFeature(idName);
				if(feature!=null) {
					sendEvent(getEventAction("click"), event.set("picked_object", feature));
					return true;
				}
				if(getName().equals(idName)) {
					sendEvent(getEventAction("click"), event.set("picked_object", this));
					return true;
				}
			}
			boolean flag = dispatcher.handleEvent(event);
			if (flag) {
				sendEvent("featureSelected", event);
			}
		}
		return false;
	}

	@Override
	public Envelope getEnvelope() {
		// TODO Auto-generated method stub
		return resource.getEnvelope();
	}

	public FeatureResource getResource() {
		return resource;
	}

	public void setResource(FeatureResource resource) {
		this.resource = resource;
	}
}
