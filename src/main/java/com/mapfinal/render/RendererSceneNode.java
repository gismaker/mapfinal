package com.mapfinal.render;

import com.mapfinal.event.Event;
import com.mapfinal.event.EventListener;

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
	public void draw(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
		//System.out.println("Renderer draw");
		if(renderer!=null) renderer.draw(event, engine);
	}

	@Override
	public boolean handleEvent(Event event) {
		// TODO Auto-generated method stub
		if(renderer!=null && Renderer.EVENT_CANCELDRAW.equals(event.getAction())) {
			renderer.handleEvent(event);
		}
		return false;
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

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getEventAction(String eventName) {
		// TODO Auto-generated method stub
		return "SceneNode:" + getClass().getSimpleName() + ":" + eventName;
	}

	@Override
	public boolean sendEvent(Event event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(String eventAction, EventListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addListener(String eventAction, EventListener listener) {
		// TODO Auto-generated method stub
		
	}
}
