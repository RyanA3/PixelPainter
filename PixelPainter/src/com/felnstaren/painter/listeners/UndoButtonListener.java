package com.felnstaren.painter.listeners;

import com.felnstaren.engine.event.EventMethod;
import com.felnstaren.engine.event.Listener;
import com.felnstaren.engine.event.events.ButtonPressEvent;
import com.felnstaren.engine.ui.canvas.UndoManager;

public class UndoButtonListener extends Listener {

	private UndoManager uman;
	
	public UndoButtonListener(UndoManager uman) {
		this.uman = uman;
	}
	
	@EventMethod
	public void onUndo(ButtonPressEvent event) {
		if(event.getButton().getName().equals("undo_button")) {
			System.out.println("undo button");
			uman.undoLast();
		} else if(event.getButton().getName().equals("redo_button")) {
			System.out.println("redo button");
			uman.redoLast();
		}
	}
	
}
