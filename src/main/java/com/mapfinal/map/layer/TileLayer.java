package com.mapfinal.map.layer;

import com.mapfinal.converter.SpatialReference;
import com.mapfinal.dispatcher.TileDispatcher;
import com.mapfinal.event.Event;
import com.mapfinal.map.MapView;
import com.mapfinal.map.AbstractLayer;
import com.mapfinal.map.MapContext;
import com.mapfinal.render.RenderEngine;
import com.mapfinal.resource.Resource;
import com.mapfinal.resource.tile.TileResource;

import org.locationtech.jts.geom.Envelope;

public class TileLayer extends AbstractLayer {

	// 调度器
	private TileDispatcher dispatcher;
	// 资源
	private TileResource resource;

	public TileLayer(String name, String url, Resource.FileType fileType) {
		// TODO Auto-generated constructor stub
		resource = new TileResource(name, url, fileType);
		setName(resource.getName());
		setTitle(resource.getName());
		TileDispatcher dispatcher = (TileDispatcher) resource.connection();
		System.out.println("FeatureDispatcher: " + resource.getName());
		setDispatcher(dispatcher);
		setSpatialReference(SpatialReference.mercator());
	}
	
	public TileLayer(TileResource resource) {
		this.resource = resource;
		setName(resource.getName());
		setTitle(resource.getName());
		TileDispatcher dispatcher = (TileDispatcher) resource.connection();
		System.out.println("FeatureDispatcher: " + resource.getName());
		setDispatcher(dispatcher);
		setSpatialReference(SpatialReference.mercator());
	}
	
	public void addTo(MapView mapNode) {
		mapNode.add(this);
	}

	@Override
	public void draw(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		if(!isVisible()) return;
		MapContext context = event.get("map");
		int zoom = (int) context.getZoom();
		if(zoom < getMinZoom() || zoom > getMaxZoom()) return;
		//System.out.println("feature layer render.");
		if(dispatcher!=null) {
			event.set("tile_renderCacheLayer", true);
			dispatcher.draw(event, engine, getRenderer());
			event.set("tile_renderCacheLayer", false);
		}
	}

	@Override
	public boolean handleEvent(Event event) {
		// TODO Auto-generated method stub
		return !super.handleEvent(event) && dispatcher!=null ? dispatcher.handleEvent(event) : false;
	}

	public TileDispatcher getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(TileDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public TileResource getResource() {
		return resource;
	}

	public void setResource(TileResource resource) {
		this.resource = resource;
	}
	
	@Override
	public Envelope getEnvelope() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
