package com.mapfinal.geometry;

public class ScreenPoint {
	private float x;
	private float y;
	
	public ScreenPoint(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public static ScreenPoint by(float x, float y) {
		return new ScreenPoint(x, y);
	}
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "(" + x +"," + y+")";
	}
}
