package com.mapfinal.geometry;

import java.util.Collection;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateList;

public class GeoLineString implements Geom {

	protected CoordinateList coordinates;
	protected final Object lock = new Object();

	public GeoLineString(Collection<? extends Coordinate> coords, boolean allowRepeated) {
		coordinates = new CoordinateList();
		coordinates.addAll(coords, allowRepeated);
	}

	public GeoLineString(Coordinate[] coords, boolean allowRepeated) {
		coordinates = new CoordinateList(coords, allowRepeated);
	}

	@Override
	public GeomType getGeomType() {
		// TODO Auto-generated method stub
		return GeomType.LINESTRING;
	}

	public void add(Coordinate coordinate) {
		synchronized (lock) {
			if (coordinates != null) {
				coordinates.add(coordinate);
			}
		}
	}

	public void add(Coordinate coordinate, boolean allowRepeated) {
		synchronized (lock) {
			if (coordinates != null) {
				coordinates.add(coordinate, allowRepeated);
			}
		}
	}

	public void add(int index, Coordinate coordinate) {
		synchronized (lock) {
			if (coordinates != null) {
				coordinates.add(index, coordinate);
			}
		}
	}

	public void add(int index, Coordinate coordinate, boolean allowRepeated) {
		synchronized (lock) {
			if (coordinates != null) {
				coordinates.add(index, coordinate, allowRepeated);
			}
		}
	}
	
	public void set(int index, Coordinate coordinate) {
		synchronized (lock) {
			if (coordinates != null) {
				coordinates.set(index, coordinate);
			}
		}
	}
	
	public void set(int index, double x, double y) {
		synchronized (lock) {
			if (coordinates != null && coordinates.get(index)!=null) {
				coordinates.get(index).setX(x);
				coordinates.get(index).setY(y);
			}
		}
	}
	
	public void set(int index, double x, double y, double z) {
		synchronized (lock) {
			if (coordinates != null && coordinates.get(index)!=null) {
				coordinates.get(index).setX(x);
				coordinates.get(index).setY(y);
				coordinates.get(index).setZ(z);
			}
		}
	}
	
	public Coordinate getCoordinate(int i) {
		return coordinates.get(i);
	}

	public CoordinateList getCoordinates() {
		return coordinates;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return coordinates.size();
	}

	@Override
	public int getCoordinateSize() {
		// TODO Auto-generated method stub
		return coordinates.size();
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
		return coordinates.isEmpty();
	}

	public boolean isClosed() {
		if (isEmpty()) {
			return false;
		}
		return getCoordinate(0).equals2D(getCoordinate(getCoordinateSize() - 1));
	}

	public boolean isRing() {
		return isClosed();
	}
}
