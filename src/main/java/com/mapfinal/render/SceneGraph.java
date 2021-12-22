package com.mapfinal.render;

import java.util.List;

import com.mapfinal.event.Event;
import com.mapfinal.kit.StringKit;
import com.mapfinal.render.pick.PickManager;
import com.mapfinal.render.pick.PickRenderEngine;

/**
 * 场景图
 * @author yangyong
 *
 */
public abstract class SceneGraph {

	private SceneGroupNode sceneRoot;
	private int width;
	private int height;
	private boolean isRedraw = false;
	private boolean isRendering = false;
	
	public SceneGraph() {
		sceneRoot = new SceneGroupNode();
	}
	
	public abstract RenderEngine getRenderEngine();
	protected abstract void update();
	
	public void addNode(SceneNode node) {
		sceneRoot.add(node);
	}
	
	public void addNode(int index, SceneNode node) {
		sceneRoot.add(index, node);
	}
	
	public void remove(SceneNode node) {
		sceneRoot.remove(node);
	}
	
	public void remove(int index) {
		sceneRoot.remove(index);
	}
	
	public void clear() {
		sceneRoot.clear();
	}

	public List<SceneNode> getSceneGroup() {
		return sceneRoot.getSceneGroup();
	}
	
	/**
	 * 渲染
	 * @param event
	 * @param engine
	 */
	public void draw(Event event, RenderEngine engine) {
		isRendering = true;
		isRedraw = false;
		engine.renderStart();
		sceneRoot.draw(event, engine);
		engine.renderEnd();
		isRendering = false;
	}
	
	public void pick(Event event, PickRenderEngine engine, float x, float y) {
		event.set("pick_screen_x", x);
		event.set("pick_screen_y", y);
		PickManager.me().start();
		engine.renderStart();
		sceneRoot.pick(event, engine);
		engine.renderEnd();
		int color = engine.getPixelColor();
		String idName = PickManager.me().getPickId(color);
		System.out.println("SceneGraph: picked: " + idName);
		if(StringKit.notBlank(idName)) {
			Event pickedEvent = new Event("picked");
			pickedEvent.set("picked_color", color);
			pickedEvent.set("picked_name", idName);
			System.out.println("SceneGraph: picked_name: " + idName);
			handleEvent(pickedEvent);
		}
		PickManager.me().stop();
	}
	
	/**
	 * 事件处理
	 * @param event
	 * @return
	 */
	public boolean handleEvent(Event event) {
		return sceneRoot.handleEvent(event);
	}
	
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public SceneNode getSceneRoot() {
		return sceneRoot;
	}

	public void setSceneRoot(SceneGroupNode sceneRoot) {
		this.sceneRoot = sceneRoot;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isRendering() {
		return isRendering;
	}

	public boolean isRedraw() {
		return isRedraw;
	}

	public void setRedraw(boolean isRedraw) {
		//if(isRendering) return;
		if(this.isRedraw==false) {
			update();
		}
		this.isRedraw = isRedraw;
	}

}
