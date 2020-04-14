package com.mapfinal.converter.scene;

import org.locationtech.jts.geom.Coordinate;

public class ScenePoint extends Coordinate {

	// 屏幕坐标x=lng,y=lat
	// 经纬度坐标x=lng,y=lat
	// public double x, y, z = 0;

	public ScenePoint(double x, double y) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
	}

	public ScenePoint(ScenePoint point) {
		// TODO Auto-generated constructor stub
		this.x = point.x;
		this.y = point.y;
	}

	public ScenePoint(double x, double y, double z) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public ScenePoint(Coordinate c) {
		// TODO Auto-generated constructor stub
		this.x = c.x;
		this.y = c.y;
		this.z = c.z;
	}
	
	public static ScenePoint create(double x, double y) {
		return new ScenePoint(x, y);
	}

	public int getSx() {
		return (int) Math.round(x);
	}

	public int getSy() {
		return (int) Math.round(y);
	}

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return new ScenePoint(this.x, this.y);
	}

	// @method add(otherPoint: Point): Point
	// Returns the result of addition of the current and the given points.
	public ScenePoint add(ScenePoint point) {
		// destructive, used directly for performance in situations where it's
		// safe to modify existing point
		this.x += point.x;
		this.y += point.y;
		return this;
	}

	// @method subtract(otherPoint: Point): Point
	// Returns the result of subtraction of the given point from the current.
	public ScenePoint subtract(ScenePoint point) {
		this.x -= point.x;
		this.y -= point.y;
		return this;
	}
	
	public ScenePoint subtract(double x, double y) {
		this.x -= x;
		this.y -= y;
		return this;
	}

	// @method divideBy(num: Number): Point
	// Returns the result of division of the current point by the given number.
	public ScenePoint divideBy(double num) {
		this.x /= num;
		this.y /= num;
		return this;
	}

	// @method multiplyBy(num: Number): Point
	// Returns the result of multiplication of the current point by the given
	// number.
	public ScenePoint multiplyBy(double num) {
		this.x *= num;
		this.y *= num;
		return this;
	}

	// @method scaleBy(scale: Point): Point
	// Multiply each coordinate of the current point by each coordinate of
	// `scale`. In linear algebra terms, multiply the point by the
	// [scaling
	// matrix](https://en.wikipedia.org/wiki/Scaling_%28geometry%29#Matrix_representation)
	// defined by `scale`.
	public ScenePoint scaleBy(ScenePoint point) {
		this.x *= point.x;
		this.y *= point.y;
		return this;
	}

	// @method unscaleBy(scale: Point): Point
	// Inverse of `scaleBy`. Divide each coordinate of the current point by
	// each coordinate of `scale`.
	public ScenePoint unscaleBy(ScenePoint point) {
		this.x /= point.x;
		this.y /= point.y;
		return this;
	}

	// @method round(): Point
	// Returns a copy of the current point with rounded coordinates.
	public ScenePoint round() {
		this.x = Math.round(this.x);
		this.y = Math.round(this.y);
		return this;
	}

	// @method floor(): Point
	// Returns a copy of the current point with floored coordinates (rounded
	// down).
	public ScenePoint floor() {
		this.x = (int) Math.floor(this.x);
		this.y = (int) Math.floor(this.y);
		return this;
	}

	// @method ceil(): Point
	// Returns a copy of the current point with ceiled coordinates (rounded up).
	public ScenePoint ceil() {
		this.x = (int) Math.ceil(this.x);
		this.y = (int) Math.ceil(this.y);
		return this;
	}

	// @method distanceTo(otherPoint: Point): Number
	// Returns the cartesian distance between the current and the given points.
	public double distanceTo(ScenePoint point) {
		double x = point.x - this.x, y = point.y - this.y;
		return Math.sqrt(x * x + y * y);
	}

	// @method equals(otherPoint: Point): Boolean
	// Returns `true` if the given point has the same coordinates.
	public boolean equals(ScenePoint point) {
		return point.x == this.x && point.y == this.y;
	}

	// @method contains(otherPoint: Point): Boolean
	// Returns `true` if both coordinates of the given point are less than the
	// corresponding current point coordinates (in absolute values).
	public boolean contains(ScenePoint point) {
		return Math.abs(point.x) <= Math.abs(this.x) && Math.abs(point.y) <= Math.abs(this.y);
	}
	
	public ScenePoint translate(Coordinate dc) {
		this.x += dc.x;
		this.y += dc.y;
		return this;
	}
	
	public ScenePoint translate(double x, double y) {
		this.x += x;
		this.y += y;
		return this;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
