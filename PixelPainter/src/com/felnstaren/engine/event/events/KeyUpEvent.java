package com.felnstaren.engine.event.events;

public class KeyUpEvent extends KeyEvent {

	public KeyUpEvent(boolean[] key_map, int key) {
		super(key_map, key);
		this.name = "KeyUpEvent";
	}
	
}
