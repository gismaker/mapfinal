package com.mapfinal.render.style;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class SimpleFillSymbol extends FillSymbol {

	/**
	 * 绘制方式
	 */
	public enum STYLE {
		/**
		 * 右上方向斜线填充
		 */
		BACKWARD_DIAGONAL,
		/**
		 * 网格填充
		 */
		CROSS,
		/**
		 * 斜向网格填充
		 */
		DIAGONAL_CROSS,
		/**
		 * 左上方向斜线填充
		 */
		FORWARD_DIAGONAL,
		/**
		 * 横线填充
		 */
		HORIZONTAL,
		/**
		 * 未知
		 */
		NULL,
		/**
		 * 颜色填充
		 */
		SOLID,
		/**
		 * 竖线填充
		 */
		VERTICAL
	}

	private int alpha = 255;
	private int color = 0;
	private SimpleFillSymbol.STYLE style = STYLE.SOLID;
	private LineSymbol outline;

	public SimpleFillSymbol(int color) {
		// TODO Auto-generated constructor stub
		this.color = color;
	}

	public SimpleFillSymbol(int color, SimpleFillSymbol.STYLE style) {
		// TODO Auto-generated constructor stub
		this.color = color;
		this.style = style;
	}

	public SimpleFillSymbol(SimpleFillSymbol symbol) {
		// TODO Auto-generated constructor stub
		this.alpha = symbol.getAlpha();
		this.color = symbol.getColor();
		this.style = symbol.style;
		this.outline = symbol.outline;
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
	public LineSymbol getOutline() {
		// TODO Auto-generated method stub
		return outline;
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
	public void setOutline(LineSymbol outline) {
		// TODO Auto-generated method stub
		this.outline = outline;
	}

	public SimpleFillSymbol.STYLE getStyle() {
		return style;
	}

	public void setStyle(SimpleFillSymbol.STYLE style) {
		this.style = style;
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

	@Override
	public FillSymbol getPickSymbol(int color) {
		// TODO Auto-generated method stub
		SimpleFillSymbol symbol = new SimpleFillSymbol(this);
		symbol.setColor(color);
		if(symbol.getOutline()!=null) {
			symbol.getOutline().setColor(color);
			symbol.getOutline().setWidth(symbol.getOutline().getWidth() + 5);
		}
		return symbol;
	}
}
