package com.mapfinal.render;

import com.mapfinal.Mapfinal;
import com.mapfinal.event.Event;
import com.mapfinal.event.EventListener;
import com.mapfinal.event.Listener;

@Listener(action={"redraw"})
public class SceneRedrawListener implements EventListener {

	@Override
	public boolean onEvent(Event event) {
		// TODO Auto-generated method stub
		if("redraw".equals(event.getAction())) {
			Mapfinal.me().getScene().setRedraw(true);
		}
		return false;
	}

}
