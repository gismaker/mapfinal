package com.mapfinal.platform.bufferedimage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.mapfinal.event.Event;
import com.mapfinal.render.RenderEngine;
import com.mapfinal.render.SceneGraph;

public class BufferedImageScene extends SceneGraph {

	//渲染引擎
	private BufferedImageRenderEngine engine;
	// 拾取渲染
	private BufferedImagePickRenderEngine pickEngine;
	//是否拾取
	private boolean pick = false;
	// 通用事件参数
	private Event event;

	public BufferedImageScene() {
		super();
		engine = new BufferedImageRenderEngine();
		pickEngine = new BufferedImagePickRenderEngine(2, 2);
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
	
	public void draw(Graphics g, int width, int height) {
		engine.setGraphics(g);
		setWidth(width);
		setHeight(height);
		Event eventRender = Event.by("render", event);
		eventRender.set("width", getWidth()).set("height", getHeight());
		this.draw(eventRender, engine);
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

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
}
