package com.mapfinal.geometry;

import java.util.Collection;

import org.locationtech.jts.geom.Coordinate;

public class GeoMultiLineString implements Geom {

	protected GeomList<GeoLineString> lines;
//	protected final Object lock = new Object();
	
	public GeoMultiLineString(Collection<? extends GeoLineString> gls) {
		lines = new GeomList<GeoLineString>(gls);
	}
	
	public GeoMultiLineString(GeoLineString[] gls) {
		lines = new GeomList<GeoLineString>(gls);
	}
	
	public GeoMultiLineString(GeoLineString gls) {
		lines = new GeomList<GeoLineString>();
		lines.add(gls);
	}
	
	public void addPoint(int line_index, Coordinate coordinate) {
		if(lines!=null) {
			if(lines.get(line_index)!=null) {
				lines.get(line_index).add(coordinate);
			}
		}
	}
	
	public void addPoint(int line_index, Coordinate coordinate, boolean allowRepeated) {
		if(lines!=null) {
			if(lines.get(line_index)!=null) {
				lines.get(line_index).add(coordinate, allowRepeated);
			}
		}
	}
	
	public void addPoint(int line_index, int pt_index, Coordinate coordinate) {
		if(lines!=null) {
			if(lines.get(line_index)!=null) {
				lines.get(line_index).add(pt_index, coordinate);
			}
		}
	}
	
	public void addPoint(int line_index, int pt_index, Coordinate coordinate, boolean allowRepeated) {
		if(lines!=null) {
			if(lines.get(line_index)!=null) {
				lines.get(line_index).add(pt_index, coordinate, allowRepeated);
			}
		}
	}
	
	public void add(Coordinate[] coordinates, boolean allowRepeated) {
//		synchronized (lock) {
			GeoLineString gls = new GeoLineString(coordinates, allowRepeated);
			if(lines!=null) {
				lines.add(gls);
			}
//		}
	}
	
	@Override
	public GeomType getGeomType() {
		// TODO Auto-generated method stub
		return GeomType.MULTILINESTRING;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return lines.getSize();
	}

	@Override
	public int getCoordinateSize() {
		// TODO Auto-generated method stub
		return lines.getCoordinateSize();
	}

	@Override
	public GeoPoint getCentroid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeoPoint getInteriorPoint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return lines.isEmpty();
	}
}
