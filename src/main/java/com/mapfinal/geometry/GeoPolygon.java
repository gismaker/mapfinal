package com.mapfinal.geometry;

import java.util.List;

import org.locationtech.jts.geom.Coordinate;

public class GeoPolygon implements Geom {

	private GeoLineRing shell = null;
	protected List<GeoLineRing> holes;
	protected final Object lock = new Object();

	public GeoPolygon(GeoLineRing shell) {
		this.setShell(shell);
	}

	public GeoPolygon(GeoLineRing shell, List<GeoLineRing> holes) {
		this.setShell(shell);
		this.holes = holes;
	}
	
	public void add(Coordinate coordinate) {
		shell.add(coordinate);
	}
	
	public void add(int pt_index, Coordinate coordinate) {
		shell.add(pt_index, coordinate);
	}
	
	public void add(int pt_index, Coordinate coordinate, boolean allowRepeated) {
		shell.add(pt_index, coordinate, allowRepeated);
	}
	
	public void addHolePoint(int hole_index, Coordinate coordinate) {
		synchronized (lock) {
			GeoLineRing hole = holes.get(hole_index);
			if(hole!=null) {
				hole.add(coordinate);
			}
		}
	}
	
	public void addHolePoint(int hole_index, int pt_index, Coordinate coordinate) {
		synchronized (lock) {
			GeoLineRing hole = holes.get(hole_index);
			if(hole!=null) {
				hole.add(pt_index, coordinate);
			}
		}
	}
	
	public void addHolePoint(int hole_index, int pt_index, Coordinate coordinate, boolean allowRepeated) {
		synchronized (lock) {
			GeoLineRing hole = holes.get(hole_index);
			if(hole!=null) {
				hole.add(pt_index, coordinate, allowRepeated);
			}
		}
	}
	
	public void set(int index, Coordinate coordinate) {
		shell.set(index, coordinate);
	}
	
	public void setHole(int hole_index, int pt_index, Coordinate coordinate) {
		synchronized (lock) {
			GeoLineRing hole = holes.get(hole_index);
			if(hole!=null) {
				hole.set(pt_index, coordinate);
			}
		}
	}
	
	public void addHole(GeoLineRing hole) {
		synchronized (lock) {
			if(holes!=null) {
				holes.add(hole);
			}
		}
	}

	@Override
	public GeomType getGeomType() {
		// TODO Auto-generated method stub
		return GeomType.POLYGON;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getCoordinateSize() {
		// TODO Auto-generated method stub
		int s = shell.getCoordinateSize();
		for (GeoLineRing geoLineRing : holes) {
			s+=geoLineRing.getCoordinateSize();
		}
		return s;
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
		return shell.isEmpty();
	}

	public GeoLineRing getShell() {
		return shell;
	}

	public void setShell(GeoLineRing shell) {
		this.shell = shell;
	}

	public List<GeoLineRing> getHoles() {
		return holes;
	}

	public void setHoles(List<GeoLineRing> holes) {
		this.holes = holes;
	}

}
