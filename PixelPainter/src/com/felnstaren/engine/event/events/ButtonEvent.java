package com.felnstaren.engine.event.events;

import com.felnstaren.engine.event.Event;
import com.felnstaren.engine.ui.button.Button;

public class ButtonEvent extends Event {

	private Button button;
	
	public ButtonEvent(Button button) {
		super("ButtonEvent");
		this.button = button;
	}
	
	
	
	public Button getButton() {
		return this.button;
	}
	
}
