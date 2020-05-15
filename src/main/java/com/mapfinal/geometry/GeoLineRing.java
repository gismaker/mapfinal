package com.mapfinal.geometry;

import java.util.Collection;

import org.locationtech.jts.geom.Coordinate;

public class GeoLineRing extends GeoLineString {

	public static final int MINIMUM_VALID_SIZE = 4;

	public GeoLineRing(Collection<? extends Coordinate> coordinates, boolean allowRepeated) {
		super(coordinates, allowRepeated);
		validateConstruction();
	}

	public GeoLineRing(Coordinate[] coordinates, boolean allowRepeated) {
		super(coordinates, allowRepeated);
		validateConstruction();
	}

	public void validateConstruction() {
		if (!isEmpty() && !super.isClosed()) {
			throw new IllegalArgumentException("Points of LinearRing do not form a closed linestring");
		}
		if (getCoordinateSize() >= 1 && getCoordinateSize() < MINIMUM_VALID_SIZE) {
			throw new IllegalArgumentException(
					"Invalid number of points in LinearRing (found " + getCoordinateSize() + " - must be 0 or >= 4)");
		}
	}
	
	public void add(Coordinate coordinate) {
		synchronized (lock) {
			if (coordinates != null) {
				coordinates.add(coordinates.size()-1, coordinate);
			}
		}
	}

	public void add(Coordinate coordinate, boolean allowRepeated) {
		synchronized (lock) {
			if (coordinates != null) {
				coordinates.add(coordinates.size()-1, coordinate, allowRepeated);
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

	@Override
	public boolean isClosed() {
		if (isEmpty()) {
			// empty LinearRings are closed by definition
			return true;
		}
		return super.isClosed();
	}

	@Override
	public GeomType getGeomType() {
		return GeomType.LINERING;
	}
}
