package com.mapfinal.map;

import java.util.Map;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;

import com.mapfinal.geometry.GeoKit;
import com.mapfinal.geometry.GeomType;
import com.mapfinal.geometry.MapCS;

public class FeaturePoint<K> extends Feature<K> {

	public FeaturePoint(Coordinate coordinate) {
		this.geometry = GeoKit.createPoint(coordinate);
		this.envelope = geometry != null ? geometry.getEnvelopeInternal() : envelope;
	}
	
	public FeaturePoint(K id, Geometry geometry) {
		this.id = id;
		if(geometry!=null && geometry.getGeometryType().equals(GeomType.POINT.getName())) {
			this.geometry = geometry;
			this.envelope = geometry != null ? geometry.getEnvelopeInternal() : envelope;
		}
	}

	public FeaturePoint(Geometry geometry, Map<String, Object> attributes) {
		if(geometry!=null && geometry.getGeometryType().equals(GeomType.POINT.getName())) {
			this.geometry = geometry;
			this.envelope = geometry != null ? geometry.getEnvelopeInternal() : envelope;
		}
		this.attributes = attributes;
	}
	
	protected Point getPoint() {
		if(this.geometry!=null && this.geometry.getGeometryType().equals(GeomType.POINT.getName())) {
			Point point = (Point) this.geometry;
			return point;
		}
		return null;
	}
	
	public void setPoint(Coordinate coordinate) {
		Point point = getPoint();
		if(point!=null) {
			MapCS mapcs = (MapCS) point.getCoordinateSequence();
			mapcs.setCoordinate(0, coordinate);
		}
	}
	
}
