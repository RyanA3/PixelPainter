package com.felnstaren.painter.engine_extension;

import com.felnstaren.engine.Renderer;
import com.felnstaren.engine.ui.button.radio.RadioButton;

public class ColorSelectorButton extends RadioButton {
	
	private int color;

	public ColorSelectorButton(int x, int y, int width, int height, String name, int color) {
		super(x, y, width, height, name);
		this.color = color;
	}
	
	
	
	@Override
	public void render(Renderer renderer) {
		renderer.drawRect(x, y, width, height, getOutline());
		renderer.fillRect(x, y, width, height, getColor());
		renderer.line(x, y, x + width, y, 0xffEEEEEE);
		renderer.line(x, y, x, y + height, 0xffEEEEEE);
		renderer.setPixel(x, y, 0xffEEEEEE);
		renderer.setPixel(x + width, y, 0xffBBBBBB);
		renderer.setPixel(x, y + height, 0xffBBBBBB);
		renderer.fillRect(x + 2, y + 2, width - 3, height - 3, color);
	}
	
	public int getSelectableColor() {
		return this.color;
	}

}
