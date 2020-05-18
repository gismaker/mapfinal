package com.mapfinal.geometry;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateArrays;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Coordinates;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;

public class ArrayMapCS implements MapCS, Serializable {
	private static final long serialVersionUID = 8671521396671195084L;
	/**
	 * The actual dimension of the coordinates in the sequence. Allowable values are
	 * 2, 3 or 4.
	 */
	private int dimension = 3;
	/**
	 * The number of measures of the coordinates in the sequence. Allowable values
	 * are 0 or 1.
	 */
	private int measures = 0;

	private Coordinate[] coordinates;

	/**
	 * Constructs a sequence based on the given array of {@link Coordinate}s (the
	 * array is not copied). The coordinate dimension defaults to 3.
	 *
	 * @param coordinates the coordinate array that will be referenced.
	 */
	public ArrayMapCS(Coordinate[] coordinates) {
		this(coordinates, CoordinateArrays.dimension(coordinates), CoordinateArrays.measures(coordinates));
	}

	/**
	 * Constructs a sequence based on the given array of {@link Coordinate}s (the
	 * array is not copied).
	 *
	 * @param coordinates the coordinate array that will be referenced.
	 * @param dimension   the dimension of the coordinates
	 */
	public ArrayMapCS(Coordinate[] coordinates, int dimension) {
		this(coordinates, dimension, CoordinateArrays.measures(coordinates));
	}

	/**
	 * Constructs a sequence based on the given array of {@link Coordinate}s (the
	 * array is not copied).
	 *
	 * @param coordinates the coordinate array that will be referenced.
	 * @param dimension   the dimension of the coordinates
	 */
	public ArrayMapCS(Coordinate[] coordinates, int dimension, int measures) {
		this.dimension = dimension;
		this.measures = measures;
		if (coordinates == null) {
			this.coordinates = new Coordinate[0];
		} else {
			this.coordinates = enforceArrayConsistency(coordinates);
		}
	}

	/**
	 * Constructs a sequence of a given size, populated with new
	 * {@link Coordinate}s.
	 *
	 * @param size the size of the sequence to create
	 */
	public ArrayMapCS(int size) {
		coordinates = new Coordinate[size];
		for (int i = 0; i < size; i++) {
			coordinates[i] = new Coordinate();
		}
	}

	/**
	 * Constructs a sequence of a given size, populated with new
	 * {@link Coordinate}s.
	 *
	 * @param size      the size of the sequence to create
	 * @param dimension the dimension of the coordinates
	 */
	public ArrayMapCS(int size, int dimension) {
		coordinates = new Coordinate[size];
		this.dimension = dimension;
		for (int i = 0; i < size; i++) {
			coordinates[i] = Coordinates.create(dimension);
		}
	}

	/**
	 * Constructs a sequence of a given size, populated with new
	 * {@link Coordinate}s.
	 *
	 * @param size      the size of the sequence to create
	 * @param dimension the dimension of the coordinates
	 */
	public ArrayMapCS(int size, int dimension, int measures) {
		coordinates = new Coordinate[size];
		this.dimension = dimension;
		this.measures = measures;
		for (int i = 0; i < size; i++) {
			coordinates[i] = createCoordinate();
		}
	}

	/**
	 * Creates a new sequence based on a deep copy of the given
	 * {@link CoordinateSequence}. The coordinate dimension is set to equal the
	 * dimension of the input.
	 *
	 * @param coordSeq the coordinate sequence that will be copied.
	 */
	public ArrayMapCS(CoordinateSequence coordSeq) {
		// NOTE: this will make a sequence of the default dimension
		if (coordSeq == null) {
			coordinates = new Coordinate[0];
			return;
		}
		dimension = coordSeq.getDimension();
		measures = coordSeq.getMeasures();
		coordinates = new Coordinate[coordSeq.size()];

		for (int i = 0; i < coordinates.length; i++) {
			coordinates[i] = coordSeq.getCoordinateCopy(i);
		}
	}

	/**
	 * Ensure array contents of the same type, making use of
	 * {@link #createCoordinate()} as needed.
	 * <p>
	 * A new array will be created if needed to return a consistent result.
	 * </p>
	 * 
	 * @param array array containing consistent coordinate instances
	 */
	protected Coordinate[] enforceArrayConsistency(Coordinate[] array) {
		Coordinate sample = createCoordinate();
		Class<?> type = sample.getClass();
		boolean isConsistent = true;
		for (int i = 0; i < array.length; i++) {
			Coordinate coordinate = array[i];
			if (coordinate != null && !coordinate.getClass().equals(type)) {
				isConsistent = false;
				break;
			}
		}
		if (isConsistent) {
			return array;
		} else {
			Class<? extends Coordinate> coordinateType = sample.getClass();
			Coordinate copy[] = (Coordinate[]) Array.newInstance(coordinateType, array.length);
			for (int i = 0; i < copy.length; i++) {
				Coordinate coordinate = array[i];
				if (coordinate != null && !coordinate.getClass().equals(type)) {
					Coordinate duplicate = createCoordinate();
					duplicate.setCoordinate(coordinate);
					copy[i] = duplicate;
				} else {
					copy[i] = coordinate;
				}
			}
			return copy;
		}
	}

	/**
	 * @see org.locationtech.jts.geom.CoordinateSequence#getDimension()
	 */
	public int getDimension() {
		return dimension;
	}

	@Override
	public int getMeasures() {
		return measures;
	}

	/**
	 * Get the Coordinate with index i.
	 *
	 * @param i the index of the coordinate
	 * @return the requested Coordinate instance
	 */
	public Coordinate getCoordinate(int i) {
		return coordinates[i];
	}

	/**
	 * Get a copy of the Coordinate with index i.
	 *
	 * @param i the index of the coordinate
	 * @return a copy of the requested Coordinate
	 */
	public Coordinate getCoordinateCopy(int i) {
		Coordinate copy = createCoordinate();
		copy.setCoordinate(coordinates[i]);
		return copy;
	}

	/**
	 * @see org.locationtech.jts.geom.CoordinateSequence#getX(int)
	 */
	public void getCoordinate(int index, Coordinate coord) {
		coord.setCoordinate(coordinates[index]);
	}

	/**
	 * @see org.locationtech.jts.geom.CoordinateSequence#getX(int)
	 */
	public double getX(int index) {
		return coordinates[index].x;
	}

	/**
	 * @see org.locationtech.jts.geom.CoordinateSequence#getY(int)
	 */
	public double getY(int index) {
		return coordinates[index].y;
	}

	/**
	 * @see org.locationtech.jts.geom.CoordinateSequence#getZ(int)
	 */
	public double getZ(int index) {
		if (hasZ()) {
			return coordinates[index].getZ();
		} else {
			return Double.NaN;
		}

	}

	/**
	 * @see org.locationtech.jts.geom.CoordinateSequence#getM(int)
	 */
	public double getM(int index) {
		if (hasM()) {
			return coordinates[index].getM();
		} else {
			return Double.NaN;
		}
	}

	/**
	 * @see org.locationtech.jts.geom.CoordinateSequence#getOrdinate(int, int)
	 */
	public double getOrdinate(int index, int ordinateIndex) {
		switch (ordinateIndex) {
		case CoordinateSequence.X:
			return coordinates[index].x;
		case CoordinateSequence.Y:
			return coordinates[index].y;
		default:
			return coordinates[index].getOrdinate(ordinateIndex);
		}
	}

	/**
	 * Creates a deep copy of the Object
	 *
	 * @return The deep copy
	 * @deprecated
	 */
	public Object clone() {
		return copy();
	}

	/**
	 * Creates a deep copy of the CoordinateArraySequence
	 *
	 * @return The deep copy
	 */
	public CoordinateArraySequence copy() {
		Coordinate[] cloneCoordinates = new Coordinate[size()];
		for (int i = 0; i < coordinates.length; i++) {
			Coordinate duplicate = createCoordinate();
			duplicate.setCoordinate(coordinates[i]);
			cloneCoordinates[i] = duplicate;
		}
		return new CoordinateArraySequence(cloneCoordinates, dimension, measures);
	}

	/**
	 * Returns the size of the coordinate sequence
	 *
	 * @return the number of coordinates
	 */
	public int size() {
		return coordinates.length;
	}

	/**
	 * @see org.locationtech.jts.geom.CoordinateSequence#setOrdinate(int, int,
	 *      double)
	 */
	public void setOrdinate(int index, int ordinateIndex, double value) {
		switch (ordinateIndex) {
		case CoordinateSequence.X:
			coordinates[index].x = value;
			break;
		case CoordinateSequence.Y:
			coordinates[index].y = value;
			break;
		default:
			coordinates[index].setOrdinate(ordinateIndex, value);
		}
	}

	/**
	 * This method exposes the internal Array of Coordinate Objects
	 *
	 * @return the Coordinate[] array.
	 */
	public Coordinate[] toCoordinateArray() {
		return coordinates;
	}

	public Envelope expandEnvelope(Envelope env) {
		for (int i = 0; i < coordinates.length; i++) {
			env.expandToInclude(coordinates[i]);
		}
		return env;
	}

	/**
	 * Returns the string Representation of the coordinate array
	 *
	 * @return The string
	 */
	public String toString() {
		if (coordinates.length > 0) {
			StringBuilder strBuilder = new StringBuilder(17 * coordinates.length);
			strBuilder.append('(');
			strBuilder.append(coordinates[0]);
			for (int i = 1; i < coordinates.length; i++) {
				strBuilder.append(", ");
				strBuilder.append(coordinates[i]);
			}
			strBuilder.append(')');
			return strBuilder.toString();
		} else {
			return "()";
		}
	}

	@Override
	public void addCoordinate(int index, Coordinate coordinate) {
		// TODO Auto-generated method stub
		List<Coordinate> list1 = Arrays.asList(coordinates);
		list1.add(index, coordinate);
		this.coordinates = list1.toArray(new Coordinate[list1.size()]);
	}

	@Override
	public void addCoordinate(Coordinate coordinate) {
		// TODO Auto-generated method stub
		List<Coordinate> list1 = Arrays.asList(coordinates);
		list1.add(coordinate);
		this.coordinates = list1.toArray(new Coordinate[list1.size()]);
	}

	@Override
	public void setCoordinate(int index, Coordinate coordinate) {
		// TODO Auto-generated method stub
		Coordinate coord = getCoordinate(index);
		if(coord!=null) {
			coord.setCoordinate(coordinate);
		}
	}

	@Override
	public void removeCoordinate(int index) {
		// TODO Auto-generated method stub
		List<Coordinate> list1 = Arrays.asList(coordinates);
		list1.remove(index);
		this.coordinates = list1.toArray(new Coordinate[list1.size()]);
	}
}