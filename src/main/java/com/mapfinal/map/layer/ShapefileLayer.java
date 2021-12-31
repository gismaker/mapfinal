package com.mapfinal.map.layer;

import com.mapfinal.resource.shapefile.Shapefile;
import com.mapfinal.resource.shapefile.ShapefileManager;

public class ShapefileLayer extends FeatureLayer {
	
	public ShapefileLayer(String filepath) {
		// TODO Auto-generated constructor stub
		super(ShapefileManager.me().create(filepath));
	}

	public ShapefileLayer(Shapefile resource) {
		super(resource);
		// TODO Auto-generated constructor stub
	}
	
	public Shapefile getShapefile() {
		return (Shapefile) getResource();
	}
}
