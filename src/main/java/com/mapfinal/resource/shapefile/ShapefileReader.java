package com.mapfinal.resource.shapefile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.mapfinal.converter.CRS;
import com.mapfinal.dispatcher.Dispatcher;
import com.mapfinal.dispatcher.SpatialIndexObject;
import com.mapfinal.map.GeoElement;
import com.mapfinal.resource.ResourceReader;

public class ShapefileReader implements ResourceReader {

	private ShapefileRandomAccess context;
	private ShapefileResourceObject resource;
	
	public ShapefileReader(ShapefileResourceObject resource) {
		// TODO Auto-generated constructor stub
		this.context = new ShapefileRandomAccess(resource.getUrl());
		this.resource = resource;
	}
	
	@Override
	public Dispatcher connection() {
		// TODO Auto-generated method stub
		try {
			context.readInit();
			resource.setFields(context.getFields());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return context.connection(resource);
	}
	

	@Override
	public Map<String, GeoElement> read(List<SpatialIndexObject> objs) {
		// TODO Auto-generated method stub
		return context.read(objs);
	}

	@Override
	public GeoElement read(SpatialIndexObject obj) {
		// TODO Auto-generated method stub
		return context.read(obj);
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		context.close();
	}

	@Override
	public CRS getCRS() {
		// TODO Auto-generated method stub
		return context.getCRS();
	}

	public ShapefileRandomAccess getContext() {
		return context;
	}

	public void setContext(ShapefileRandomAccess context) {
		this.context = context;
	}

	public ShapefileResourceObject getResource() {
		return resource;
	}

	public void setResource(ShapefileResourceObject resource) {
		this.resource = resource;
	}
	
}
