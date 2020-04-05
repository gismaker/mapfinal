package com.mapfinal.resource.shapefile;

import com.mapfinal.map.layer.FeatureLayer;

public class ShapefileLayer extends FeatureLayer {
	
	public ShapefileLayer(String filepath) {
		// TODO Auto-generated constructor stub
		super(ShapefileManager.me().create(filepath));
	}

	public ShapefileLayer(Shapefile resource) {
		super(resource);
		// TODO Auto-generated constructor stub
	}
}
