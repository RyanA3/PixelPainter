package com.felnstaren.painter.listeners;

import java.awt.event.KeyEvent;

import com.felnstaren.engine.event.EventMethod;
import com.felnstaren.engine.event.Listener;
import com.felnstaren.engine.event.events.KeyDownEvent;
import com.felnstaren.engine.ui.canvas.UndoManager;

public class KeybindListener extends Listener {

	private UndoManager uman;
	
	public KeybindListener(UndoManager uman) {
		this.uman = uman;
	}
	
	
	
	@EventMethod
	public void onKeyDown(KeyDownEvent event) {
		if(!event.getKeyMap()[KeyEvent.VK_CONTROL]) return;
		System.out.println("Control key down: " + KeyEvent.getKeyText(event.getKey()));
		
		if(event.getKey() == KeyEvent.VK_Z) {
			new java.util.Timer().schedule( 
				new java.util.TimerTask() {
					public void run() {
						uman.undoLast();
			        }
			    }, 1000 
			);
		}
	}
	
}
