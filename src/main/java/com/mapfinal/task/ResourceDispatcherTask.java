package com.mapfinal.task;

import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.event.Callback;
import com.mapfinal.event.Event;
import com.mapfinal.map.Graphic;
import com.mapfinal.resource.ResourceDispatcher;

public class ResourceDispatcherTask implements Runnable {

	private Callback callback;
	private ResourceDispatcher reader;
	private SpatialIndexObject sio;
	
	public ResourceDispatcherTask(ResourceDispatcher reader, SpatialIndexObject sio, Callback callback) {
		// TODO Auto-generated constructor stub
		this.reader = reader;
		this.sio = sio;
		this.callback = callback;
	}
	
	@Override
	public void run() {
		if(reader!=null) {
			Graphic e = reader.read(sio);
			if(callback!=null) {
				Event event = new Event("resourceReader", "feature", e);
				callback.execute(event);
			}
		}
	}

	public Callback getCallback() {
		return callback;
	}

	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	public ResourceDispatcher getReader() {
		return reader;
	}

	public void setReader(ResourceDispatcher reader) {
		this.reader = reader;
	}

	public SpatialIndexObject getSio() {
		return sio;
	}

	public void setSio(SpatialIndexObject sio) {
		this.sio = sio;
	}
	
	
}
