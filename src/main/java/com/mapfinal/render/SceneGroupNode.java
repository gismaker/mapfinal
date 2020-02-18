package com.mapfinal.render;

import java.util.ArrayList;
import java.util.List;

import com.mapfinal.event.Callback;
import com.mapfinal.event.Event;

public class SceneGroupNode implements SceneNode {

	private List<SceneNode> sceneGroup;
	private String name = "groupNode";
	private boolean bVisible = true;
	
	public SceneGroupNode() {
		setSceneGroup(new ArrayList<SceneNode>());
	}
	
	public void add(SceneNode node) {
		sceneGroup.add(node);
	}
	
	public void add(int index, SceneNode node) {
		sceneGroup.add(index, node);
	}
	
	public void remove(SceneNode node) {
		sceneGroup.remove(node);
	}
	
	public void remove(int index) {
		sceneGroup.remove(index);
	}
	
	public void clear() {
		sceneGroup.clear();
	}

	public List<SceneNode> getSceneGroup() {
		return sceneGroup;
	}

	public void setSceneGroup(List<SceneNode> sceneGroup) {
		this.sceneGroup = sceneGroup;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public boolean isDrawable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onRender(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		for (int i = 0; i < sceneGroup.size(); i++) {
			SceneNode node = sceneGroup.get(i);
			if(node!=null) node.onRender(event, engine);
		}
	}

	@Override
	public void onEvent(Event event) {
		// TODO Auto-generated method stub
		for (int i = 0; i < sceneGroup.size(); i++) {
			SceneNode node = sceneGroup.get(i);
			if(node!=null) node.onEvent(event);
		}
	}
	
	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return bVisible;
	}

	@Override
	public void setVisible(boolean bVisible) {
		// TODO Auto-generated method stub
		this.bVisible = bVisible;
	}
}
