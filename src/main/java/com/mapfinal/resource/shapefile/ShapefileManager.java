package com.mapfinal.resource.shapefile;

import com.mapfinal.resource.FeatureResourceManager;

public class ShapefileManager extends FeatureResourceManager<Long, ShapefileFeature, Shapefile> {

	private ShapefileReaderFactory readerFactory;
	
	public ShapefileManager() {
		// TODO Auto-generated constructor stub
		this.setReaderFactory(new DefaultShapefileReaderFactory());
	}

	private static final ShapefileManager me = new ShapefileManager();
	
	public static ShapefileManager me() {
		return me;
	}
	
	@Override
	public String getResourceType() {
		// TODO Auto-generated method stub
		return "shp";
	}

	public ShapefileReaderFactory getReaderFactory() {
		return readerFactory;
	}

	public void setReaderFactory(ShapefileReaderFactory readerFactory) {
		this.readerFactory = readerFactory;
	}

}
