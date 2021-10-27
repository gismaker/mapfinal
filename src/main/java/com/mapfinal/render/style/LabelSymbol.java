package com.mapfinal.render.style;

import com.alibaba.fastjson.JSONObject;

/**
 * 文本标注
 * @author yangyong
 *
 */
public class LabelSymbol implements Symbol {
	
	private int fontSize = 12;
	private String fontFamily = "Arial";
	/**
	 * plain 普通
	 * bold 加粗
	 * italic 斜体
	 */
	private String fontStyle = "plain";
	private String fontColor = "#000000";
	
	private boolean background = false;
	private String fillColor = "#FFFFFF";
	private int padding = 0;
	
	private int offsetX = 0;
	private int offsetY = 0;
	
	private boolean border = false;
	private String borderColor = "#F7F8F9";
	private int width = 1;
	
	private float minZoom = 0;
	private float maxZoom = 0;
	
	public int getFontSize() {
		return fontSize;
	}
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	public String getFontFamily() {
		return fontFamily;
	}
	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}
	public String getFontColor() {
		return fontColor;
	}
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}
	public boolean isBackground() {
		return background;
	}
	public void setBackground(boolean background) {
		this.background = background;
	}
	public String getFillColor() {
		return fillColor;
	}
	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}
	public int getPadding() {
		return padding;
	}
	public void setPadding(int padding) {
		this.padding = padding;
	}
	public boolean isBorder() {
		return border;
	}
	public void setBorder(boolean border) {
		this.border = border;
	}
	public String getBorderColor() {
		return borderColor;
	}
	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public float getMinZoom() {
		return minZoom;
	}
	public void setMinZoom(float minZoom) {
		this.minZoom = minZoom;
	}
	public float getMaxZoom() {
		return maxZoom;
	}
	public void setMaxZoom(float maxZoom) {
		this.maxZoom = maxZoom;
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
		return null;
	}
	public String getFontStyle() {
		return fontStyle;
	}
	public void setFontStyle(String fontStyle) {
		this.fontStyle = fontStyle;
	}
	public int getOffsetX() {
		return offsetX;
	}
	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}
	public int getOffsetY() {
		return offsetY;
	}
	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}
	
}
