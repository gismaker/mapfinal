package com.mapfinal.event.listener;

import com.mapfinal.event.Event;
import com.mapfinal.event.EventKit;
import com.mapfinal.event.EventListener;
import com.mapfinal.geometry.ScreenPoint;
import com.mapfinal.kit.StringKit;
import com.mapfinal.map.MapContext;

public class MapMoveListener implements EventListener {

	private float x0 = 0, y0 = 0;
	private boolean bMove = false;
	
	@Override
	public boolean onEvent(Event event) {
		// TODO Auto-generated method stub
		if (StringKit.isBlank(event.getAction()))
			return false;
		MapContext context = event.get("map");
		switch (event.getAction()) {
		case "mouseDown":
			context.setZoomScale(false);
			ScreenPoint sp = event.get("screenPoint");
			x0 = sp.getX();
			y0 = sp.getY();
			bMove = true;
			return true;
		case "mouseUp":
			if (bMove) {
				bMove = false;
				if(!context.isZoomScale()) {
					context.resetCenterForDrag();
					//System.out.println("[GeoMap.onEvent] mouseUp: " + dx + ", " + dy);
				}
				context.setZoomScale(false);
				context.drag(0, 0);
				// System.out.println("[GeoMap.onEvent] mouseUp");
				EventKit.sendEvent("redraw");
			}
			return true;
		case "mouseMove":
			if (bMove && !context.isZoomScale()) {
				ScreenPoint spm = event.get("screenPoint");
				context.drag(spm.getX() - x0, spm.getY() - y0);
				// System.out.println("[GeoMap.onEvent] move x: " + x + ", y: "
				// + y + ", x0: " + x0 + ", y0: " + y0);
				// System.out.println("[GeoMap.onEvent] move dx: " + dx + ", dy:
				// " + dy);
				EventKit.sendEvent("redraw");
			}
			return true;
		default:
			break;
		}
		return false;
	}

}
