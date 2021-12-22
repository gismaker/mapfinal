package com.mapfinal.platform.swing;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.mapfinal.event.Event;
import com.mapfinal.platform.bufferedimage.BufferedImagePickRenderEngine;
import com.mapfinal.render.RenderEngine;
import com.mapfinal.render.SceneGraph;

public class GraphicsScene extends SceneGraph {
	//渲染引擎
	private GraphicsJPanelRenderEngine engine;
	//拾取渲染
	private BufferedImagePickRenderEngine pickEngine;
	//是否拾取
	private boolean pick = false;
	//通用事件参数
	private Event event;
	
	public GraphicsScene(JPanel panel) {
		super();
		engine = new GraphicsJPanelRenderEngine(panel);
		pickEngine = new BufferedImagePickRenderEngine(100, 100);
		event = new Event("render");
	}
	
	@Override
	public RenderEngine getRenderEngine() {
		// TODO Auto-generated method stub
		return engine;
	}
	
	public void putEventData(String name, Object data) {
		event.put(name, data);
	}
	
	public void draw(Graphics g, int width, int height) {
//		BufferedImage bi = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
//		engine.setGraphics(bi.getGraphics());
		engine.setGraphics(g);
		//System.out.println(width+"," + height);
		setWidth(width);
		setHeight(height);
		Event eventRender = Event.by("render", event);
		eventRender.set("width", getWidth()).set("height", getHeight());
		this.draw(eventRender, engine);
//		g.drawImage(bi, 0, 0, width,height, null);
//		bi.flush();
		//pick画板
		g.drawImage(pickEngine.getPickImage(), 0, 0, pickEngine.getWidth(),pickEngine.getHeight(), null);
	}
	
	public BufferedImage drawTo(BufferedImage image) {
		if(image==null) {
			image =  new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
		}
		engine.setGraphics(image.getGraphics());
		setWidth(image.getWidth());
		setHeight(image.getHeight());
		Event eventRender = Event.by("drawing", event);
		eventRender.set("width", getWidth()).set("height", getHeight());
		this.draw(eventRender, engine);
		return image;
	}
	
	public void drawPick(float x, float y) {
		setWidth(pickEngine.getWidth());
		setHeight(pickEngine.getHeight());
		Event eventRender = Event.by("pick", event);
		eventRender.set("width", getWidth()).set("height", getHeight());
		this.pick(eventRender, pickEngine, x, y);
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
