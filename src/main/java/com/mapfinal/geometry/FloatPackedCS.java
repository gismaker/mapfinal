package com.mapfinal.geometry;

import java.util.Arrays;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateXY;
import org.locationtech.jts.geom.CoordinateXYM;
import org.locationtech.jts.geom.CoordinateXYZM;
import org.locationtech.jts.geom.Envelope;

public class FloatPackedCS extends PackedMapCS {
	/**
	 * The packed coordinate array
	 */
	float[] coords;

	/**
	 * Constructs a packed coordinate sequence from an array of <code>float</code>s
	 *
	 * @param coords
	 * @param dimensions
	 */
	public FloatPackedCS(float[] coords, int dimension, int measures) {
		super(dimension, measures);
		if (coords.length % dimension != 0) {
			throw new IllegalArgumentException("Packed array does not contain " + "an integral number of coordinates");
		}
		this.coords = coords;
	}

	/**
	 * Constructs a packed coordinate sequence from an array of <code>double</code>s
	 *
	 * @param coordinates
	 * @param dimension
	 */
	public FloatPackedCS(double[] coordinates, int dimension, int measures) {
		super(dimension, measures);
		this.coords = new float[coordinates.length];

		for (int i = 0; i < coordinates.length; i++) {
			this.coords[i] = (float) coordinates[i];
		}
	}

	/**
	 * Builds a new packed coordinate sequence out of a coordinate array
	 * 
	 * @param coordinates
	 * @param dimension
	 */
	public FloatPackedCS(Coordinate[] coordinates, int dimension) {
		this(coordinates, dimension, 0);
	}
	
	public FloatPackedCS(Coordinate[] coordinates) {
		this(coordinates, 3, 0);
	}

	/**
	 * Constructs a packed coordinate sequence out of a coordinate array
	 *
	 * @param coordinates
	 */
	public FloatPackedCS(Coordinate[] coordinates, int dimension, int measures) {
		super(dimension, measures);
		if (coordinates == null)
			coordinates = new Coordinate[0];

		coords = new float[coordinates.length * this.dimension];
		for (int i = 0; i < coordinates.length; i++) {
			coords[i * this.dimension] = (float) coordinates[i].x;
			if (this.dimension >= 2)
				coords[i * this.dimension + 1] = (float) coordinates[i].y;
			if (this.dimension >= 3)
				coords[i * this.dimension + 2] = (float) coordinates[i].getZ();
		}
	}

	/**
	 * Constructs an empty packed coordinate sequence of a given size and dimension
	 */
	public FloatPackedCS(int size, int dimension, int measures) {
		super(dimension, measures);
		coords = new float[size * this.dimension];
	}

	/**
	 * @see org.locationtech.jts.geom.CoordinateSequence#getCoordinate(int)
	 */
	public Coordinate getCoordinateInternal(int i) {
		double x = coords[i * dimension];
		double y = coords[i * dimension + 1];
		if (dimension == 2 && measures == 0) {
			return new CoordinateXY(x, y);
		} else if (dimension == 3 && measures == 0) {
			double z = coords[i * dimension + 2];
			return new Coordinate(x, y, z);
		} else if (dimension == 3 && measures == 1) {
			double m = coords[i * dimension + 2];
			return new CoordinateXYM(x, y, m);
		} else if (dimension == 4 && measures == 1) {
			double z = coords[i * dimension + 2];
			double m = coords[i * dimension + 3];
			return new CoordinateXYZM(x, y, z, m);
		}
		return new Coordinate(x, y);
	}

	/**
	 * Gets the underlying array containing the coordinate values.
	 * 
	 * @return the array of coordinate values
	 */
	public float[] getRawCoordinates() {
		return coords;
	}

	/**
	 * @see org.locationtech.jts.geom.CoordinateSequence#size()
	 */
	public int size() {
		return coords.length / dimension;
	}

	/**
	 * @see java.lang.Object#clone()
	 * @deprecated
	 */
	public Object clone() {
		return copy();
	}

	public FloatPackedCS copy() {
		float[] clone = Arrays.copyOf(coords, coords.length);
		return new FloatPackedCS(clone, dimension, measures);
	}

	/**
	 * @see org.locationtech.jts.geom.CoordinateSequence#getOrdinate(int, int) For
	 *      performance reasons the ordinate index is not checked. If it is larger
	 *      than the dimension a meaningless value may be returned.
	 */
	public double getOrdinate(int index, int ordinate) {
		return coords[index * dimension + ordinate];
	}

	/**
	 * @see com.vividsolutions.jts.geom.PackedCoordinateSequence#setOrdinate(int,
	 *      int, double)
	 */
	public void setOrdinate(int index, int ordinate, double value) {
		coordRef = null;
		coords[index * dimension + ordinate] = (float) value;
	}

	public Envelope expandEnvelope(Envelope env) {
		for (int i = 0; i < coords.length; i += dimension) {
			env.expandToInclude(coords[i], coords[i + 1]);
		}
		return env;
	}

	@Override
	public void addCoordinate(int index, Coordinate coordinate) {
		// TODO Auto-generated method stub
		float[] newCoord = new float[coords.length + dimension];
		int len = index * dimension;
		for (int i = 0; i < len; i++) {
			newCoord[i] = coords[i];
		}
		newCoord[len] = (float) coordinate.x;
		if (this.dimension >= 2)
			newCoord[len + 1] = (float) coordinate.y;
		if (this.dimension >= 3)
			newCoord[len + 2] = (float) coordinate.getOrdinate(2); // Z or M
		if (this.dimension >= 4)
			newCoord[len + 3] = (float) coordinate.getOrdinate(3); // M
		for (int i = len + dimension; i < coords.length + dimension; i++) {
			newCoord[i] = coords[i-dimension];
		}
		coords = newCoord;
	}

	@Override
	public void addCoordinate(Coordinate coordinate) {
		// TODO Auto-generated method stub
		int len = coords.length;
		float[] newCoord = Arrays.copyOf(coords, coords.length + dimension);
		newCoord[len] = (float) coordinate.x;
		if (this.dimension >= 2)
			newCoord[len + 1] = (float) coordinate.y;
		if (this.dimension >= 3)
			newCoord[len + 2] = (float) coordinate.getOrdinate(2); // Z or M
		if (this.dimension >= 4)
			newCoord[len + 3] = (float) coordinate.getOrdinate(3); // M
		this.coords = newCoord;
	}

	@Override
	public void setCoordinate(int index, Coordinate coordinate) {
		// TODO Auto-generated method stub
		setOrdinate(index, 0, coordinate.x);
		if (this.dimension >= 2)
			setOrdinate(index, 1, coordinate.y);
		if (this.dimension >= 3)
			setOrdinate(index, 2, coordinate.getOrdinate(2));// Z or M
		if (this.dimension >= 4)
			setOrdinate(index, 3, coordinate.getOrdinate(3));// M
	}

	@Override
	public void removeCoordinate(int index) {
		// TODO Auto-generated method stub
		float[] newCoord = new float[coords.length - dimension];
		int len = index * dimension;
		for (int i = 0; i < len; i++) {
			newCoord[i] = coords[i];
		}
		for (int i = len + dimension; i < coords.length; i++) {
			newCoord[i-dimension] = coords[i];
		}
		coords = newCoord;
	}
}
