package com.mapfinal.converter.scene;

import com.mapfinal.geometry.Latlng;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;

/**
 * 场景坐标转换，不是屏幕坐标
 * @author yangyong
 *
 */
public abstract class SceneCRS {

	private String code;
	private Projection projection;
	private Transformation transformation;
	
	public abstract double distance(Latlng latlng1, Latlng latlng2);

	/**
	 * 经纬度转屏幕坐标
	 * @param latlng
	 * @param zoom
	 * @return
	 */
	public ScenePoint latLngToPoint(Latlng latlng, double zoom) {
		ScenePoint projectedPoint = this.projection.project(latlng);
		double scale = this.scale(zoom);
		return this.transformation.transform(projectedPoint, scale);
	}
	
	/**
	 * 屏幕坐标转经纬度
	 * @param point
	 * @param zoom
	 * @return
	 */
	public Latlng pointToLatLng(ScenePoint point, double zoom) {
		double scale = this.scale(zoom);
		//point.setX(xScaleIn(scale, point.x));
		point.setY(yScaleIn(scale, point.y));
		ScenePoint untransformedPoint = this.transformation.untransform(point, scale);
		return this.projection.unproject(untransformedPoint);
	}
	
	/**
	 * 当前Scene投影坐标转屏幕坐标
	 * @param coordinate
	 * @param zoom
	 * @return
	 */
	public ScenePoint coordinateToPoint(Coordinate coordinate, double zoom) {
		double scale = this.scale(zoom);
		return this.transformation.transform(new ScenePoint(coordinate), scale);
	}
	
	/**
	 * 屏幕坐标转当前Scene投影坐标
	 * @param point
	 * @param zoom
	 * @return
	 */
	public Coordinate pointToCoordinate(ScenePoint point, double zoom) {
		double scale = this.scale(zoom);
		//point.setX(xScaleIn(scale, point.x));
		point.setY(yScaleIn(scale, point.y));
		ScenePoint untransformedPoint = this.transformation.untransform(point, scale);
		return untransformedPoint;
	}
	
	private double xScaleIn(double scale, double c) {
		double s = 2*scale;
		c = c < 0 ? 0 : c;
		c = c > s ? s : c;
		return c;
	}
	
	private double yScaleIn(double scale, double c) {
		c = c < 0 ? 0 : c;
		c = c > scale ? scale : c;
		return c;
	}
	
	// @method project(latlng: LatLng): Point
	// Projects geographical coordinates into coordinates in units accepted for
	// this CRS (e.g. meters for EPSG:3857, for passing it to WMS services).
	public ScenePoint project(Latlng latlng) {
		return this.projection.project(latlng);
	}

	// @method unproject(point: Point): LatLng
	// Given a projected coordinate returns the corresponding LatLng.
	// The inverse of `project`.
	public Latlng unproject(ScenePoint point) {
		return this.projection.unproject(point);
	}
	
	// @method scale(zoom: Number): Number
	// Returns the scale used when transforming projected coordinates into
	// pixel coordinates for a particular zoom. For example, it returns
	// `256 * 2^zoom` for Mercator-based CRS.
	public double scale(double zoom) {
		return 256 * Math.pow(2, zoom);
	}

	// @method zoom(scale: Number): Number
	// Inverse of `scale()`, returns the zoom level corresponding to a scale
	// factor of `scale`.
	public double zoom(float scale) {
		return (Math.log(scale / 256) / Math.log(2));
	}

	// @method getProjectedBounds(zoom: Number): Bounds
	// Returns the projection's bounds scaled and transformed for the provided
	// `zoom`.
	public Envelope getProjectedBounds(float zoom) {
		if (this.infinite) {
			return null;
		}
		Envelope b = this.projection.bounds();
		double s = this.scale(zoom);
		ScenePoint min = this.transformation.transform(new ScenePoint(b.getMinX(), b.getMinY()), s);
		ScenePoint max = this.transformation.transform(new ScenePoint(b.getMaxX(), b.getMaxY()), s);
		return new Envelope(min.x, max.x, min.y, max.y);
	}

	// @method distance(latlng1: LatLng, latlng2: LatLng): Number
	// Returns the distance between two geographical coordinates.

	// @property code: String
	// Standard code name of the CRS passed into WMS services (e.g.
	// `'EPSG:3857'`)
	//
	// @property wrapLng: Number[]
	// An array of two numbers defining whether the longitude (horizontal)
	// coordinate
	// axis wraps around a given range and how. Defaults to `[-180, 180]` in
	// most
	// geographical CRSs. If `undefined`, the longitude axis does not wrap
	// around.
	//
	// @property wrapLat: Number[]
	// Like `wrapLng`, but for the latitude (vertical) axis.

	// wrapLng: [min, max],
	// wrapLat: [min, max],

	// @property infinite: Boolean
	// If true, the coordinate space will be unbounded (infinite in both axes)
	private boolean infinite = false;

	/*
	// @method wrapLatLng(latlng: LatLng): LatLng
	// Returns a `LatLng` where lat and lng has been wrapped according to the
	// CRS's `wrapLat` and `wrapLng` properties, if they are outside the CRS's bounds.
	public Latlng wrapLatLng(Latlng latlng) {
		double lng = this.wrapLng ? wrapNum(latlng.lng, this.wrapLng, true) : latlng.lng;
		double lat = this.wrapLat ? wrapNum(latlng.lat, this.wrapLat, true) : latlng.lat;
		double alt = latlng.alt;

		return L.latLng(lat, lng, alt);
	}
	
	private double wrapNum(double x, double[] range, boolean includeMax) {
		double max = range[1],
			    min = range[0],
			    d = max - min;
			return x == max && includeMax ? x : ((x - min) % d + d) % d + min;
		}
		*/
	
	public Projection getProjection() {
		return projection;
	}

	public void setProjection(Projection projection) {
		this.projection = projection;
	}

	public Transformation getTransformation() {
		return transformation;
	}

	public void setTransformation(Transformation transformation) {
		this.transformation = transformation;
	}

	public boolean isInfinite() {
		return infinite;
	}

	public void setInfinite(boolean infinite) {
		this.infinite = infinite;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
