package com.mapfinal.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;

public class GeomList<M extends Geom> implements Geom {

	protected List<M> geoms;
	protected final Object lock = new Object();

	public GeomList() {
		geoms = new ArrayList<M>();
	}
	
	public GeomList(Collection<? extends M> gs) {
		geoms = new ArrayList<M>();
		geoms.addAll(gs);
	}
	
	public GeomList(M[] gs) {
		geoms = new ArrayList<M>();
		for (int i = 0; i < gs.length; i++) {
			geoms.add(gs[i]);
		}
	}
	
	public M get(int index) {
		synchronized (lock) {
			if(geoms!=null) {
				return geoms.get(index);
			}
			return null;
		}
	}
	
	public void add(M geom) {
		synchronized (lock) {
			if(geoms!=null) {
				geoms.add(geom);
			}
		}
	}
	
	public void add(int index, M geom) {
		synchronized (lock) {
			if(geoms!=null) {
				geoms.add(index, geom);
			}
		}
	}
	
	public void set(int index, M geom) {
		synchronized (lock) {
			if(geoms!=null) {
				geoms.set(index, geom);
			}
		}
	}
	
	public List<M> getGeoms() {
		return geoms;
	}

	public void setGeoms(List<M> geoms) {
		this.geoms = geoms;
	}

	@Override
	public GeomType getGeomType() {
		// TODO Auto-generated method stub
		return GeomType.GEOMLIST;
	}

	@Override
	public Envelope getEnvelope() {
		// TODO Auto-generated method stub
		if (isEmpty()) {
		      return new Envelope();
		}
		Envelope envelope = new Envelope();
		for (M m : geoms) {
			envelope.expandToInclude(m.getEnvelope());
		}
		return envelope;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return geoms.size();
	}

	@Override
	public int getCoordinateSize() {
		// TODO Auto-generated method stub
		int s = 0;
		for (M m : geoms) {
			s+= m.getCoordinateSize();
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
		return geoms.isEmpty();
	}

	@Override
	public Geometry toGeometry() {
		// TODO Auto-generated method stub
		return null;
	}
}
