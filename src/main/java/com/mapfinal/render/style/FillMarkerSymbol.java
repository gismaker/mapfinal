package com.mapfinal.render.style;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class FillMarkerSymbol extends MarkerSymbol {

	private FillSymbol fill;
	private MarkerSymbol.STYLE style = STYLE.CIRCLE;

	public FillMarkerSymbol(int color) {
		// TODO Auto-generated constructor stub
		this.fill = new SimpleFillSymbol(color);
	}
	
	public FillMarkerSymbol(FillSymbol fill) {
		// TODO Auto-generated constructor stub
		this.fill = fill;
	}
	
	public FillMarkerSymbol(FillSymbol fill, float width, float height) {
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

	public FillSymbol getFill() {
		return fill;
	}

	public void setFill(FillSymbol fill) {
		this.fill = fill;
	}

	public MarkerSymbol.STYLE getStyle() {
		return style;
	}

	public void setStyle(MarkerSymbol.STYLE style) {
		this.style = style;
	}
}
