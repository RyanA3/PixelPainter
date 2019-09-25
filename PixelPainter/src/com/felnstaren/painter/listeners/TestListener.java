package com.felnstaren.painter.listeners;

import com.felnstaren.engine.event.EventMethod;
import com.felnstaren.engine.event.Listener;
import com.felnstaren.engine.event.events.ButtonPressEvent;
import com.felnstaren.engine.event.events.ButtonReleaseEvent;
import com.felnstaren.engine.gfx.Image;
import com.felnstaren.engine.ui.button.Button;
import com.felnstaren.painter.Options;

public class TestListener extends Listener {

	@EventMethod
	public void onEvent1(ButtonPressEvent event) {
		System.out.println("press " + event.getButton().getName());
	}
	
	@EventMethod
	public void onButton2(ButtonReleaseEvent event) {
		System.out.println("release " + event.getButton().getName());
		
		Button button = event.getButton();
		if(!button.getName().equals("brush_size_button")) return;
		
		Options.brush_size *= 2;
		if(Options.brush_size > 4) Options.brush_size = 1;
		
		button.setIcon(new Image("/resources/icons/bs" + Options.brush_size + ".png"));
	}

}
