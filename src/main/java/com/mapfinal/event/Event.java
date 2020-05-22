/**
 * Copyright (c) 2015-2017, Henry Yang 杨勇 (gismail@foxmail.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mapfinal.event;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.mapfinal.geometry.ScreenPoint;

public class Event implements Cloneable, Serializable {
	private static final long serialVersionUID = 1L;

	private final long timestamp;
	private String action;
	private Map<String, Object> data;
	private Callback callback;
	
	public Event(String action) {
		this.action = action;
		this.data = new HashMap<>();
		this.timestamp = System.currentTimeMillis();
	}

	public Event(String action, String name, Object data) {
		this.action = action;
		this.data = new HashMap<>();
		this.data.put(name, data);
		this.timestamp = System.currentTimeMillis();
	}
	
	public static Event by(String action) {
		return new Event(action);
	}
	
	public static Event by(String action, String name, Object data) {
		return new Event(action, name, data);
	}
	
	public Event clone() {
		Event o = null;
        try {
            o = (Event) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    } 
	
	public Event set(String name, Object data) {
		this.data.put(name, data);
		return this;
	}
	
	public Event setScreenPoint(float x, float y) {
		this.put("screenPoint", ScreenPoint.by(x, y));
		return this;
	}
	
	public void put(String name, Object data) {
		this.data.put(name, data);
	}
	
	public boolean hasData(String name) {
		return data.containsKey(name);
	}
	
	public boolean isAction(String actionName) {
		return actionName.equals(action) ? true : false;
	}

	@SuppressWarnings("unchecked")
	public <M> M get(String name) {
		return (M) data.get(name);
	}
	
	public <M> M get(String name, M defaultValue) {
		Object res = data.get(name);
		return res==null ? defaultValue : (M) res;
	}
	
	public Map<String, Object> getData() {
		return data;
	}

	public String getAction() {
		return action;
	}

	public long getTimestamp() {
		return this.timestamp;
	}
	
	public Callback getCallback() {
		return callback;
	}

	public void setCallback(Callback callback) {
		this.callback = callback;
	}
	
	public boolean isRender() {
		return "render".equals(action) ? true : false;
	}
	
	public boolean hasCallback() {
		return this.callback!=null ? true : false;
	}

	@Override
	public String toString() {
		return "Event [timestamp=" + timestamp + ", action=" + action + ", data=" + data + "]";
	}
}
