package com.mapfinal.geometry;

import java.util.Collection;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;

public class GeoMultiLineString implements Geom {

	protected GeomList<GeoLineString> lines;
//	protected final Object lock = new Object();
	
	public GeoMultiLineString(Collection<? extends GeoLineString> gls) {
		lines = new GeomList<GeoLineString>(gls);
	}
	
	public GeoMultiLineString(GeoLineString[] gls) {
		lines = new GeomList<GeoLineString>(gls);
	}
	
	public GeoMultiLineString(GeoLineString line) {
		lines = new GeomList<GeoLineString>();
		lines.add(line);
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
	public Envelope getEnvelope() {
		// TODO Auto-generated method stub
		if (isEmpty()) {
			return new Envelope();
		}
		return lines.getEnvelope();
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

	@Override
	public Geometry toGeometry() {
		// TODO Auto-generated method stub
		LineString[] lineArray = new LineString[lines.geoms.size()];
		for (int i = 0; i < lines.geoms.size(); i++) {
			GeoLineString gls = lines.geoms.get(i);
			lineArray[i] = GeoKit.createLine(gls.getCoordinates());
		}
		return GeoKit.createMultiLine(lineArray);
	}
}
