package com.mapfinal.render.style;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mapfinal.kit.ColorKit;

public abstract class MarkerSymbol implements Symbol {
	/**
	 * 绘制方式
	 */
	public enum STYLE {
		/**
		 * 圆形
		 */
		CIRCLE,
		/**
		 * 矩形
		 */
		RECT,
		/**
		 * 椭圆
		 */
		OVAL,
		/**
		 * 图标
		 */
		ICON,
		/**
		 * 未知
		 */
		NULL
	}
	/**
	 * 标注样式角度
	 */
	private float angle = 0;
	/**
	 * 标注样式宽度
	 */
	private float width = 5;
	/**
	 * 标注样式高度
	 */
	private float height = 5;
	/**
	 * 标注样式X方向偏移量
	 */
	private float offsetX = 0;
	/**
	 * 标注样式Y方向偏移量
	 */
	private float offsetY = 0;
	/**
	 * 广告板
	 */
	private boolean isBillboard = false;
	
	
	public static SimpleMarkerSymbol DEFAULT() {
		return new SimpleMarkerSymbol(ColorKit.RED);
	}
	
	public MarkerSymbol() {
	}
	
	public MarkerSymbol(MarkerSymbol symbol) {
		this.angle = symbol.getAngle();
		this.width = symbol.getWidth();
		this.height = symbol.getHeight();
		this.offsetX = symbol.getOffsetX();
		this.offsetY = symbol.getOffsetY();
		this.isBillboard = symbol.isBillboard();
	}
	
	public abstract MarkerSymbol getPickSymbol(int color);
	
	public float getX(float x) {
		return x - width / 2 + offsetX;
	}
	
	public float getY(float y) {
		return y - height / 2 + offsetY;
	}

	/**
	 * 获取标注样式角度
	 * @return
	 */
	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	/**
	 * 获取标注样式宽度
	 * @return
	 */
	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * 获取标注样式高度
	 * @return
	 */
	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * 获取标注样式X方向偏移量
	 * @return
	 */
	public float getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(float offsetX) {
		this.offsetX = offsetX;
	}

	/**
	 * 获取标注样式Y方向偏移量
	 * @return
	 */
	public float getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(float offsetY) {
		this.offsetY = offsetY;
	}

	public boolean isBillboard() {
		return isBillboard;
	}

	public void setBillboard(boolean isBillboard) {
		this.isBillboard = isBillboard;
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
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
}
