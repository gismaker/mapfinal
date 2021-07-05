package com.mapfinal.geometry;

public class ScreenPoint {
	public float x;
	public float y;
	
	public ScreenPoint(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public static ScreenPoint by(float x, float y) {
		return new ScreenPoint(x, y);
	}
	
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float x() {
		return x;
	}
	
	public float y() {
		return y;
	}
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "(" + x +"," + y+")";
	}
	
	public ScreenPoint clone() {
		return new ScreenPoint(x, y);
	}
	
	public ScreenPoint add(ScreenPoint otherPoint) {
		this.x += otherPoint.x();
		this.y += otherPoint.y();
		return this;
	}
	
	public ScreenPoint plus(ScreenPoint otherPoint) {
		this.x += otherPoint.x();
		this.y += otherPoint.y();
		return this;
	}
	
	/**
	 * Returns the result of subtraction of the given point from the current.
	 * @return
	 */
	public ScreenPoint subtract(ScreenPoint otherPoint) {
		this.x -= otherPoint.x();
		this.y -= otherPoint.y();
		return this;
	}
	
	/**
	 * Returns the result of division of the current point by the given number.
	 * @param num
	 * @return
	 */
	public ScreenPoint divideBy(double num) {
		this.x /= num;
		this.y /= num;
		return this;
	}
	
	/**
	 * Returns the result of multiplication of the current point by the given number.
	 * @param num
	 * @return
	 */
	public ScreenPoint multiplyBy(double num) {
		this.x *= num;
		this.y *= num;
		return this;
	}
	
	/**
	 * Multiply each coordinate of the current point by each coordinate of scale. In linear algebra terms, multiply the point by the scaling matrix defined by scale.
	 * @param scale
	 * @return
	 */
	public ScreenPoint scaleBy(ScreenPoint scale) {
		this.x *= scale.x();
		this.y *= scale.y();
		return this;
	}
	
	/**
	 * Inverse of scaleBy. Divide each coordinate of the current point by each coordinate of scale.
	 * @param scale
	 * @return
	 */
	public ScreenPoint unscaleBy(ScreenPoint scale) {
		this.x /= scale.x();
		this.y /= scale.y();
		return this;
	}

	/**
	 * Returns a copy of the current point with rounded coordinates.
	 * @return
	 */
	public ScreenPoint round() {
		this.x = Math.round(this.x);
		this.y = Math.round(this.y);
		return this;
	}

	/**
	 * Returns a copy of the current point with floored coordinates (rounded down).
	 * @return
	 */
	public ScreenPoint floor() {
		this.x = (float) Math.floor(this.x);
		this.y = (float) Math.floor(this.y);
		return this;
	}
	
	/**
	 * Returns a copy of the current point with ceiled coordinates (rounded up).
	 * @return
	 */
	public ScreenPoint ceil() {
		this.x = (float) Math.ceil(this.x);
		this.y = (float) Math.ceil(this.y);
		return this;
	}

	/**
	 * Returns the cartesian distance between the current and the given points.
	 * @param otherPoint
	 * @return
	 */
	public double distance(ScreenPoint otherPoint) {
		float dx = otherPoint.x - this.x,
			    dy = otherPoint.y - this.y;
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	/**
	 * Returns true if the given point has the same coordinates.
	 * @param otherPoint
	 * @return
	 */
	public boolean equals(ScreenPoint otherPoint) {
		return otherPoint.x == this.x &&
				otherPoint.y == this.y;
	}
	
	/**
	 * Returns true if both coordinates of the given point are less than the corresponding current point coordinates (in absolute values).
	 * @param otherPoint
	 * @return
	 */
	public boolean contains(ScreenPoint otherPoint) {
		return Math.abs(otherPoint.x()) <= Math.abs(this.x) && Math.abs(otherPoint.y()) <= Math.abs(this.y);
	}
}
