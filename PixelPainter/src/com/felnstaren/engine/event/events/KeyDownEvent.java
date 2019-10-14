package com.felnstaren.engine.event.events;

public class KeyDownEvent extends KeyEvent {

	public KeyDownEvent(boolean[] key_map, int key) {
		super(key_map, key);
		this.name = "KeyDownEvent";
	}
	
}
