package com.mapfinal.geometry;

import java.util.Collection;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Polygon;

public class GeoMultiPolygon implements Geom {

	protected GeomList<GeoPolygon> polygons;
//	protected final Object lock = new Object();
	
	public GeoMultiPolygon(Collection<? extends GeoPolygon> gls) {
		polygons = new GeomList<GeoPolygon>(gls);
	}
	
	public GeoMultiPolygon(GeoPolygon[] gls) {
		polygons = new GeomList<GeoPolygon>(gls);
	}
	
	public GeoMultiPolygon(GeoPolygon gls) {
		polygons = new GeomList<GeoPolygon>();
		polygons.add(gls);
	}
	
	public void add(Coordinate[] coordinates, boolean allowRepeated) {
//		synchronized (lock) {
			GeoPolygon gls = new GeoPolygon(new GeoLineRing(coordinates, allowRepeated));
			if(polygons!=null) {
				polygons.add(gls);
			}
//		}
	}
	
	public void addHole(int pg_index, Coordinate[] coordinates, boolean allowRepeated) {
		if(polygons!=null) {
			if(polygons.get(pg_index)!=null) {
				polygons.get(pg_index).addHole(new GeoLineRing(coordinates, allowRepeated));
			}
		}
	}
	
	public void addPoint(int pg_index, Coordinate coordinate) {
		if(polygons!=null) {
			if(polygons.get(pg_index)!=null) {
				polygons.get(pg_index).add(coordinate);
			}
		}
	}
	
	public void addPoint(int pg_index, int pt_index, Coordinate coordinate) {
		if(polygons!=null) {
			if(polygons.get(pg_index)!=null) {
				polygons.get(pg_index).add(pt_index, coordinate);
			}
		}
	}
	
	public void addPoint(int pg_index, int pt_index, Coordinate coordinate, boolean allowRepeated) {
		if(polygons!=null) {
			if(polygons.get(pg_index)!=null) {
				polygons.get(pg_index).add(pt_index, coordinate, allowRepeated);
			}
		}
	}
	
	public void addHolePoint(int pg_index, int hole_index, Coordinate coordinate) {
		if(polygons!=null) {
			if(polygons.get(pg_index)!=null) {
				polygons.get(pg_index).addHolePoint(hole_index, coordinate);
			}
		}
	}
	
	public void addPoint(int pg_index, int hole_index, int pt_index, Coordinate coordinate) {
		if(polygons!=null) {
			if(polygons.get(pg_index)!=null) {
				polygons.get(pg_index).addHolePoint(hole_index, pt_index, coordinate);
			}
		}
	}
	
	public void addPoint(int pg_index, int hole_index, int pt_index, Coordinate coordinate, boolean allowRepeated) {
		if(polygons!=null) {
			if(polygons.get(pg_index)!=null) {
				polygons.get(pg_index).addHolePoint(hole_index, pt_index, coordinate, allowRepeated);
			}
		}
	}
	
	public void setPoint(int pg_index, int pt_index, Coordinate coordinate) {
		if(polygons!=null) {
			if(polygons.get(pg_index)!=null) {
				polygons.get(pg_index).set(pt_index, coordinate);
			}
		}
	}
	
	public void setHole(int pg_index, int hole_index, int pt_index, Coordinate coordinate) {
		if(polygons!=null) {
			if(polygons.get(pg_index)!=null) {
				polygons.get(pg_index).setHole(hole_index, pt_index, coordinate);
			}
		}
	}
	
	@Override
	public GeomType getGeomType() {
		// TODO Auto-generated method stub
		return GeomType.MULTIPOLYGON;
	}

	@Override
	public Envelope getEnvelope() {
		// TODO Auto-generated method stub
		if (isEmpty()) {
			return new Envelope();
		}
		return polygons.getEnvelope();
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return polygons.getSize();
	}

	@Override
	public int getCoordinateSize() {
		// TODO Auto-generated method stub
		return polygons.getCoordinateSize();
	}

	@Override
	public GeoPoint getCentroid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeoPoint getInteriorPoint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return polygons.isEmpty();
	}

	@Override
	public Geometry toGeometry() {
		// TODO Auto-generated method stub
		Polygon[] polygonArray = new Polygon[polygons.geoms.size()];
		for (int i = 0; i < polygons.geoms.size(); i++) {
			GeoPolygon gls = polygons.geoms.get(i);
			polygonArray[i] = (Polygon) gls.toGeometry();
		}
		return GeoKit.getGeometryFactory().createMultiPolygon(polygonArray);
	}
}
