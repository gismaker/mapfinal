package com.mapfinal.dispatcher.indexer;

import java.util.ArrayList;
import java.util.List;

import com.mapfinal.dispatcher.Dispatcher;
import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.dispatcher.SpatialIndexer;
import com.mapfinal.event.Event;
import org.locationtech.jts.geom.Envelope;

/**
 * 无索引
 * @author yangyong
 *
 */
public class NoneIndexer implements SpatialIndexer {

	private SpatialIndexObject spatialIndexObject;
	
	public NoneIndexer(SpatialIndexObject spatialIndexObject) {
		// TODO Auto-generated constructor stub
		this.spatialIndexObject = spatialIndexObject;
	}
	
	@Override
	public List<SpatialIndexObject> query(Event event, Envelope env) {
		// TODO Auto-generated method stub
		List<SpatialIndexObject> res = new ArrayList<>();
		res.add(spatialIndexObject);
		return res;
	}

	@Override
	public void query(Event event, Envelope env, Dispatcher visitor) {
		// TODO Auto-generated method stub
		visitor.resultAction(spatialIndexObject);
	}

	public SpatialIndexObject getSpatialIndexObject() {
		return spatialIndexObject;
	}

	public void setSpatialIndexObject(SpatialIndexObject spatialIndexObject) {
		this.spatialIndexObject = spatialIndexObject;
	}
}
