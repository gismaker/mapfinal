package com.mapfinal.render.style;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class SimpleLineSymbol extends LineSymbol {

	public enum STYLE {
		/**
		 * 虚线
		 */
		DASH,
		/**
		 * 点划线
		 */
		DASHDOT,
		/**
		 * 双点划线
		 */
		DASHDOTDOT,
		/**
		 * 点线
		 */
		DOT,
		/**
		 * 未知类型
		 */
		NULL,
		/**
		 * 实线
		 */
		SOLID
	}

	private int alpha = 255;
	private int color = 0;
	private SimpleLineSymbol.STYLE style = STYLE.SOLID;
	private SimpleLineSymbol outline;
	private float width = 1.0f;

	public SimpleLineSymbol(int color) {
		this.color = color;
	}
	
	public SimpleLineSymbol(int color, float width) {
		this.color = color;
		this.width = width;
	}

	public SimpleLineSymbol(int color, float width, SimpleLineSymbol.STYLE style) {
		this.color = color;
		this.width = width;
		this.style = style;
	}

	public SimpleLineSymbol(SimpleLineSymbol symbol) {
		this.color = symbol.getColor();
		this.width = symbol.getWidth();
		this.style = symbol.getStyle();
		this.outline = symbol.getOutline();
		this.alpha = symbol.alpha;
	}
	
	@Override
	public int getAlpha() {
		// TODO Auto-generated method stub
		return alpha;
	}

	@Override
	public int getColor() {
		// TODO Auto-generated method stub
		return color;
	}

	@Override
	public void setAlpha(int alpha) {
		// TODO Auto-generated method stub
		this.alpha = alpha;
	}

	@Override
	public void setColor(int color) {
		// TODO Auto-generated method stub
		this.color = color;
	}

	@Override
	public float getWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	public void setWidth(float width) {
		// TODO Auto-generated method stub
		this.width = width;
	}

	public SimpleLineSymbol getOutline() {
		return outline;
	}

	/**
	 * 获取线样式的类型
	 * 
	 * @return
	 */
	public SimpleLineSymbol.STYLE getStyle() {
		return style;
	}

	public void setOutline(SimpleLineSymbol outline) {
		this.outline = outline;
	}

	/**
	 * 设置线样式的类型
	 * 
	 * @param style
	 */
	public void setStyle(SimpleLineSymbol.STYLE style) {
		this.style = style;
	}

	@Override
	public void fromJson(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		alpha = jsonObject.getIntValue("alpha");
		color = jsonObject.getIntValue("color");
		style = jsonObject.getObject("style", SimpleLineSymbol.STYLE.class);
		outline = jsonObject.getObject("outline", SimpleLineSymbol.class);
		width = jsonObject.getFloatValue("width");
	}

	@Override
	public JSONObject toJson() {
		// TODO Auto-generated method stub
		return (JSONObject) JSON.toJSON(this);
	}

}
