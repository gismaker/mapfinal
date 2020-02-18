package com.mapfinal.map.layer;

import com.mapfinal.dispatcher.TileDispatcher;
import com.mapfinal.event.Event;
import com.mapfinal.map.MapView;
import com.mapfinal.map.MapContext;
import com.mapfinal.render.RenderEngine;
import com.mapfinal.resource.Resource;
import com.mapfinal.resource.ResourceObject;
import com.mapfinal.resource.tile.TileResourceObject;
import org.locationtech.jts.geom.Envelope;

public class TileLayer extends AbstractLayer {

	// 调度器
	private TileDispatcher dispatcher;
	// 资源
	private ResourceObject resource;

	public TileLayer(String name, String url, Resource.FileType fileType) {
		// TODO Auto-generated constructor stub
		resource = new TileResourceObject(name, url, fileType);
		setName(resource.getName());
		setTitle(resource.getName());
		TileDispatcher dispatcher = (TileDispatcher) resource.getReader().connection();
		System.out.println("FeatureDispatcher: " + dispatcher.getResource().getName());
		setDispatcher(dispatcher);
		setSpatialReference(resource.getSpatialReference());
	}
	
	public TileLayer(ResourceObject resource) {
		this.resource = resource;
		setName(resource.getName());
		setTitle(resource.getName());
		TileDispatcher dispatcher = (TileDispatcher) resource.getReader().connection();
		System.out.println("FeatureDispatcher: " + dispatcher.getResource().getName());
		setDispatcher(dispatcher);
		setSpatialReference(resource.getSpatialReference());
	}
	
	public void addTo(MapView mapNode) {
		mapNode.add(this);
	}

	@Override
	public void onRender(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		if(!isVisible()) return;
		MapContext context = event.get("map");
		int zoom = (int) context.getZoom();
		if(zoom < getMinZoom() || zoom > getMaxZoom()) return;
		//System.out.println("feature layer render.");
		if(dispatcher!=null) {
			event.set("layer", this);
			dispatcher.onRender(event, engine, getRenderer());
		}
	}

	@Override
	public void onEvent(Event event) {
		// TODO Auto-generated method stub
		if(dispatcher!=null) {
			dispatcher.onEvent(event);
		}
	}

	public TileDispatcher getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(TileDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public ResourceObject getResource() {
		return resource;
	}

	public void setResource(ResourceObject resource) {
		this.resource = resource;
	}
	
	@Override
	public Envelope getEnvelope() {
		// TODO Auto-generated method stub
		return resource != null ? resource.getEnvelope() : null;
	}
	
	@Override
	public void setMinZoom(int minZoom) {
		// TODO Auto-generated method stub
		super.setMinZoom(minZoom);
		this.dispatcher.setMinZoom(minZoom);
	}
	
	@Override
	public void setMaxZoom(int maxZoom) {
		// TODO Auto-generated method stub
		super.setMaxZoom(maxZoom);
		this.dispatcher.setMaxZoom(maxZoom);
	}
}
