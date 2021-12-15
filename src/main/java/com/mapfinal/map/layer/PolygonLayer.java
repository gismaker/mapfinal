package com.mapfinal.map.layer;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.ParseException;

import com.mapfinal.event.Event;
import com.mapfinal.geometry.GeoKit;
import com.mapfinal.geometry.GeomType;
import com.mapfinal.geometry.MapCS;
import com.mapfinal.render.RenderEngine;
import com.mapfinal.render.pick.PickManager;
import com.mapfinal.render.style.FillSymbol;

public class PolygonLayer extends GeometryLayer {
	
	protected FillSymbol symbol;
	/**
	 * 图形类型
	 */
	protected GeomType geomType;
	
	protected boolean isEditMode = false;
	
	public PolygonLayer(Coordinate[] coordinates, FillSymbol symbol) {
		super(GeoKit.createPolygon(coordinates));
		this.geomType = GeomType.POLYGON;
		this.symbol = symbol;
		setEditMode(true);
	}
	
	public PolygonLayer(String wkt, FillSymbol symbol) throws ParseException {
		super(GeoKit.createPolygonByWKT(wkt));
		this.geomType = GeomType.POLYGON;
		this.symbol = symbol;
		setEditMode(true);
	} 
	
	@Override
	public void draw(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		if(!isDrawable()) return;
		if(!isVisible()) return;
		if(event.isRender()) {
			render(event, engine);
		} else if("pick".equals(event.getAction())) {
			pick(event, engine);
		}
	}
	
	private void render(Event event, RenderEngine engine) {
		if(geometry!=null) {
			engine.render(event, symbol, geometry);
		}
	}
	
	public void pick(Event event, RenderEngine engine) {
		if(geometry!=null) {
			//MapContext context = event.get("map");
			//System.out.println("PolygonLayer: context: " + context.isMainThread() + "," + context.getCenter());
			int color = PickManager.me().getRenderColor(getName());
			//System.out.println("PolygonLayer: color: " + color);
			FillSymbol pickSymbol = symbol==null ? FillSymbol.DEFAULT().getPickSymbol(color)
					: symbol.getPickSymbol(color);
			engine.render(event, pickSymbol, geometry);
		}
	}
	
	@Override
	public boolean handleEvent(Event event) {
		// TODO Auto-generated method stub
		if(geometry==null) return false;
		if(event.isAction("picked")) {
			String idName = event.get("picked_name");
			if(getName().equals(idName)) {
				sendEvent(getEventAction("click"), event.set("picked_object", this));
				return true;
			}
		}
		super.handleEvent(event);
		return false;		
	}
	
	protected MapCS getMapCS(int index) {
		Polygon lineString = getLine(index);
		return lineString==null ? null : (MapCS) lineString.getExteriorRing().getCoordinateSequence();
	}
	
	protected Polygon getLine(int index) {
		if(geomType == GeomType.POLYGON) {
			Polygon p = (Polygon) this.geometry;
			return p;
		} else if(geomType == GeomType.MULTIPOLYGON) {
			MultiPolygon multiPolygon = (MultiPolygon) this.geometry;
			Geometry geo = multiPolygon.getGeometryN(index);
			if(geo!=null) {
				return (Polygon) geo;
			}
		}
		return null;
	}
	
	protected boolean createGeomtry(Coordinate coordinate) {
		if(this.geometry==null) {
			Polygon p = GeoKit.createPolygon(new Coordinate[]{coordinate});
			this.geometry = GeoKit.createMultiPolygon(new Polygon[]{p});
			this.geomType = GeomType.MULTIPOLYGON;
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
