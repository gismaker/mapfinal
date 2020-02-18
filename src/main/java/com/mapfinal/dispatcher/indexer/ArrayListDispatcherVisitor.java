package com.mapfinal.dispatcher.indexer;

import java.util.ArrayList;
import java.util.List;

import com.mapfinal.dispatcher.Dispatcher;
import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.dispatcher.SpatialIndexer;
import com.mapfinal.resource.ResourceObject;

public class ArrayListDispatcherVisitor extends Dispatcher {

	private List<SpatialIndexObject> items;
	
	public ArrayListDispatcherVisitor() {
		super(null, null);
		// TODO Auto-generated constructor stub
	}
	
	public ArrayListDispatcherVisitor(SpatialIndexer indexer, ResourceObject resource) {
		super(indexer, resource);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void resultAction(SpatialIndexObject sio) {
		// TODO Auto-generated method stub
		if(items==null) {
			items = new ArrayList<SpatialIndexObject>();
		}
		items.add(sio);
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		if(items!=null) items.clear();
		items = null;
	}

	public List<SpatialIndexObject> getItems() {
		return items;
	}
}
