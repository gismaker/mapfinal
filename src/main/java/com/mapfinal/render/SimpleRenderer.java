package com.mapfinal.render;

import java.util.Map;

import org.locationtech.jts.geom.Geometry;

import com.mapfinal.event.Event;
import com.mapfinal.geometry.GeomType;
import com.mapfinal.map.Feature;
import com.mapfinal.render.style.SimpleFillSymbol;
import com.mapfinal.render.style.SimpleLineSymbol;
import com.mapfinal.render.style.SimpleMarkerSymbol;
import com.mapfinal.render.style.Symbol;

public class SimpleRenderer implements Renderer {

	private Symbol symbol;
	private boolean pickMode = false;
	private int pickColor = -1;
//	private Renderable renderable;
	
	public SimpleRenderer(Symbol symbol) {
		this.setSymbol(symbol);
	}
	
//	public SimpleRenderer(Symbol symbol, Renderable renderable) {
//		this.setSymbol(symbol);
//		this.setRenderable(renderable);
//	}
	
	public SimpleRenderer(int color, boolean pickMode) {
		this.pickMode = pickMode;
		this.pickColor = color;
	}
	
	@Override
	public void setPickMode(int color, boolean pickMode) {
		// TODO Auto-generated method stub
		this.pickColor = color;
		this.pickMode = pickMode;
	}

	public Symbol getSymbol() {
		return symbol;
	}

	public void setSymbol(Symbol symbol) {
		this.symbol = symbol;
	}

	@Override
	public void draw(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
//		if(renderable!=null) renderable.draw(event, engine, this);
		
	}

	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
//		if(renderable!=null) renderable.handleEvent(event);
	}

	@Override
	public Symbol getSymbol(Feature feature) {
		// TODO Auto-generated method stub
		if(pickMode) {
			if(feature==null || feature.getGeometry()==null) return null;
			//
			if(getSymbol()!=null) {
				return getSymbol().getPickSymbol(pickColor);
			}
			//
			String gtype = feature.getGeometry().getGeometryType();
			GeomType type = GeomType.valueOf(gtype);
			if(type == GeomType.MULTIPOLYGON || type==GeomType.POLYGON) {
				return SimpleFillSymbol.create(pickColor, pickColor);
			} else if(type == GeomType.MULTILINESTRING || type==GeomType.LINERING
					|| type == GeomType.LINESEGMENT || type==GeomType.LINESTRING) {
				return SimpleLineSymbol.create(pickColor);
			} else if(type == GeomType.MULTIPOINT || type==GeomType.POINT) {
				return SimpleMarkerSymbol.create(pickColor);
			}
			return SimpleFillSymbol.create(pickColor, pickColor);
		}
		return getSymbol();
	}

	@Override
	public Symbol getSymbol(Geometry geometry, Map<String, Object> attributes) {
		// TODO Auto-generated method stub
		return getSymbol();
	}
}
