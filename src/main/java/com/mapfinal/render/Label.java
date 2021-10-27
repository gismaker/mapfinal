package com.mapfinal.render;

import com.mapfinal.Mapfinal;
import com.mapfinal.converter.scene.ScenePoint;
import com.mapfinal.event.Event;
import com.mapfinal.geometry.Latlng;
import com.mapfinal.geometry.ScreenPoint;
import com.mapfinal.geometry.ScreenRect;
import com.mapfinal.map.AbstractLayer;
import com.mapfinal.map.MapContext;
import com.mapfinal.render.style.LabelSymbol;

public class Label {
	
	private String text;
	private Latlng position;

	private LabelSymbol symbol;
	
	private boolean visible = true;
	
	public Label(String text, Latlng position, LabelSymbol symbol) {
		// TODO Auto-generated constructor stub
		this.text = text;
		this.position = position;
		this.symbol = symbol;
	}
	
	public ScreenRect getRect(MapContext context) {
		ScenePoint p1 = context.latLngToPoint(position);
		
		RenderEngine engine = Mapfinal.me().getScene().getRenderEngine();
		ScreenPoint lp = engine.getLableBox(this);
		float sw = lp.getX();
		float sh = lp.getY();
		
		int fx = getSymbol()!=null ? getSymbol().getOffsetX() : 0;
		int fy = getSymbol()!=null ? getSymbol().getOffsetY() : 0;
		
		int padding = getSymbol()!=null ? getSymbol().getPadding() : 0;
		
		ScreenPoint sp1 = new ScreenPoint(p1.getSx() + fx, p1.getSy() + fy);
		ScreenPoint sp2 = new ScreenPoint(p1.getSx() + fx + padding * 2 + sw, p1.getSy() + fy);
		ScreenPoint sp3 = new ScreenPoint(p1.getSx() + fx + padding * 2 + sw, p1.getSy() + fy - padding * 2 - sh);
		ScreenPoint sp4 = new ScreenPoint(p1.getSx() + fx, p1.getSy() + fy - padding * 2 - sh);
		return new ScreenRect(sp1, sp2, sp3, sp4);
	}
	
	public boolean handleEvent(Event event, AbstractLayer layer) {
		if(!visible) return false;
		if(event.isAction("mouseClick")) {
			MapContext context = event.get("map");
			ScenePoint p1 = context.latLngToPoint(position);
			
			RenderEngine engine = Mapfinal.me().getScene().getRenderEngine();
			ScreenPoint lp = engine.getLableBox(this);
			
			int fx = getSymbol()!=null ? getSymbol().getOffsetX() : 0;
			int fy = getSymbol()!=null ? getSymbol().getOffsetY() : 0;
			int padding = getSymbol()!=null ? getSymbol().getPadding() : 0;
			
			float sw = lp.getX();
			float sh = lp.getY();
			//左上角像素值
			int x = (int) Math.round(p1.getSx() + fx - padding);
			int y = (int) Math.round(p1.getSy() + fy + padding);
			//右下角像素值
			int ex = (int) Math.round(p1.getSx() + sw + fx + padding);
			int ey = (int) Math.round(p1.getSy() - sh + fy - padding);
			
			//用户点击的像素坐标
			ScreenPoint sp = event.get("screenPoint");
			float x0 = sp.getX();
			float y0 = sp.getY();
			
			if(x0 >= x && x0<=ex && y0 >= y && y0<=ey) {
				return layer.sendEvent(Event.by(layer.getEventAction("click"), "center", position));
			}
		}
		return false;
	}

	public LabelSymbol getSymbol() {
		return symbol;
	}

	public void setSymbol(LabelSymbol symbol) {
		this.symbol = symbol;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Latlng getPosition() {
		return position;
	}
	public void setPosition(Latlng position) {
		this.position = position;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
}
