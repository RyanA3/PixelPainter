package com.felnstaren.engine.ui.canvas;

import java.util.Stack;

import com.felnstaren.engine.event.events.CanvasOperationEvent;

public class UndoManager {

	private Stack<CanvasOperationEvent> undo_list;
	private Stack<CanvasOperationEvent> redo_list;
	private int max = 3;
	
	
	public UndoManager() {
		this.undo_list = new Stack<CanvasOperationEvent>();
		this.redo_list = new Stack<CanvasOperationEvent>();
		
		undo_list.ensureCapacity(max + 1);
		redo_list.ensureCapacity(max + 1);
	}
	
	
	
	public void addOperation(CanvasOperationEvent operation) {
		undo_list.push(operation);
		trim();
	}
	
	public void undoLast() {
		if(undo_list.size() <= 0) return;
		
		CanvasOperationEvent operation = undo_list.pop();
		operation.undo();
		
		redo_list.push(operation);
		trim();
	}
	
	public void redoLast() {
		if(redo_list.size() <= 0) return;
		
		CanvasOperationEvent operation = redo_list.pop();
		operation.apply();
		
		undo_list.push(operation);
		trim();
	}
	
	
	private void trim() {
		if(undo_list.size() > max) {
			undo_list.removeElementAt(0);
			System.out.println("trimmed operation from undos");
		}
		if(redo_list.size() > max) {
			redo_list.removeElementAt(0);
			System.out.println("trimmed operation from redos");
		}
	}
}
