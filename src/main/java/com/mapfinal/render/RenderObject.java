package com.mapfinal.render;

import com.mapfinal.event.Event;

public abstract class RenderObject implements Renderable {
	
	protected boolean isEditMode = false;
	
	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		if(event.isAction("editMode")) {
			setEditMode(true);
		} else if(event.isAction("normalMode")) {
			setEditMode(false);
		} 
	}
	
	public boolean isEditMode() {
		return isEditMode;
	}

	public void setEditMode(boolean isEditMode) {
		this.isEditMode = isEditMode;
	}
}
