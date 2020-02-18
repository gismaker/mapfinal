package com.mapfinal.render.style;

public class MarkerSymbol implements Symbol {

	/**
	 * 标注样式角度
	 */
	private float angle;
	/**
	 * 标注样式宽度
	 */
	private int width;
	/**
	 * 标注样式高度
	 */
	private float height;
	/**
	 * 标注样式X方向偏移量
	 */
	private float offsetX;
	/**
	 * 标注样式Y方向偏移量
	 */
	private float offsetY;
	/**
	 * 广告板
	 */
	private boolean isBillboard;
	
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
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
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
}
