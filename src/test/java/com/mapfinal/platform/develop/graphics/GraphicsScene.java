package com.mapfinal.platform.develop.graphics;

import java.awt.Graphics;

import javax.swing.JPanel;

import com.mapfinal.event.Event;
import com.mapfinal.render.SceneGraph;

public class GraphicsScene extends SceneGraph {
	
	private GraphicsJPanelRenderEngine engine;
	
	public GraphicsScene(JPanel panel) {
		super();
		engine = new GraphicsJPanelRenderEngine(panel);
	}
	
	public void draw(Graphics g, int width, int height) {
		engine.setGraphics(g);
		//System.out.println(width+"," + height);
		setWidth(width);
		setHeight(height);
		Event event = new Event("render").set("width", getWidth()).set("height", getHeight());
		super.draw(event, engine);
	}
	
	@Override
	public void pick(float x, float y) {
		GraphicsPickRenderEngine pickEngine = new GraphicsPickRenderEngine();
		setWidth(1);
		setHeight(1);
		Event event = new Event("pick").set("width", getWidth()).set("height", getHeight());
		super.draw(event, pickEngine);
	}

	@Override
	protected void update() {
		// TODO Auto-generated method stub
		engine.update();
	}
}
