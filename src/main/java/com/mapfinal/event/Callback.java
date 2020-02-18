package com.mapfinal.event;

public interface Callback {
	public void execute(Event event);
	public void error(Event event);
}
