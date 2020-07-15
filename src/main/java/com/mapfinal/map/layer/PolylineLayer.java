package com.mapfinal.map.layer;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiLineString;

import com.mapfinal.event.Event;
import com.mapfinal.geometry.GeoKit;
import com.mapfinal.geometry.GeomType;
import com.mapfinal.geometry.MapCS;
import com.mapfinal.render.RenderEngine;
import com.mapfinal.render.pick.PickManager;
import com.mapfinal.render.style.LineSymbol;
import com.mapfinal.render.style.SimpleLineSymbol;

public class PolylineLayer extends GeometryLayer {
	
	protected SimpleLineSymbol symbol;
	/**
	 * 图形类型
	 */
	protected GeomType geomType;
	
	protected boolean isEditMode = false;
	
	public PolylineLayer(Coordinate[] coordinates, SimpleLineSymbol symbol) {
		super(GeoKit.createLine(coordinates));
		this.geomType = GeomType.LINESTRING;
		this.symbol = symbol;
		setEditMode(true);
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
			//System.out.println("PolygonLayer: color: " + color);
			LineSymbol pickSymbol = symbol==null ? LineSymbol.DEFAULT().getPickSymbol(color)
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
			String idName = event.get("picked_name");
			if(getName().equals(idName)) {
				sendEvent(getEventAction("click"), event.set("picked_object", this));
				return true;
			}
		}
		return false;		
	}
	
	protected MapCS getMapCS(int index) {
		LineString lineString = getLine(index);
		return lineString==null ? null : (MapCS) lineString.getCoordinateSequence();
	}
	
	protected LineString getLine(int index) {
		if(geomType == GeomType.LINESTRING) {
			LineString lineString = (LineString) this.geometry;
			return lineString;
		} else if(geomType == GeomType.MULTILINESTRING) {
			MultiLineString multiLineString = (MultiLineString) this.geometry;
			Geometry geo = multiLineString.getGeometryN(index);
			if(geo!=null) {
				return (LineString) geo;
			}
		}
		return null;
	}
	
	protected boolean createGeomtry(Coordinate coordinate) {
		if(this.geometry==null) {
			LineString line = GeoKit.createLine(new Coordinate[]{coordinate});
			this.geometry = GeoKit.createMultiLine(new LineString[]{line});
			this.geomType = GeomType.MULTILINESTRING;
			return true;
		}
		return false;
	}
	
	public void addCoordinate(Coordinate coordinate) {
		if(!createGeomtry(coordinate)) {
			MapCS mapcs = getMapCS(this.geometry.getNumGeometries()-1);
			if(mapcs!=null) {
				mapcs.addCoordinate(coordinate);
			}
		}
	}
	
	public void addCoordinate(int line_index, Coordinate coordinate) {
		if(!createGeomtry(coordinate) && line_index < this.geometry.getNumGeometries()) {
			MapCS mapcs = getMapCS(line_index);
			if(mapcs!=null) {
				mapcs.addCoordinate(coordinate);
			}
		}
	}
	
	public void addCoordinate(int line_index, int pt_index, Coordinate coordinate) {
		if(!createGeomtry(coordinate) && line_index < this.geometry.getNumGeometries()) {
			MapCS mapcs = getMapCS(line_index);
			if(mapcs!=null) {
				mapcs.addCoordinate(pt_index, coordinate);
			}
		}
	}
	
	public void setCoordinate(int line_index, int pt_index, Coordinate coordinate) {
		if(!createGeomtry(coordinate) && line_index < this.geometry.getNumGeometries()) {
			MapCS mapcs = getMapCS(line_index);
			if(mapcs!=null) {
				mapcs.setCoordinate(pt_index, coordinate);
			}
		}
	}
	
	public void removeCoordinate(int line_index, int pt_index) {
		if(this.geometry!=null && line_index < this.geometry.getNumGeometries()) {
			MapCS mapcs = getMapCS(line_index);
			if(mapcs!=null) {
				mapcs.removeCoordinate(pt_index);
			}
		}
	}

	public SimpleLineSymbol getSymbol() {
		return symbol;
	}

	public void setSymbol(SimpleLineSymbol symbol) {
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
