package com.mapfinal.dispatcher.indexer;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.index.ItemVisitor;

import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.dispatcher.SpatialIndexer;
import com.mapfinal.event.Event;

public class SimpleIndexer implements SpatialIndexer {
	
	List<SpatialIndexObject> sios;
	
	public SimpleIndexer() {
		// TODO Auto-generated constructor stub
		sios = new ArrayList<SpatialIndexObject>();
	}
	
	public void insert(Envelope env, SpatialIndexObject sio) {
		if(sio.getEnvelope()==null) {
			sio.setEnvelope(env);
		}
		sios.add(sio);
	}

	@Override
	public List<SpatialIndexObject> query(Event event, Envelope env) {
		// TODO Auto-generated method stub
		List<SpatialIndexObject> nsios = new ArrayList<SpatialIndexObject>();
		for (SpatialIndexObject sio : sios) {
			if(sio.getEnvelope().intersects(env)) {
				nsios.add(sio);
			}
		}
		return nsios;
	}

	@Override
	public void query(Event event, Envelope env, ItemVisitor visitor) {
		// TODO Auto-generated method stub
		for (SpatialIndexObject sio : sios) {
			if(sio.getEnvelope().intersects(env)) {
				visitor.visitItem(sio);
			}
		}
	}

}
