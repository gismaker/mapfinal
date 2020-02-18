package com.mapfinal.resource.shapefile;

import com.mapfinal.map.layer.FeatureLayer;
import com.mapfinal.resource.FeatureResourceObject;

public class ShapefileLayer extends FeatureLayer {
	
	public ShapefileLayer(String filepath) {
		// TODO Auto-generated constructor stub
		super(new ShapefileResourceObject(filepath));
	}

	public ShapefileLayer(FeatureResourceObject resource) {
		super(resource);
		// TODO Auto-generated constructor stub
	}
}
