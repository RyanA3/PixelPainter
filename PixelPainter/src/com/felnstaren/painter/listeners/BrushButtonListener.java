package com.felnstaren.painter.listeners;

import java.util.Arrays;
import java.util.List;

import com.felnstaren.engine.event.EventMethod;
import com.felnstaren.engine.event.Listener;
import com.felnstaren.engine.event.events.ButtonPressEvent;
import com.felnstaren.engine.ui.canvas.Brush;
import com.felnstaren.engine.ui.canvas.BrushMode;

public class BrushButtonListener extends Listener {
	
	private static List<BrushMode> cycle = Arrays.asList(BrushMode.FREEHAND, BrushMode.LINE);

	private Brush brush;
	
	public BrushButtonListener(Brush brush) {
		this.brush = brush;
	}
	
	@EventMethod
	public void onBrushButton(ButtonPressEvent event) {
		if(!event.getButton().getName().equals("brush_mode_button")) return;
		
		if(cycle.size() - 1 == cycle.indexOf(brush.getMode())) brush.setMode(cycle.get(0));
		else brush.setMode(cycle.get(cycle.indexOf(brush.getMode()) + 1));
		
		event.getButton().setIcon(brush.getMode().getIcon());
	}
	
}
