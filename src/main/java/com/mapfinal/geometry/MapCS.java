package com.mapfinal.geometry;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;

public interface MapCS extends CoordinateSequence {

	public void addCoordinate(int index, Coordinate coordinate);

	public void addCoordinate(Coordinate coordinate);

	public void setCoordinate(int index, Coordinate coordinate);

	public void removeCoordinate(int index);
	
	default Coordinate enforceConsistency(Coordinate coord) {
		Coordinate sample = createCoordinate();
		sample.setCoordinate(coord);
		return sample;
	}
}
