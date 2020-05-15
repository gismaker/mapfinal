package com.mapfinal.geometry;

import org.locationtech.jts.geom.Coordinate;

public class GeoPoint implements Geom {

	protected Coordinate coordinate;
	
	public GeoPoint(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	
	public GeoPoint(double x, double y) {
		this.coordinate = new Coordinate(x, y);
	}
	
	public GeoPoint(double x, double y, double z) {
		this.coordinate = new Coordinate(x, y, z);
	}
	
	public void set(double x, double y) {
		this.coordinate.setX(x);
		this.coordinate.setY(y);
	}
	
	public void set(double x, double y, double z) {
		this.coordinate.setX(x);
		this.coordinate.setY(y);
		this.coordinate.setZ(z);
	}
	
	public double x() {
		return coordinate.x;
	}
	
	public double y() {
		return coordinate.y;
	}
	
	public double getZ() {
		return coordinate.getZ();
	}


	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	@Override
	public GeomType getGeomType() {
		// TODO Auto-generated method stub
		return GeomType.POINT;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getCoordinateSize() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public GeoPoint getCentroid() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public GeoPoint getInteriorPoint() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return coordinate == null ? true : false;
	}
	
	
	public GeoPoint plus(GeoPoint otherPoint) {
		if (!isEmpty()) return this;
		this.coordinate.x += otherPoint.x();
		this.coordinate.y += otherPoint.y();
		return this;
	}
	
	/**
	 * Returns the result of subtraction of the given point from the current.
	 * @return
	 */
	public GeoPoint subtract(GeoPoint otherPoint) {
		if (!isEmpty()) return this;
		this.coordinate.x -= otherPoint.x();
		this.coordinate.y -= otherPoint.y();
		return this;
	}
	
	/**
	 * Returns the result of division of the current point by the given number.
	 * @param num
	 * @return
	 */
	public GeoPoint divideBy(double num) {
		if (!isEmpty()) return this;
		this.coordinate.x /= num;
		this.coordinate.y /= num;
		return this;
	}
	
	/**
	 * Returns the result of multiplication of the current point by the given number.
	 * @param num
	 * @return
	 */
	public GeoPoint multiplyBy(double num) {
		if (!isEmpty()) return this;
		this.coordinate.x *= num;
		this.coordinate.y *= num;
		return this;
	}
	
	/**
	 * Multiply each coordinate of the current point by each coordinate of scale. In linear algebra terms, multiply the point by the scaling matrix defined by scale.
	 * @param scale
	 * @return
	 */
	public GeoPoint scaleBy(GeoPoint scale) {
		if (!isEmpty()) return this;
		this.coordinate.x *= scale.x();
		this.coordinate.y *= scale.y();
		return this;
	}
	
	/**
	 * Inverse of scaleBy. Divide each coordinate of the current point by each coordinate of scale.
	 * @param scale
	 * @return
	 */
	public GeoPoint unscaleBy(GeoPoint scale) {
		if (!isEmpty()) return this;
		this.coordinate.x /= scale.x();
		this.coordinate.y /= scale.y();
		return this;
	}

	/**
	 * Returns a copy of the current point with rounded coordinates.
	 * @return
	 */
	public GeoPoint round() {
		if(isEmpty()) return this;
		this.coordinate.x = Math.round(this.coordinate.x);
		this.coordinate.y = Math.round(this.coordinate.y);
		return this;
	}

	/**
	 * Returns a copy of the current point with floored coordinates (rounded down).
	 * @return
	 */
	public GeoPoint floor() {
		if(isEmpty()) return this;
		this.coordinate.x = Math.floor(this.coordinate.x);
		this.coordinate.y = Math.floor(this.coordinate.y);
		return this;
	}
	
	/**
	 * Returns a copy of the current point with ceiled coordinates (rounded up).
	 * @return
	 */
	public GeoPoint ceil() {
		if(isEmpty()) return this;
		this.coordinate.x = Math.ceil(this.coordinate.x);
		this.coordinate.y = Math.ceil(this.coordinate.y);
		return this;
	}

	/**
	 * Returns the cartesian distance between the current and the given points.
	 * @param otherPoint
	 * @return
	 */
	public double distance(GeoPoint otherPoint) {
		return this.coordinate.distance(otherPoint.getCoordinate());
	}
	
	public double distance3D(GeoPoint otherPoint) {
		return this.coordinate.distance3D(otherPoint.getCoordinate());
	}
	
	/**
	 * Returns true if the given point has the same coordinates.
	 * @param otherPoint
	 * @return
	 */
	public boolean equals(GeoPoint otherPoint) {
		return this.coordinate.equals(otherPoint.getCoordinate());
	}
	
	public boolean equals2D(GeoPoint otherPoint) {
		return this.coordinate.equals2D(otherPoint.getCoordinate());
	}
	
	public boolean equals3D(GeoPoint otherPoint) {
		return this.coordinate.equals3D(otherPoint.getCoordinate());
	}
	
	/**
	 * Returns true if both coordinates of the given point are less than the corresponding current point coordinates (in absolute values).
	 * @param otherPoint
	 * @return
	 */
	public boolean contains(GeoPoint otherPoint) {
		return Math.abs(otherPoint.x()) <= Math.abs(this.coordinate.x) && Math.abs(otherPoint.y()) <= Math.abs(this.coordinate.y);
	}
	
	public double getX() {
		return this.coordinate.x;
	}

	public void setX(double x) {
		this.coordinate.x = x;
	}

	public double getY() {
		return this.coordinate.y;
	}

	public void setY(double y) {
		this.coordinate.y = y;
	}
	
	@Override
	public String toString() {
		return "Point"+coordinate.toString();
	}
}
