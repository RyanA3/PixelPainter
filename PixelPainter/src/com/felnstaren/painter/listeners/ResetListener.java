package com.felnstaren.painter.listeners;

import com.felnstaren.engine.event.EventMethod;
import com.felnstaren.engine.event.Listener;
import com.felnstaren.engine.event.events.ButtonPressEvent;
import com.felnstaren.engine.ui.canvas.Canvas;

public class ResetListener extends Listener {
	
	public Canvas canvas;
	
	public ResetListener(Canvas canvas) {
		this.canvas = canvas;
	}

	@EventMethod
	public void onPressReset(ButtonPressEvent event) {
		if(!event.getButton().getName().equals("reset")) return;
		System.out.println("cleared the canvas");
		canvas.clear();
	}
	
}
