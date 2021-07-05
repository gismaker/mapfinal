package com.mapfinal.platform.develop.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.mapfinal.event.Event;
import com.mapfinal.render.SceneGraph;

public class GraphicsScene extends SceneGraph {
	
	private GraphicsJPanelRenderEngine engine;
	
	GraphicsPickRenderEngine pickEngine;
	boolean pick = false;
	
	public GraphicsScene(JPanel panel) {
		super();
		engine = new GraphicsJPanelRenderEngine(panel);
		pickEngine = new GraphicsPickRenderEngine(100, 100);
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
	
	public void drawPick(float x, float y) {
		setWidth(pickEngine.getWidth());
		setHeight(pickEngine.getHeight());
		pick(pickEngine, x, y);
		pick = true;
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
