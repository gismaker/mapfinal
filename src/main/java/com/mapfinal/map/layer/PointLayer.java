package com.mapfinal.map.layer;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.Point;

import com.mapfinal.event.Event;
import com.mapfinal.geometry.GeoKit;
import com.mapfinal.geometry.GeomType;
import com.mapfinal.geometry.MapCS;
import com.mapfinal.kit.StringKit;
import com.mapfinal.render.RenderEngine;
import com.mapfinal.render.pick.PickManager;
import com.mapfinal.render.style.MarkerSymbol;

public class PointLayer extends GeometryLayer {
	
	protected MarkerSymbol symbol;
	/**
	 * 图形类型
	 */
	protected GeomType geomType;
	
	protected boolean isEditMode = false;
	
	public PointLayer() {
		super(null);
		setName("pointLayer_" + StringKit.getUuid32());
		setEditMode(true);
	}
	
	public PointLayer(Coordinate coordinate, MarkerSymbol symbol) {
		super(null);
		createGeomtry(GeoKit.createPoint(coordinate));
		this.geomType = GeomType.MULTIPOINT;
		this.symbol = symbol;
	}
	
	@Override
	public void draw(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		if("pick".equals(event.getAction())) {
			pick(event, engine);
		} else {
			render(event, engine);
		}
	}
	
	private void render(Event event, RenderEngine engine) {
		if(geometry!=null) {
			engine.render(event, symbol, geometry);
		}
	}
	
	private void pick(Event event, RenderEngine engine) {
		if(geometry!=null) {
			int color = PickManager.me().getRenderColor(getName());
			MarkerSymbol pickSymbol = symbol==null ? MarkerSymbol.DEFAULT().getPickSymbol(color)
					: symbol.getPickSymbol(color);
			engine.render(event, pickSymbol, geometry);
		}
	}
	
	@Override
	public boolean handleEvent(Event event) {
		// TODO Auto-generated method stub
		super.handleEvent(event);
		if(geometry==null) return false;
		if(event.isAction("picked")) {
			String idName = event.get("picked_objIdName");
			if(getName().equals(idName)) {
				sendEvent("picked", event.set("picked_objHandle", this));
				return true;
			}
		}
		return false;		
	}
	
	public PointLayer(Coordinate[] coordinates, MarkerSymbol symbol) {
		super(null);
		this.geometry = GeoKit.createMultiPoint(coordinates);
		this.geomType = GeomType.MULTIPOINT;
		this.symbol = symbol;
	}
	
	protected Point getPoint(int index) {
		if(geomType == GeomType.POINT) {
			Point point = (Point) this.geometry;
			return point;
		} else if(geomType == GeomType.MULTIPOINT) {
			MultiPoint multiPoint = (MultiPoint) this.geometry;
			Geometry geo = multiPoint.getGeometryN(index);
			if(geo!=null) {
				return (Point) geo;
			}
		}
		return null;
	}
	
	protected boolean createGeomtry(Point point) {
		if(this.geometry==null) {
			this.geometry = GeoKit.createMultiPoint(new Point[]{point});
			this.geomType = GeomType.MULTIPOINT;
			return true;
		}
		return false;
	}
	
	public void addPoint(Point point) {
		if(!createGeomtry(point)) {
			MultiPoint multiPoint = (MultiPoint) this.geometry;
			int size = multiPoint.getNumGeometries() + 1;
			Point[] pts = new Point[size];
			for (int i = 0; i < multiPoint.getNumGeometries(); i++) {
			     Point pt = (Point) multiPoint.getGeometryN(i);
			     pts[i] = pt;
			}
			pts[size-1] = point;
			this.geometry = GeoKit.createMultiPoint(pts);
		}
	}
	
	public void addPoint(int pt_index, Point point) {
		if(!createGeomtry(point)) {
			MultiPoint multiPoint = (MultiPoint) this.geometry;
			int size = multiPoint.getNumGeometries() + 1;
			int t = 0;
			Point[] pts = new Point[size];
			for (int i = 0; i < size; i++) {
				if(pt_index==i) {
					pts[i] = point;
					t=1;
				} else {
					Point pt = (Point) multiPoint.getGeometryN(i-t);
				    pts[i] = pt;
				}
			}
			this.geometry = GeoKit.createMultiPoint(pts);
		}
	}
	
	public void addPoint(Coordinate coordinate) {
		Point point = GeoKit.createPoint(coordinate);
		addPoint(point);
	}
	
	public void addPoint(int pt_index, Coordinate coordinate) {
		Point point = GeoKit.createPoint(coordinate);
		addPoint(pt_index, point);
	}
	
	public void setPoint(int pt_index, Coordinate coordinate) {
		Point point = getPoint(pt_index);
		if(point!=null) {
			MapCS mapcs = (MapCS) point.getCoordinateSequence();
			mapcs.setCoordinate(0, coordinate);
		}
	}
	
	public void removePoint(int pt_index) {
		if(this.geometry!=null && pt_index < this.geometry.getNumGeometries()) {
			MultiPoint multiPoint = (MultiPoint) this.geometry;
			int nums = multiPoint.getNumGeometries();
			int size = nums - 1;
			int t = 0;
			Point[] pts = new Point[size];
			for (int i = 0; i < nums; i++) {
				if(pt_index==i) {
					t=1;
				} else {
					Point pt = (Point) multiPoint.getGeometryN(i);
				    pts[i-t] = pt;
				}
			}
			this.geometry = GeoKit.createMultiPoint(pts);
		}
	}

	public MarkerSymbol getSymbol() {
		return symbol;
	}

	public void setSymbol(MarkerSymbol symbol) {
		this.symbol = symbol;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public boolean isEditMode() {
		return isEditMode;
	}

	public void setEditMode(boolean isEditMode) {
		this.isEditMode = isEditMode;
	}
}
