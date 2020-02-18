package com.mapfinal.resource;

import java.util.List;

import com.mapfinal.map.GeoElement;

public interface ResourceWriter {

	void open();
	
	void write(List<GeoElement> elements);
	
	void write(GeoElement element);
	
	void close();
}
