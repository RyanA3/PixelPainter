package com.felnstaren.painter.listeners;

import com.felnstaren.engine.event.EventMethod;
import com.felnstaren.engine.event.Listener;
import com.felnstaren.engine.event.events.ButtonPressEvent;
import com.felnstaren.engine.ui.canvas.Canvas;
import com.felnstaren.engine.ui.canvas.CanvasPainter;

public class ResetListener extends Listener {
	
	private CanvasPainter cpaint;
	private Canvas canvas;
	
	public ResetListener(CanvasPainter cpaint, Canvas canvas) {
		this.cpaint = cpaint;
		this.canvas = canvas;
	}

	@EventMethod
	public void onPressReset(ButtonPressEvent event) {
		if(!event.getButton().getName().equals("reset")) return;
		System.out.println("cleared the canvas");
		cpaint.clear(canvas);
		cpaint.export(canvas);
	}
	
}
