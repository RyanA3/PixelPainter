package com.felnstaren.engine.ui.canvas;

import com.felnstaren.engine.gfx.Image;

public enum BrushMode {

	FREEHAND(new Image("/resources/icons/pencil_brush.png")),
	LINE(new Image("/resources/icons/line_brush.png")),
	FILL_CIRCLE(new Image("/resources/icons/circle_fill.png")),
	CIRCLE(new Image("/resources/icons/circle.png"));
	
	private Image icon;
	
	private BrushMode(Image icon) {
		this.icon = icon;
	}
	
	
	
	public Image getIcon() {
		return this.icon;
	}
	
}
