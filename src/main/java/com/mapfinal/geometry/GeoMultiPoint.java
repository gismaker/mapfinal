package com.mapfinal.geometry;

import java.util.Collection;

import org.locationtech.jts.geom.Coordinate;

public class GeoMultiPoint implements Geom {

	protected GeomList<GeoPoint> points;
//	protected final Object lock = new Object();
	
	public GeoMultiPoint(Collection<? extends Coordinate> coords) {
		points = new GeomList<GeoPoint>();
		for (Coordinate coordinate : coords) {
			add(coordinate);
		}
	}
	
	public GeoMultiPoint(Coordinate[] coords) {
		points = new GeomList<GeoPoint>();
		for (Coordinate coordinate : coords) {
			add(coordinate);
		}
	}
	
	public void add(Coordinate coordinate) {
//		synchronized (lock) {
			GeoPoint gpt = new GeoPoint(coordinate);
			if(points!=null) {
				points.add(gpt);
			}
//		}
	}
	
	public void set(int index, GeoPoint geoPoint) {
//		synchronized (lock) {
			if(points!=null) {
				points.set(index, geoPoint);
			}
//		}
	}
	
	public void set(int index, Coordinate coordinate) {
//		synchronized (lock) {
			if(points!=null && points.get(index)!=null) {
				points.get(index).setCoordinate(coordinate);
			}
//		}
	}
	
	public GeoPoint getPoints(int i) {
		return points.get(i);
	}
	
	public Coordinate getCoordinate(int i) {
		return points.get(i)!=null ? points.get(i).getCoordinate() : null;
	}
	
	public GeomList<GeoPoint> getPoints() {
		return points;
	}

	public void setPoints(GeomList<GeoPoint> points) {
		this.points = points;
	}

	@Override
	public GeomType getGeomType() {
		// TODO Auto-generated method stub
		return GeomType.MULTIPOINT;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return points.getSize();
	}

	@Override
	public int getCoordinateSize() {
		// TODO Auto-generated method stub
		return points.getSize();
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
		return points.isEmpty();
	}	
}
