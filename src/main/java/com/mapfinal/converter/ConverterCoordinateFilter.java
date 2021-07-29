package com.mapfinal.converter;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateFilter;

public class ConverterCoordinateFilter implements CoordinateFilter {
	
	private Converter converter;
	
	public ConverterCoordinateFilter(Converter converter) {
		// TODO Auto-generated constructor stub
		this.converter = converter;
	}

	@Override
	public void filter(Coordinate coord) {
		// TODO Auto-generated method stub
		if(converter!=null) {
			coord = converter.transform(coord);
		}
	}

}
