package com.felnstaren.engine.ui.button;

import com.felnstaren.engine.Renderer;
import com.felnstaren.engine.gfx.Image;
import com.felnstaren.engine.ui.UIElement;

public class Button extends UIElement {

	protected String name;
	protected boolean pressed = false;
	
	protected Image icon;
	
	public Button(int x, int y, int width, int height, String name, Image icon) {
		super(x, y, width, height);
		this.name = name;
		this.icon = icon;
	}
	
	
	
	public void render(Renderer renderer) {
		renderer.drawRect(x, y, width, height, getOutline());
		renderer.fillRect(x, y, width, height, getColor());
		renderer.line(x, y, x + width, y, 0xffEEEEEE);
		renderer.line(x, y, x, y + height, 0xffEEEEEE);
		renderer.setPixel(x, y, 0xffEEEEEE);
		renderer.setPixel(x + width, y, 0xffBBBBBB);
		renderer.setPixel(x, y + height, 0xffBBBBBB);
		
		if(icon != null) renderer.drawImage(icon, x + (width / 2 - icon.getWidth() / 2), y + (height / 2 - icon.getHeight() / 2));
	}
	
	
	
	public boolean isPressed() {
		return this.pressed;
	}
	
	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}
	
	public int getColor() {
		if(pressed) return 0xffAAAAAA;
		else return 0xffDDDDDD;
	}
	
	public int getOutline() {
		return 0xff777777;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	public void setIcon(Image image) {
		this.icon = image;
	}
	
}
