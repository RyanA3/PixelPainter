package com.felnstaren.engine.event.events;

import com.felnstaren.engine.event.Event;
import com.felnstaren.engine.ui.button.radio.RadioButton;

public class RadioButtonEvent extends Event {

private RadioButton button;
	
	public RadioButtonEvent(RadioButton button) {
		super("RadioButtonEvent");
		this.button = button;
	}
	
	
	
	public RadioButton getButton() {
		return this.button;
	}
	
}
