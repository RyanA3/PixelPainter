package com.felnstaren.engine.event;

public class Event {
	
	protected String name;
	protected boolean cancelled;
	
	public Event(String name) {
		this.name = name;
	}
	
	
	
	public String getName() {
		return this.name;
	}
	
	public boolean isCancelled() {
		return this.cancelled;
	}
	
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
}
