package com.mapfinal.converter.scene;

public class Transformation {

	private double _a, _b, _c, _d;

	public Transformation(double a, double b, double c, double d) {
		this._a = a;
		this._b = b;
		this._c = c;
		this._d = d;
	}

	// @method transform(point: Point, scale?: Number): Point
	// Returns a transformed point, optionally multiplied by the given scale.
	// Only accepts actual `L.Point` instances, not arrays.
	// (Point, Number) -> Point
	public ScenePoint transform(ScenePoint point, Double scale) {
		//System.out.println("Point: " + point.x + ", " + point.y + ", Scale: " + scale);
		//System.out.println("Param: " + _a + ", " + _b + ", " + _c + ", " + _d);
		scale = scale == null ? 1 : scale;
		point.x = scale * (this._a * point.x + this._b);
		point.y = scale * (this._c * point.y + this._d);
		return point;
	}

	// @method untransform(point: Point, scale?: Number): Point
	// Returns the reverse transformation of the given point, optionally divided
	// by the given scale. Only accepts actual `L.Point` instances, not arrays.
	public ScenePoint untransform(ScenePoint point, Double scale) {
		scale = scale == null ? 1 : scale;
		return new ScenePoint((point.x / scale - this._b) / this._a, (point.y / scale - this._d) / this._c);
	}
}
