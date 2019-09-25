package com.felnstaren.engine.event.events;

import com.felnstaren.engine.ui.canvas.Canvas;

public class CanvasPaintEvent extends CanvasEvent {
	
	private int px, py, pre, post;

	public CanvasPaintEvent(Canvas canvas, int px, int py, int pre, int post) {
		super(canvas);
		this.px = px;
		this.py = py;
		this.pre = pre;
		this.post = post;
	}
	
	
	
	public int getPixelX() {
		return this.px;
	}
	
	public int getPixelY() {
		return this.py;
	}
	
	public int getOriginalPixel() {
		return this.pre;
	}
	
	public int getNewPixel() {
		return this.post;
	}
	
}
