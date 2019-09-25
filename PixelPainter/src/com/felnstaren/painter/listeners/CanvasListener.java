package com.felnstaren.painter.listeners;

import com.felnstaren.engine.event.EventMethod;
import com.felnstaren.engine.event.Listener;
import com.felnstaren.engine.event.events.CanvasPaintEvent;

public class CanvasListener extends Listener {

	@EventMethod
	public void onPaint(CanvasPaintEvent event) {
		System.out.println("Pixel at " + event.getPixelX() + " " + event.getPixelY() + " changed from " + event.getOriginalPixel() + " to " + event.getNewPixel());
	}
	
}
