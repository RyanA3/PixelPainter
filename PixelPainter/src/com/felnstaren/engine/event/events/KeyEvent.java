package com.felnstaren.engine.event.events;

import com.felnstaren.engine.event.Event;

public class KeyEvent extends Event {

	private int key;
	private boolean[] key_map;
	
	public KeyEvent(boolean[] key_map, int key) {
		super("KeyEvent");
		this.key = key;
		this.key_map = key_map;
	}
	
	
	
	public int getKey() {
		return this.key;
	}
	
	public boolean[] getKeyMap() {
		return this.key_map;
	}
	
	public boolean isPressed(int k) {
		return key_map[k];
	}
	
}
