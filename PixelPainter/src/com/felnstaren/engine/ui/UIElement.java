package com.felnstaren.engine.ui;

import com.felnstaren.engine.Renderer;

public abstract class UIElement {

	protected int x, y, width, height;
	
	public UIElement(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	
	
	public abstract void render(Renderer renderer);
	
	
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	
	
	public boolean isInside(int x, int y) {
		return x >= this.x &&
				y >= this.y &&
				x <= this.x + this.width &&
				y <= this.y + this.height;
	}
	
}
