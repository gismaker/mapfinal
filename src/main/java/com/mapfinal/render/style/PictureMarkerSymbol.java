package com.mapfinal.render.style;

import com.mapfinal.resource.image.Image;

public class PictureMarkerSymbol extends MarkerSymbol {

	private Image<?> image;
	private MarkerSymbol.STYLE style = STYLE.ICON;
	
	public PictureMarkerSymbol(Image<?> image) {
		// TODO Auto-generated constructor stub
		this.image = image;
	}
	public MarkerSymbol.STYLE getStyle() {
		return style;
	}
	public Image<?> getImage() {
		return image;
	}
	public void setImage(Image<?> image) {
		this.image = image;
	}
	
	@Override
	public MarkerSymbol getPickSymbol(int color) {
		// TODO Auto-generated method stub
		return new SimpleMarkerSymbol(color);
	}
}
