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
import com.mapfinal.render.style.FillSymbol;

public class PolygonLayer extends GeometryLayer {
	
	protected FillSymbol symbol;
	/**
	 * 图形类型
	 */
	protected GeomType geomType;
	
	protected boolean isEditMode = false;
	
	public PolygonLayer(Coordinate[] coordinates, FillSymbol symbol) {
		super(GeoKit.createLine(coordinates));
		this.geomType = GeomType.LINESTRING;
		this.symbol = symbol;
		setEditMode(true);
	}
	
	@Override
	public void draw(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		if(geometry!=null) {
			engine.render(event, symbol, geometry);
		}
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
				mapcs.addCoordinate(mapcs.size()-1, coordinate);
			}
		}
	}
	
	public void addCoordinate(int polygon_index, Coordinate coordinate) {
		if(!createGeomtry(coordinate) && polygon_index < this.geometry.getNumGeometries()) {
			MapCS mapcs = getMapCS(polygon_index);
			if(mapcs!=null) {
				mapcs.addCoordinate(mapcs.size()-2, coordinate);
			}
		}
	}
	
	public void addCoordinate(int polygon_index, int pt_index, Coordinate coordinate) {
		if(!createGeomtry(coordinate) && polygon_index < this.geometry.getNumGeometries()) {
			MapCS mapcs = getMapCS(polygon_index);
			if(mapcs!=null) {
				mapcs.addCoordinate(pt_index, coordinate);
			}
		}
	}
	
	public void setCoordinate(int polygon_index, int pt_index, Coordinate coordinate) {
		if(!createGeomtry(coordinate) && polygon_index < this.geometry.getNumGeometries()) {
			MapCS mapcs = getMapCS(polygon_index);
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

	public FillSymbol getSymbol() {
		return symbol;
	}

	public void setSymbol(FillSymbol symbol) {
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
