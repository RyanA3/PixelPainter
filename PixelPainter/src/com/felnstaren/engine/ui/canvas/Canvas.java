package com.felnstaren.engine.ui.canvas;

import com.felnstaren.engine.Renderer;
import com.felnstaren.engine.ui.UIElement;

public class Canvas extends UIElement {
	
	private int pixels[];
	
	public Canvas(int x, int y, int width, int height) {
		super(x, y, width, height);
		
		this.pixels = new int[width * height];
		for(int i = 0; i < pixels.length; i++) pixels[i] = 0xffF8F8F8;
	}
	
	
	
	public void render(Renderer renderer) {
		for(int px = 0; px < width; px++) {
			for(int py = 0; py < height; py++) {
				renderer.hardSetPixel(px + x, py + y, pixels[px + py * width]);
			}
		}
	}
	
	

	
	public int[] getPixels() {
		return this.pixels;
	}
	
	
	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
}
