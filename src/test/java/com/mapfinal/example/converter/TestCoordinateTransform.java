package com.mapfinal.example.converter;

import org.locationtech.jts.geom.Coordinate;
import org.osgeo.proj4j.CRSFactory;
import org.osgeo.proj4j.CoordinateReferenceSystem;
import org.osgeo.proj4j.CoordinateTransform;
import org.osgeo.proj4j.CoordinateTransformFactory;
import org.osgeo.proj4j.ProjCoordinate;

import com.mapfinal.converter.CRS;
import com.mapfinal.converter.Converter;
import com.mapfinal.converter.ConverterManager;

public class TestCoordinateTransform {

	public static void main(String[] args) {
		//proj4j
		CRSFactory targetFactory = new CRSFactory();
		CoordinateReferenceSystem sourceCRS = targetFactory.createFromName("epsg:3857");
		CoordinateReferenceSystem targetCRS = targetFactory.createFromName("epsg:4326");
		
		CoordinateTransformFactory ctf = new CoordinateTransformFactory();
		CoordinateTransform transform = ctf.createTransform(sourceCRS, targetCRS);

		ProjCoordinate pc1 = new ProjCoordinate(242075.00535055372, 750123.32090043, 0);
	 
		ProjCoordinate res = new ProjCoordinate();
		res = transform.transform(pc1, res);
		System.out.println("lcc: " + pc1.toString() + " to " + res.toString());
		//Coordinate result = new Coordinate(res.x, res.y, res.z);
		
		//mapfinal
		
		Coordinate p = new Coordinate(242075.00535055372, 750123.32090043, 0);
		
		CRS source = ConverterManager.me().getCRS("EPSG:3857");
		CRS target = ConverterManager.me().getCRS("EPSG:4326");
		Converter converter = ConverterManager.me().getFactory().build(source, target);
		Coordinate destCoordinate = converter.transform(p);
		System.out.println("ddd: " + destCoordinate.toString());
		
		Coordinate c = ConverterManager.use("EPSG:3857", "EPSG:4326").transform(p);
		System.out.println("ccc: " + c.toString());
		
	}
	
//	private Coordinate transform(Coordinate coordinate, Coordinate destCoordinate, CoordinateReferenceSystem sourceCRS, CoordinateReferenceSystem targetCRS) {
//		   com.mapfinal.converter.CRS source = ConverterManager.me().getFactory().parseWKT("sourceCRS", sourceCRS.toWKT());
//		   com.mapfinal.converter.CRS target = ConverterManager.me().getCRS("EPSG:4326");
//		   Converter converter = ConverterManager.me().getFactory().build(source, target);
//		   destCoordinate = converter.transform(coordinate);
//		   return destCoordinate;
//		}
}
