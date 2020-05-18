package com.mapfinal.geometry;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.CoordinateSequenceFactory;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;

public class MapCSFactory implements CoordinateSequenceFactory, Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -490967652434798906L;

	private Class<? extends CoordinateSequence> implClass;

	public MapCSFactory(Class<? extends CoordinateSequence> implClass) {
		this.implClass = implClass;
	}

	/**
	 * Returns a {@link CoordinateArraySequence} based on the given array (the
	 * array is not copied).
	 *
	 * @param coordinates
	 *            the coordinates, which may not be null nor contain null
	 *            elements
	 */
	public CoordinateSequence create(Coordinate[] coordinates) {
		try {
			Constructor c1 = implClass.getDeclaredConstructor(Coordinate[].class);
			c1.setAccessible(true);
			CoordinateSequence cs = (CoordinateSequence) c1.newInstance(new Object[]{coordinates});
			return cs;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return new CoordinateArraySequence(coordinates);
	}

	/**
	 * @see org.locationtech.jts.geom.CoordinateSequenceFactory#create(org.locationtech.jts.geom.CoordinateSequence)
	 */
	public CoordinateSequence create(CoordinateSequence coordSeq) {
		try {
			Constructor c1 = implClass.getDeclaredConstructor(CoordinateSequence.class);
			c1.setAccessible(true);
			CoordinateSequence cs = (CoordinateSequence) c1.newInstance(coordSeq);
			return cs;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return new CoordinateArraySequence(coordSeq);
	}

	/**
	 * The created sequence dimension is clamped to be &lt;= 3.
	 * 
	 * @see org.locationtech.jts.geom.CoordinateSequenceFactory#create(int, int)
	 *
	 */
	public CoordinateSequence create(int size, int dimension) {
		if (dimension > 3)
			dimension = 3;
		// throw new IllegalArgumentException("dimension must be <= 3");

		// handle bogus dimension
		if (dimension < 2)
			dimension = 2;

		try {
			Constructor c1 = implClass.getDeclaredConstructor(int.class, int.class);
			c1.setAccessible(true);
			CoordinateSequence cs = (CoordinateSequence) c1.newInstance(size, dimension);
			return cs;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return new CoordinateArraySequence(size, dimension);
	}

	public CoordinateSequence create(int size, int dimension, int measures) {
		int spatial = dimension - measures;

		if (measures > 1) {
			measures = 1; // clip measures
			// throw new IllegalArgumentException("measures must be <= 1");
		}
		if ((spatial) > 3) {
			spatial = 3; // clip spatial dimension
			// throw new IllegalArgumentException("spatial dimension must be <=
			// 3");
		}

		if (spatial < 2)
			spatial = 2; // handle bogus spatial dimension

		try {
			Constructor c1 = implClass.getDeclaredConstructor(int.class, int.class, int.class);
			c1.setAccessible(true);
			CoordinateSequence cs = (CoordinateSequence) c1.newInstance(size, spatial + measures, measures);
			return cs;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return new CoordinateArraySequence(size, spatial + measures, measures);
	}

}
