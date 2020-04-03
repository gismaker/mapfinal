package com.mapfinal.render;

import java.util.List;

import com.mapfinal.event.Event;

/**
 * 场景图
 * https://blog.csdn.net/sdnulyc/article/details/77555348
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
	
	public void onRender(Event event, RenderEngine engine) {
		isRendering = true;
		isRedraw = false;
		sceneRoot.onRender(event, engine);
		isRendering = false;
	}
	
	public void onEvent(Event event) {
		sceneRoot.onEvent(event);
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
		if(isRendering) return;
		if(this.isRedraw==false) {
			update();
		}
		this.isRedraw = isRedraw;
	}

}
