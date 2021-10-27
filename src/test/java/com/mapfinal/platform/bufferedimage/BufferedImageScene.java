package com.mapfinal.platform.bufferedimage;

import java.awt.Graphics;

import com.mapfinal.event.Event;
import com.mapfinal.render.RenderEngine;
import com.mapfinal.render.SceneGraph;

public class BufferedImageScene extends SceneGraph {
	
	private BufferedImageRenderEngine engine;
	
	boolean pick = false;
	
	public BufferedImageScene() {
		super();
		engine = new BufferedImageRenderEngine();
	}
	
	@Override
	public RenderEngine getRenderEngine() {
		// TODO Auto-generated method stub
		return engine;
	}
	
	public void draw(Graphics g, int width, int height) {
//		BufferedImage bi = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
//		engine.setGraphics(bi.getGraphics());
		engine.setGraphics(g);
		//System.out.println(width+"," + height);
		setWidth(width);
		setHeight(height);
		Event event = new Event("render").set("width", getWidth()).set("height", getHeight());
		super.draw(event, engine);
//		g.drawImage(bi, 0, 0, width,height, null);
//		bi.flush();
		//pick画板
		//g.drawImage(pickEngine.getPickImage(), 0, 0, pickEngine.getWidth(),pickEngine.getHeight(), null);
	}
	
	@Override
	protected void update() {
		// TODO Auto-generated method stub
		engine.update();
	}
	
	public boolean getPick() {
		return pick;
	}
}
