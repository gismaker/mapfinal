package com.mapfinal.platform.develop.graphics;

import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import com.mapfinal.render.pick.PickRenderEngine;

import org.locationtech.jts.geom.Coordinate;

/**
 * Java绘图: 使用 Graphics 类绘制线段、矩形、椭圆/圆弧/扇形、图片、文本
 * https://blog.csdn.net/xietansheng/article/details/55669157
 * 
 * @author yangyong
 *
 */
public class GraphicsPickRenderEngine extends GraphicsRenderEngine implements PickRenderEngine {

	private BufferedImage pickImage;
	private int width = 300;
	private int height = 300;

	public GraphicsPickRenderEngine(int width, int height) {
		this.width = width;
		this.height = height;
		pickImage = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
		renderStart();
		renderEnd();
	}
	
	public void resize(int width, int height) {
		
	}

	@Override
	public void update() {
	}
	
	@Override
	public void translate(Coordinate coordinate) {
		// TODO Auto-generated method stub
		getGraphics2D().translate((int) coordinate.x, (int) coordinate.y);
	}

	@Override
	public void renderStart() {
		setGraphics(pickImage.createGraphics());
		getGraphics2D().setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		getGraphics2D().setBackground(Color.WHITE);
		getGraphics2D().clearRect(0, 0, getWidth(), getHeight());
	}

	@Override
	public void renderEnd() {
		// TODO Auto-generated method stub
		getGraphics2D().dispose();
		pickImage.flush();
	}
	
	@Override
	public int getPixelColor() {
		// TODO Auto-generated method stub
		int k = width / 2;
		int n = height / 2;
		int color = pickImage.getRGB(k, n) + 16777216;
		System.out.println("PickRenderEngine: color: " + color);
//		for (int i = 0; i < height; i++) {
//			for (int j = 0; j < width; j++) {
//				int c = pickImage.getRGB(j,i) + 16777216;
//				if(c > 0) System.out.println("PickRenderEngine: " + i + ", " + j + ", " + c);
//			}
//		}
		return color;
	}
	
	public BufferedImage getPickImage() {
		return pickImage;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
