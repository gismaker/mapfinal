package com.mapfinal.geometry;

import java.io.ObjectStreamException;
import java.lang.ref.SoftReference;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequences;

public abstract class PackedMapCS implements MapCS {
	/**
	 * The dimensions of the coordinates held in the packed array
	 */
	protected int dimension;

	/**
	 * The number of measures of the coordinates held in the packed array.
	 */
	protected int measures;

	protected PackedMapCS(int dimension, int measures) {
		if (dimension - measures < 2) {
			throw new IllegalArgumentException("Must have at least 2 spatial dimensions");
		}
		this.dimension = dimension;
		this.measures = measures;
	}

	/**
	 * A soft reference to the Coordinate[] representation of this sequence.
	 * Makes repeated coordinate array accesses more efficient.
	 */
	protected transient SoftReference<Coordinate[]> coordRef;

	/**
	 * @see org.locationtech.jts.geom.CoordinateSequence#getDimension()
	 */
	public int getDimension() {
		return this.dimension;
	}

	@Override
	public int getMeasures() {
		return this.measures;
	}

	/**
	 * @see org.locationtech.jts.geom.CoordinateSequence#getCoordinate(int)
	 */
	public Coordinate getCoordinate(int i) {
		Coordinate[] coords = getCachedCoords();
		if (coords != null)
			return coords[i];
		else
			return getCoordinateInternal(i);
	}

	/**
	 * @see org.locationtech.jts.geom.CoordinateSequence#getCoordinate(int)
	 */
	public Coordinate getCoordinateCopy(int i) {
		return getCoordinateInternal(i);
	}

	/**
	 * @see org.locationtech.jts.geom.CoordinateSequence#getCoordinate(int)
	 */
	public void getCoordinate(int i, Coordinate coord) {
		coord.x = getOrdinate(i, 0);
		coord.y = getOrdinate(i, 1);
		if (hasZ()) {
			coord.setZ(getZ(i));
		}
		if (hasM()) {
			coord.setM(getM(i));
		}
	}

	/**
	 * @see org.locationtech.jts.geom.CoordinateSequence#toCoordinateArray()
	 */
	public Coordinate[] toCoordinateArray() {
		Coordinate[] coords = getCachedCoords();
		// testing - never cache
		if (coords != null)
			return coords;

		coords = new Coordinate[size()];
		for (int i = 0; i < coords.length; i++) {
			coords[i] = getCoordinateInternal(i);
		}
		coordRef = new SoftReference<Coordinate[]>(coords);

		return coords;
	}

	/**
	 * @return
	 */
	private Coordinate[] getCachedCoords() {
		if (coordRef != null) {
			Coordinate[] coords = (Coordinate[]) coordRef.get();
			if (coords != null) {
				return coords;
			} else {
				// System.out.print("-");
				coordRef = null;
				return null;
			}
		} else {
			// System.out.print("-");
			return null;
		}

	}

	/**
	 * @see org.locationtech.jts.geom.CoordinateSequence#getX(int)
	 */
	public double getX(int index) {
		return getOrdinate(index, 0);
	}

	/**
	 * @see org.locationtech.jts.geom.CoordinateSequence#getY(int)
	 */
	public double getY(int index) {
		return getOrdinate(index, 1);
	}

	/**
	 * @see org.locationtech.jts.geom.CoordinateSequence#getOrdinate(int, int)
	 */
	public abstract double getOrdinate(int index, int ordinateIndex);

	/**
	 * Sets the first ordinate of a coordinate in this sequence.
	 *
	 * @param index
	 *            the coordinate index
	 * @param value
	 *            the new ordinate value
	 */
	public void setX(int index, double value) {
		coordRef = null;
		setOrdinate(index, 0, value);
	}

	/**
	 * Sets the second ordinate of a coordinate in this sequence.
	 *
	 * @param index
	 *            the coordinate index
	 * @param value
	 *            the new ordinate value
	 */
	public void setY(int index, double value) {
		coordRef = null;
		setOrdinate(index, 1, value);
	}

	public String toString() {
		return CoordinateSequences.toString(this);
	}

	protected Object readResolve() throws ObjectStreamException {
		coordRef = null;
		return this;
	}

	/**
	 * Returns a Coordinate representation of the specified coordinate, by
	 * always building a new Coordinate object
	 *
	 * @param index
	 * @return
	 */
	protected abstract Coordinate getCoordinateInternal(int index);

	/**
	 * @see java.lang.Object#clone()
	 * @deprecated
	 */
	public abstract Object clone();

	public abstract PackedMapCS copy();

	/**
	 * Sets the ordinate of a coordinate in this sequence. <br>
	 * Warning: for performance reasons the ordinate index is not checked - if
	 * it is over dimensions you may not get an exception but a meaningless
	 * value.
	 *
	 * @param index
	 *            the coordinate index
	 * @param ordinate
	 *            the ordinate index in the coordinate, 0 based, smaller than
	 *            the number of dimensions
	 * @param value
	 *            the new ordinate value
	 */
	public abstract void setOrdinate(int index, int ordinate, double value);

}
