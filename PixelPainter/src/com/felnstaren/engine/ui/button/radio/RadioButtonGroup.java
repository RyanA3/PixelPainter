package com.felnstaren.engine.ui.button.radio;

import java.util.ArrayList;
import java.util.List;

public class RadioButtonGroup {
	
	private List<RadioButton> buttons;

	public RadioButtonGroup() {
		buttons = new ArrayList<RadioButton>();
	}
	
	
	
	public void addButton(RadioButton button) {
		this.buttons.add(button);
		if(this.buttons.size() == 1) button.setPressed(true);
	}
	
	
	
	public void setPressed(RadioButton button) {
		if(getPressedButton() != null) getPressedButton().setPressed(false);
		button.setPressed(true);
	}
	

	
	public boolean hasButton(RadioButton button) {
		return buttons.contains(button);
	}
	
	public RadioButton getPressedButton() {
		for(RadioButton button : buttons) {
			if(button.isPressed()) return button;
		}
		
		return null;
	}
	
	
	
	public List<RadioButton> getButtons() {
		return this.buttons;
	}
}
