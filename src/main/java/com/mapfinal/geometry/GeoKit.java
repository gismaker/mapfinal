package com.mapfinal.geometry;

import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequenceFactory;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.util.GeometricShapeFactory;

import com.mapfinal.converter.scene.ScenePoint;
import com.mapfinal.kit.StringKit;

public class GeoKit {

	private static GeometryFactory geometryFactory;
	private static boolean geometryFactoryCreated = false;

	public static void initGeometryFactory() {
		if (geometryFactoryCreated)
			return;
		geometryFactoryCreated = true;
		geometryFactory = new GeometryFactory();
	}

	public static void initGeometryFactory(PrecisionModel precisionModel, int SRID,
			CoordinateSequenceFactory coordinateSequenceFactory) {
		if (geometryFactoryCreated)
			return;
		geometryFactoryCreated = true;
		geometryFactory = new GeometryFactory(precisionModel, SRID, coordinateSequenceFactory);
	}

	public static void initGeometryFactory(CoordinateSequenceFactory coordinateSequenceFactory) {
		if (geometryFactoryCreated)
			return;
		geometryFactoryCreated = true;
		geometryFactory = new GeometryFactory(coordinateSequenceFactory);
	}

	public static GeometryFactory getGeometryFactory() {
		return geometryFactory;
	}
	
	public static boolean hasNullElements(Object[] array) {
	    for (int i = 0; i < array.length; i++) {
	      if (array[i] == null) {
	        return true;
	      }
	    }
	    return false;
	  }

	/**
	 * 创建一个圆
	 * 
	 * @param x
	 * @param y
	 * @param RADIUS
	 * @return
	 */
	public static Geometry createCircle(double x, double y, final double RADIUS) {
		GeometricShapeFactory shapeFactory = new GeometricShapeFactory();
		shapeFactory.setNumPoints(32);
		shapeFactory.setCentre(new Coordinate(x, y));
		shapeFactory.setSize(RADIUS * 2);
		return shapeFactory.createCircle();
	}

	/**
	 * As meaning of method name. 获得两点之间的距离
	 *
	 * @param p0
	 * @param p1
	 * @return
	 */
	public static float getDistanceBetween2Points(Coordinate p0, Coordinate p1) {
		float distance = (float) Math.sqrt(Math.pow(p0.y - p1.y, 2) + Math.pow(p0.x - p1.x, 2));
		return distance;
	}

	/**
	 * Get middle point between p1 and p2. 获得两点连线的中点
	 *
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static Coordinate getMiddlePoint(Coordinate p1, Coordinate p2) {
		return new Coordinate((p1.x + p2.x) / 2.0f, (p1.y + p2.y) / 2.0f);
	}

	/**
	 * Get point between p1 and p2 by percent. 根据百分比获取两点之间的某个点坐标
	 *
	 * @param p1
	 * @param p2
	 * @param percent
	 * @return
	 */
	public static Coordinate getPointByPercent(Coordinate p1, Coordinate p2, float percent) {
		return new Coordinate(evaluateValue(percent, p1.x, p2.x), evaluateValue(percent, p1.y, p2.y));
	}

	/**
	 * 根据分度值，计算从start到end中，fraction位置的值。fraction范围为0 -> 1
	 *
	 * @param fraction
	 * @param start
	 * @param end
	 * @return
	 */
	public static float evaluateValue(float fraction, Number start, Number end) {
		return start.floatValue() + (end.floatValue() - start.floatValue()) * fraction;
	}

	/**
	 * Get the point of intersection between circle and line. 获取
	 * 通过指定圆心，斜率为lineK的直线与圆的交点。
	 *
	 * @param pMiddle
	 *            The circle center point.
	 * @param radius
	 *            The circle radius.
	 * @param lineK
	 *            The slope of line which cross the pMiddle.
	 * @return
	 */
	public static Coordinate[] getIntersectionPoints(Coordinate pMiddle, float radius, Double lineK) {
		Coordinate[] points = new Coordinate[2];
		float radian, xOffset = 0, yOffset = 0;
		if (lineK != null) {
			radian = (float) Math.atan(lineK);
			xOffset = (float) (Math.sin(radian) * radius);
			yOffset = (float) (Math.cos(radian) * radius);
		} else {
			xOffset = radius;
			yOffset = 0;
		}
		points[0] = new Coordinate(pMiddle.x + xOffset, pMiddle.y - yOffset);
		points[1] = new Coordinate(pMiddle.x - xOffset, pMiddle.y + yOffset);
		return points;
	}

	/**
	 * create a rectangle(矩形)
	 * 
	 * @return
	 */
	public static Envelope createEnvelope(double x1, double x2, double y1, double y2) {
		Envelope envelope = new Envelope(x1, x2, y1, y2);
		return envelope;
	}

	/**
	 * create a Point
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static Coordinate point(double x, double y) {
		return new Coordinate(x, y);
	}

	/**
	 * create a Point
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static Latlng latlng(double lat, double lng) {
		return new Latlng(lat, lng);
	}

	/**
	 * create a Point
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static ScenePoint scenePoint(double x, double y) {
		return new ScenePoint(x, y);
	}

	/**
	 * create a point
	 * 
	 * @return
	 */
	public static Point createPoint(Coordinate coord) {
		Point point = geometryFactory.createPoint(coord);
		return point;
	}

	/**
	 * create a point by WKT
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Point createPointByWKT(String wkt) throws ParseException {
		if (StringKit.isBlank(wkt) || !wkt.startsWith("POINT"))
			return null;
		WKTReader reader = new WKTReader(geometryFactory);
		Point point = (Point) reader.read(wkt);
		return point;
	}

	/**
	 * create multiPoint by wkt
	 * 
	 * @return
	 */
	public static MultiPoint createMultiPointByWKT(String wkt) throws ParseException {
		if (StringKit.isBlank(wkt) || !wkt.startsWith("MULTIPOINT"))
			return null;
		WKTReader reader = new WKTReader(geometryFactory);
		MultiPoint mpoint = (MultiPoint) reader.read(wkt);
		return mpoint;
	}
	
	public static MultiPoint createMultiPoint(Point[] points) {
		MultiPoint ms = geometryFactory.createMultiPoint(points);
		return ms;
	}
	
	public static MultiPoint createMultiPoint(Coordinate[] coords) {
		MultiPoint ms = geometryFactory.createMultiPointFromCoords(coords);
		return ms;
	}

	/**
	 *
	 * create a line
	 * 
	 * @return
	 */
	public static LineString createLine(Coordinate[] coords) {
		LineString line = geometryFactory.createLineString(coords);
		return line;
	}

	public static LineString createLine(double a, double b, double c, double d) {
		Coordinate[] coords = new Coordinate[] { new Coordinate(a, b), new Coordinate(c, d) };
		LineString line = geometryFactory.createLineString(coords);
		return line;
	}

	/**
	 * create a line
	 * 
	 * @return
	 */
	public static LineString createLine(List<Coordinate> points) {
		Coordinate[] coords = (Coordinate[]) points.toArray(new Coordinate[points.size()]);
		LineString line = geometryFactory.createLineString(coords);
		return line;
	}

	/**
	 * create a line by WKT
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static LineString createLineByWKT(String wkt) throws ParseException {
		if (StringKit.isBlank(wkt) || !wkt.startsWith("LINESTRING"))
			return null;
		WKTReader reader = new WKTReader(geometryFactory);
		LineString line = (LineString) reader.read(wkt);
		return line;
	}

	/**
	 * create multiLine
	 * 
	 * @return
	 */
	public static MultiLineString createMultiLine(LineString[] lineStrings) {
		MultiLineString ms = geometryFactory.createMultiLineString(lineStrings);
		return ms;
	}

	/**
	 * create multiLine by WKT
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static MultiLineString createMultiLineByWKT(String wkt) throws ParseException {
		if (StringKit.isBlank(wkt) || !wkt.startsWith("MULTILINESTRING"))
			return null;
		WKTReader reader = new WKTReader(geometryFactory);
		MultiLineString line = (MultiLineString) reader.read(wkt);
		return line;
	}

	/**
	 * create a polygon(多边形) by WKT
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Polygon createPolygonByWKT(String wkt) throws ParseException {
		if (StringKit.isBlank(wkt) || !wkt.startsWith("POLYGON"))
			return null;
		WKTReader reader = new WKTReader(geometryFactory);
		Polygon polygon = (Polygon) reader.read(wkt);
		return polygon;
	}
	
	
	public static Polygon createPolygon(Coordinate[] coords) {
		Polygon polygon = geometryFactory.createPolygon(coords);
		return polygon;
	}
	
	public static MultiPolygon createMultiPolygon(Polygon[] polygons) {
		MultiPolygon multiPolygon = geometryFactory.createMultiPolygon(polygons);
		return multiPolygon;
	}

	/**
	 * create multi polygon by wkt
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static MultiPolygon createMultiPolygonByWKT(String wkt) throws ParseException {
		if (StringKit.isBlank(wkt) || !wkt.startsWith("MULTIPOLYGON"))
			return null;
		WKTReader reader = new WKTReader(geometryFactory);
		MultiPolygon mpolygon = (MultiPolygon) reader.read(wkt);
		return mpolygon;
	}
	
	public static Geometry readWKT(String wkt) throws ParseException {
		if (StringKit.isBlank(wkt))
			return null;
		WKTReader reader = new WKTReader(geometryFactory);
		return reader.read(wkt);
	}

	public static Geometry parseWKT(String wkt) {
		if (StringKit.isBlank(wkt))
			return null;
		WKTReader reader = new WKTReader(geometryFactory);
		try {
			return reader.read(wkt);
		} catch (ParseException e) {
			throw new RuntimeException("parseWKT Error", e);
		}
	}

	/**
	 * create GeometryCollection contain point or multiPoint or line or
	 * multiLine or polygon or multiPolygon
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static GeometryCollection createGeometryCollect(Geometry[] geometries) {
		return geometryFactory.createGeometryCollection(geometries);
	}

	/**
	 * 返回(A)与(B)中距离最近的两个点的距离
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static double distance(Geometry a, Geometry b) {
		return a.distance(b);
	}
	
	private static double R = 6371000;
	private static double rad = Math.PI / 180;
	
	public static double distance(Latlng latlng1, Latlng latlng2) {
		double lat1 = latlng1.lat() * rad;
		double lat2 = latlng2.lat() * rad;
		double a = Math.sin(lat1) * Math.sin(lat2)
				+ Math.cos(lat1) * Math.cos(lat2) * Math.cos((latlng2.lng() - latlng1.lng()) * rad);
		return R * Math.acos(Math.min(a, 1));
	}
	
	/**
	 * 两个几何对象的交集
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static Geometry intersection(Geometry a, Geometry b) {
		return a.intersection(b);
	}

	/**
	 * 几何对象合并
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static Geometry union(Geometry a, Geometry b) {
		return a.union(b);
	}

	/**
	 * 在A几何对象中有的，但是B几何对象中没有
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static Geometry difference(Geometry a, Geometry b) {
		return a.difference(b);
	}
}
