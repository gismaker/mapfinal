package com.mapfinal.event.listener;

import com.mapfinal.event.Event;
import com.mapfinal.event.EventKit;
import com.mapfinal.event.EventListener;
import com.mapfinal.kit.StringKit;
import com.mapfinal.map.MapContext;

public class MapZoomListener implements EventListener {

	public boolean onEvent(Event event) {
		if (StringKit.isBlank(event.getAction()))
			return false;
		MapContext context = event.get("map");
		if ("mouseWheel".equals(event.getAction())) {
			context.setZoomScale(true);
			if(event.get("rotation")!=null) {
				int rotation = event.get("rotation");
				if (rotation == 1) {
					context.zoomOut();
				}
				if (rotation == -1) {
					context.zoomIn();
				}
			}
			if(event.get("scaleFactor")!=null) {
				float scaleFactor = event.get("scaleFactor");
				float hzoom = context.getZoom();
				hzoom += scaleFactor - 1;
				context.setZoom(hzoom);
			}
			// System.out.println("[GeoMap.onEvent] current zoom: " +
			// getZoom());
			EventKit.sendEvent("redraw");
			//isZoomScale = false;
			return true;
		}
		return false;
	}
}
