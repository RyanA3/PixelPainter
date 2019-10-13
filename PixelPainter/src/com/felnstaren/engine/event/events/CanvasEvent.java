package com.felnstaren.engine.event.events;

import com.felnstaren.engine.event.Event;
import com.felnstaren.engine.ui.canvas.Canvas;

public class CanvasEvent extends Event {
	
	protected Canvas canvas;
	
	public CanvasEvent(Canvas canvas) {
		super("CanvasEvent");
		this.canvas = canvas;
	}
	
	
	
	public Canvas getCanvas() {
		return this.canvas;
	}

}
