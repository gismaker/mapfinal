package com.mapfinal.converter.proj4;

import org.osgeo.proj4j.CoordinateReferenceSystem;
import org.osgeo.proj4j.CoordinateTransform;
import org.osgeo.proj4j.ProjCoordinate;

import com.mapfinal.converter.CRS;
import com.mapfinal.converter.Converter;
import org.locationtech.jts.geom.Coordinate;

public class Proj4Converter implements Converter {
	
	private CoordinateTransform transform;
	
	public CoordinateTransform getTransform() {
		return transform;
	}

	public Proj4Converter(CoordinateTransform transform) {
		// TODO Auto-generated constructor stub
		this.transform = transform;
	}

	@Override
	public Coordinate transform(Coordinate src, Coordinate tgt) {
		// TODO Auto-generated method stub
		ProjCoordinate paramProjCoordinate1 = new ProjCoordinate(src.x, src.y, src.getZ());
		ProjCoordinate paramProjCoordinate2 = new ProjCoordinate(tgt.x, tgt.y, tgt.getZ());
		ProjCoordinate res = transform.transform(paramProjCoordinate1, paramProjCoordinate2);
		tgt.x = res.x;
		tgt.y = res.y;
		tgt.setZ(res.z);
		return tgt;
	}

	@Override
	public Coordinate transform(Coordinate coordinate) {
		// TODO Auto-generated method stub
		ProjCoordinate paramProjCoordinate = new ProjCoordinate(coordinate.x, coordinate.y, coordinate.z);
		ProjCoordinate res = new ProjCoordinate();
		res = transform.transform(paramProjCoordinate, res);
		return new Coordinate(res.x, res.y, res.z);
	}

	@Override
	public Coordinate transform(double x, double y, double z) {
		// TODO Auto-generated method stub
		ProjCoordinate paramProjCoordinate = new ProjCoordinate(x, y, z);
		ProjCoordinate res = new ProjCoordinate();
		res = transform.transform(paramProjCoordinate, res);
		return new Coordinate(res.x, res.y, res.z);
	}

	@Override
	public CRS getSourceCRS() {
		// TODO Auto-generated method stub
		return transform!=null ? toCRS(transform.getSourceCRS()) : null;
	}

	@Override
	public CRS getTargetCRS() {
		// TODO Auto-generated method stub
		return transform!=null ? toCRS(transform.getTargetCRS()) : null;
	}
	
	private CRS toCRS(CoordinateReferenceSystem crs) {
		return new CRS(crs.getName(), crs.getParameters(), CRS.PROJ4);
	}
}
