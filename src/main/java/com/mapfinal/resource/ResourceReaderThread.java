package com.mapfinal.resource;

import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.event.Callback;
import com.mapfinal.event.Event;
import com.mapfinal.map.GeoElement;

public class ResourceReaderThread implements Runnable {

	private Callback callback;
	private ResourceReader reader;
	private SpatialIndexObject sio;
	
	public ResourceReaderThread(ResourceReader reader, SpatialIndexObject sio, Callback callback) {
		// TODO Auto-generated constructor stub
		this.reader = reader;
		this.sio = sio;
		this.callback = callback;
	}
	
	@Override
	public void run() {
		if(reader!=null) {
			GeoElement e = reader.read(sio);
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

	public ResourceReader getReader() {
		return reader;
	}

	public void setReader(ResourceReader reader) {
		this.reader = reader;
	}

	public SpatialIndexObject getSio() {
		return sio;
	}

	public void setSio(SpatialIndexObject sio) {
		this.sio = sio;
	}
	
	
}
