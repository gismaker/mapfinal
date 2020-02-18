package com.mapfinal.dispatcher;

import java.util.List;

import com.mapfinal.event.Event;
import com.mapfinal.resource.ResourceObject;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.index.ItemVisitor;

/**
 * 调度器：一个调度器对应多个layer，一个资源可对应多个调度器，
 * @author yangyong
 *
 */
public abstract class Dispatcher implements ItemVisitor {

	private SpatialIndexer indexer;
	private ResourceObject resource;
	
	public Dispatcher(SpatialIndexer indexer, ResourceObject resource) {
		this.setIndexer(indexer);
		this.setResource(resource);
	}
	
	@Override
	public void visitItem(Object paramObject) {
		// TODO Auto-generated method stub
		if (paramObject instanceof SpatialIndexObject) {
			SpatialIndexObject sio = (SpatialIndexObject) paramObject;
			resultAction(sio);
		}
	}
	
	public abstract void resultAction(SpatialIndexObject sio);
	public abstract void close();
	
	//查询
	public List<SpatialIndexObject> query(Event event, Envelope env) {
		return indexer!=null ? indexer.query(event, env) : null;
	}
		//查询
	public void query(Event event, Envelope env, Dispatcher visitor) {
		visitor = visitor==null ? this : visitor;
		indexer.query(event, env, visitor);
	}
	
	public SpatialIndexer getIndexer() {
		return indexer;
	}

	public void setIndexer(SpatialIndexer indexer) {
		this.indexer = indexer;
	}

	public ResourceObject getResource() {
		return resource;
	}

	public void setResource(ResourceObject resource) {
		this.resource = resource;
	}

}
