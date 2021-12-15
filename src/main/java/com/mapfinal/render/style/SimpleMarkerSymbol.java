package com.mapfinal.render.style;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mapfinal.Mapfinal;

public class SimpleMarkerSymbol extends MarkerSymbol {

	private SimpleFillSymbol fill;
	private MarkerSymbol.STYLE style = STYLE.CIRCLE;

	public SimpleMarkerSymbol(int color) {
		// TODO Auto-generated constructor stub
		this.fill = new SimpleFillSymbol(color);
	}
	
	public SimpleMarkerSymbol(SimpleFillSymbol fill) {
		// TODO Auto-generated constructor stub
		this.fill = fill;
	}
	
	public SimpleMarkerSymbol(SimpleMarkerSymbol symbol) {
		// TODO Auto-generated constructor stub
		super(symbol);
		if(symbol!=null) {
			this.fill = new SimpleFillSymbol(symbol.getFill());
			this.style = symbol.getStyle();
		}
	}
	
	public SimpleMarkerSymbol(SimpleFillSymbol fill, float width, float height) {
		// TODO Auto-generated constructor stub
		this.fill = fill;
		setWidth(width);
		setHeight(height);
	}

	@Override
	public void fromJson(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JSONObject toJson() {
		// TODO Auto-generated method stub
		return (JSONObject) JSON.toJSON(this);
	}

	public SimpleFillSymbol getFill() {
		return fill;
	}

	public void setFill(SimpleFillSymbol fill) {
		this.fill = fill;
	}

	public MarkerSymbol.STYLE getStyle() {
		return style;
	}

	public void setStyle(MarkerSymbol.STYLE style) {
		this.style = style;
	}

	@Override
	public MarkerSymbol getPickSymbol(int color) {
		// TODO Auto-generated method stub
		SimpleMarkerSymbol symbol = new SimpleMarkerSymbol(this);
		symbol.getFill().setColor(color);
		symbol.setWidth(this.getWidth() + Mapfinal.platform().pickPointZommPixel());
		symbol.setHeight(this.getHeight() + Mapfinal.platform().pickPointZommPixel());
		if(symbol.getFill().getOutline()!=null) {
			symbol.getFill().getOutline().setColor(color);
		}
		return symbol;
	}
}
