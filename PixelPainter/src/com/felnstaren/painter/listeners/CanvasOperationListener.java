package com.felnstaren.painter.listeners;

import com.felnstaren.engine.event.EventMethod;
import com.felnstaren.engine.event.Listener;
import com.felnstaren.engine.event.events.CanvasOperationEvent;
import com.felnstaren.engine.ui.canvas.UndoManager;

public class CanvasOperationListener extends Listener {

	private UndoManager uman;
	
	public CanvasOperationListener(UndoManager uman) {
		this.uman = uman;
	}
	
	@EventMethod
	public void onOperation(CanvasOperationEvent event) {
		System.out.println("CanvasOperation>> affected pix: " + event.getTotalEdited());
		uman.addOperation(event);
	}
	
}
