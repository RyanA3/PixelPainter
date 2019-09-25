package com.felnstaren.painter.listeners;

import com.felnstaren.engine.event.EventMethod;
import com.felnstaren.engine.event.Listener;
import com.felnstaren.engine.event.events.RadioButtonSelectEvent;
import com.felnstaren.engine.ui.button.radio.RadioButton;
import com.felnstaren.painter.Options;
import com.felnstaren.painter.engine_extension.ColorSelectorButton;

public class ColorListener extends Listener {

	@EventMethod
	public void onColorChange(RadioButtonSelectEvent event) {
		RadioButton rbutton = event.getButton();
		
		if(!(rbutton instanceof ColorSelectorButton)) return;
		ColorSelectorButton button = (ColorSelectorButton) rbutton;
		
		Options.color = button.getSelectableColor();
	}
	
}
