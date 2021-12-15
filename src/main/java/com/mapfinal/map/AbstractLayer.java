package com.mapfinal.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mapfinal.converter.SpatialReference;
import com.mapfinal.event.Event;
import com.mapfinal.event.EventListener;
import com.mapfinal.kit.StringKit;
import com.mapfinal.render.RenderEngine;
import com.mapfinal.render.Renderer;
import com.mapfinal.render.pick.PickManager;

/**
 * https://blog.csdn.net/Smart3S/article/details/81121349
 * @author yangyong
 *
 */
public abstract class AbstractLayer implements Layer {

	private int id;
	private String name;
	private String title;
	private float opacity = 1.0f;
	private float minZoom = 0;
	private float maxZoom = 24;
	private boolean visible = true;
	/**
	 * 坐标系，应用于转换器
	 */
	private SpatialReference spatialReference;
	/**
	 * 渲染器
	 */
	private Renderer renderer;
	/**
	 * 父类
	 */
	private Layer parent;
	/**
	 * 事件列表
	 */
	private Map<String, List<EventListener>> listenerMap;
	
	public AbstractLayer() {
		setName("layer_" + StringKit.getUuid32());
	}
	
	public MapContext getMapContext(Event event) {
		if(event!=null) {
			return event.get("map");
		}
		return null;
	}

	@Override
	public void addTo(LayerGroup layerGroup) {
		this.id = layerGroup.add(this);
		this.setParent(layerGroup);
	}
	
	@Override
	public void setParent(Layer layer) {
		// TODO Auto-generated method stub
		parent = layer;
	}

	@Override
	public Layer getParent() {
		// TODO Auto-generated method stub
		return parent;
	}
	
	@Override
	public boolean isDrawable() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	@Override
	public void pick(Event event, RenderEngine engine) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void drawFinished(int id, float percent) {
		// TODO Auto-generated method stub
		if (parent != null) {
			parent.drawFinished(id, percent);
        }
	}
	
	@Override
	public boolean handleEvent(Event event) {
		// TODO Auto-generated method stub
		if(renderer!=null && Renderer.EVENT_CANCELDRAW.equals(event.getAction())) {
			renderer.handleEvent(event);
		}
		return false;
	}
	
	@Override
	public String getEventAction(String eventName) {
		return "Layer:" + getClass().getSimpleName() + ":" + eventName;
	}
	
	public void addClick(EventListener listener) {
		addListener(getEventAction("click"), listener);
	}
	
	public void removeClick(EventListener listener) {
		removeListener(getEventAction("click"), listener);
	}
	
	public void clearClick() {
		clearListener(getEventAction("click"));
	}

	@Override
	public void removeListener(String eventAction, EventListener listener) {
		if(listenerMap!=null) {
			deleteListner(listenerMap.get(eventAction), listener);
		}
	}
	
	public void clearListener(String eventAction) {
		if(listenerMap!=null) {
			List<EventListener> list = listenerMap.get(eventAction);
			if (null != list) {
				list.clear();
			}
		}
	}
	
	private void deleteListner(List<EventListener> listenerList, EventListener listener) {
		if(listenerList==null) return;
		EventListener deleteListener = null;
		for (EventListener eventlistener : listenerList) {
			if (eventlistener == listener) {
				deleteListener = listener;
			}
		}
		if (deleteListener != null) {
			listenerList.remove(deleteListener);
		}
	}

	@Override
	public void addListener(String eventAction, EventListener listener) {
		if (listener == null) {
			return;
		}
		if(listenerMap==null) {
			listenerMap = new HashMap<String, List<EventListener>>();
		}
		List<EventListener> list = listenerMap.get(eventAction);
		if (null == list) {
			list = new ArrayList<EventListener>();
		}
		if (list.isEmpty() || !list.contains(listener)) {
			list.add(listener);
		}
		listenerMap.put(eventAction, list);
	}

	@Override
	public boolean sendEvent(final Event event) {
		return sendEvent(event.getAction(), event);
	}
	
	@Override
	public boolean sendEvent(String action, final Event event) {
		if(listenerMap!=null) {
			List<EventListener> syncListeners = listenerMap.get(action);
			if (syncListeners != null && !syncListeners.isEmpty()) {
				return invokeListeners(event, syncListeners);
			}
		}
		return false;
	}
	
	private boolean invokeListeners(final Event message, List<EventListener> syncListeners) {
		for (final EventListener listener : syncListeners) {
			try {
				boolean flag = listener.onEvent(message);
				if(flag) return true;
			} catch (Throwable e) {
				System.err.println(String.format("listener[%s] onMessage is erro! ", listener.getClass()));
				e.printStackTrace();
			}
		}
		return false;
	}
	
	@Override
	public String getName() {
		if(StringKit.isBlank(name)) {
			setName("layer_" + StringKit.getUuid32());
		}
		return name;
	}
	
	@Override
	public void setName(String name) {
		if(StringKit.isBlank(name)) return;
		if(name.equals(this.name)) return;
		PickManager.me().registerId(name, this.getClass().getName());
		this.name = name;
	}
	
	@Override
	public String getTitle() {
		return title;
	}
	
	@Override
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public SpatialReference getSpatialReference() {
		return spatialReference;
	}
	
	public void setSpatialReference(SpatialReference spatialReference) {
		this.spatialReference = spatialReference;
	}
	
	@Override
	public Renderer getRenderer() {
		return renderer;
	}
	
	@Override
	public void setRenderer(Renderer renderer) {
		this.renderer = renderer;
	}
	
	@Override
	public float getOpacity() {
		return opacity;
	}
	
	@Override
	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}
	
	@Override
	public float getMinZoom() {
		return minZoom;
	}
	
	@Override
	public void setMinZoom(float minZoom) {
		this.minZoom = minZoom;
	}
	
	@Override
	public float getMaxZoom() {
		return maxZoom;
	}
	
	@Override
	public void setMaxZoom(float maxZoom) {
		this.maxZoom = maxZoom;
	}
	
	@Override
	public boolean isVisible() {
		return visible;
	}
	
	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
