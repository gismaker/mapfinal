package com.mapfinal.render.style;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mapfinal.resource.image.Image;

public class TextureFillSymbol extends FillSymbol {

	private int alpha = 255;
	private int color = 0;
	private LineSymbol outline;
	private Image image;
	private int textureId;//for 3D

	public TextureFillSymbol(int color) {
		// TODO Auto-generated constructor stub
		this.color = color;
	}

	public TextureFillSymbol(TextureFillSymbol symbol) {
		// TODO Auto-generated constructor stub
		this.alpha = symbol.getAlpha();
		this.color = symbol.getColor();
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

	@Override
	public void fromJson(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JSONObject toJson() {
		// TODO Auto-generated method stub
		return (JSONObject) JSON.toJSON(this);
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getTextureId() {
		return textureId;
	}

	public void setTextureId(int textureId) {
		this.textureId = textureId;
	}

}
