package com.mapfinal.resource.shapefile;

import com.mapfinal.map.layer.FeatureLayer;

public class ShapefileLayer extends FeatureLayer {
	
	public ShapefileLayer(String filepath) {
		// TODO Auto-generated constructor stub
		super(new Shapefile(filepath));
	}

	public ShapefileLayer(Shapefile resource) {
		super(resource);
		// TODO Auto-generated constructor stub
	}
}
