package com.mapfinal.platform.develop.graphics;

import java.awt.Graphics;

import javax.swing.JPanel;

import com.mapfinal.event.Event;
import com.mapfinal.render.SceneGraph;

public class GraphicsScene extends SceneGraph {
	
	private GraphicsRenderEngine engine;
	
	public GraphicsScene(JPanel panel) {
		super();
		engine = new GraphicsRenderEngine(panel);
	}
	
	public void onRender(Graphics g, int width, int height) {
		engine.setGraphics(g);
		//System.out.println(width+"," + height);
		setWidth(width);
		setHeight(height);
		Event event = new Event("render").set("width", getWidth()).set("height", getHeight());
		super.onRender(event, engine);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		engine.update();
	}
}
