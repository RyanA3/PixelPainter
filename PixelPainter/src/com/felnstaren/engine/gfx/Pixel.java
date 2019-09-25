package com.felnstaren.engine.gfx;

public class Pixel {
	
	private int x, y, color;
	
	public Pixel(int x, int y, int color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getColor() {
		return this.color;
	}

}
