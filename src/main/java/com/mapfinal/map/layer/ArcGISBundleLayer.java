package com.mapfinal.map.layer;

import com.mapfinal.converter.SpatialReference;
import com.mapfinal.dispatcher.TileDispatcher;
import com.mapfinal.event.Event;
import com.mapfinal.map.MapView;
import com.mapfinal.map.AbstractLayer;
import com.mapfinal.map.MapContext;
import com.mapfinal.render.RenderEngine;
import com.mapfinal.resource.bundle.BundleResource;
import org.locationtech.jts.geom.Envelope;

public class ArcGISBundleLayer extends AbstractLayer {

	// 调度器
	private TileDispatcher dispatcher;
	// 资源
	private BundleResource resource;

	public ArcGISBundleLayer(String name, String url) {
		// TODO Auto-generated constructor stub
		resource = new BundleResource(name, url);
		setName(resource.getName());
		setTitle(resource.getName());
		TileDispatcher dispatcher = (TileDispatcher) resource.connection();
		setDispatcher(dispatcher);
		setSpatialReference(SpatialReference.mercator());
	}
	
	public ArcGISBundleLayer(BundleResource resource) {
		this.resource = resource;
		setName(resource.getName());
		setTitle(resource.getName());
		TileDispatcher dispatcher = (TileDispatcher) resource.connection();
		setDispatcher(dispatcher);
		setSpatialReference(SpatialReference.mercator());
	}
	
	public void addTo(MapView mapNode) {
		mapNode.add(this);
	}

	@Override
	public void draw(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		if(!isDrawable()) return;
		if(!isVisible()) return;
		if(!event.isRender()) return;
		MapContext context = event.get("map");
		int zoom = (int) context.getZoom();
		if(zoom < getMinZoom() || zoom > getMaxZoom()) return;
		//System.out.println("feature layer render.");
		if(dispatcher!=null) {
			dispatcher.draw(event, engine, getRenderer());
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

	public BundleResource getResource() {
		return resource;
	}

	public void setResource(BundleResource resource) {
		this.resource = resource;
	}
	
	@Override
	public Envelope getEnvelope() {
		// TODO Auto-generated method stub
		return null;
	}
}
