package com.mapfinal.resource.vectortile;

import com.mapfinal.dispatcher.Dispatcher;
import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.event.Event;
import com.mapfinal.resource.Resource;
import com.mapfinal.resource.tile.TileData;
import com.mapfinal.resource.tile.TileFeature;
import com.mapfinal.resource.tile.TileResourceDispatcher;

public class VectorTileResource extends TileResourceDispatcher<TileFeature> implements Resource<TileData> {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Dispatcher connection(Event event) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TileFeature read(SpatialIndexObject sio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getMemorySize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void prepare(Event event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TileData read(Event event) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writer(Event event, TileData data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTmsType() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setCurrentTileNumberOnScreen(int numTile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEvent(Event event) {
		// TODO Auto-generated method stub
		
	}

}
