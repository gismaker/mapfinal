package com.mapfinal.render;

import com.mapfinal.event.Callback;
import com.mapfinal.event.Event;

public class RendererSceneNode implements SceneNode {

	private Renderer renderer;
	private String name;
	private boolean bVisible = true;
	
	public RendererSceneNode(String name, Renderer renderer) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.setRenderer(renderer);
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean isDrawable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onRender(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		//System.out.println("Renderer onRender");
		if(renderer!=null) renderer.onRender(event, engine);
	}

	@Override
	public void onEvent(Event event) {
		// TODO Auto-generated method stub
		if(renderer!=null) renderer.onEvent(event);
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public void setRenderer(Renderer renderer) {
		this.renderer = renderer;
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
