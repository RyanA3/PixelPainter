package com.felnstaren.engine.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {

	private int width, height;
	private int[] pixels;
	private boolean alpha = false;
	
	public Image(String path) {
		BufferedImage img = null;
		
		try { img = ImageIO.read(Image.class.getResourceAsStream(path)); } 
		catch (IOException e) { e.printStackTrace(); }
		
		width = img.getWidth();
		height = img.getHeight();
		pixels = img.getRGB(0, 0, width, height, null, 0, width);
		
		img.flush();
	}
	
	public Image(int[] pixels, int width, int height) {
		this.pixels = pixels;
		this.width = width;
		this.height = height;
	}
	
	
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int[] getPixels() {
		return this.pixels;
	}

	public int getPixel(int pixel) {
		return this.pixels[pixel];
	}
	
	
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}
	
	public void setPixel(int pixel) {
		this.pixels[pixel] = pixel;
	}
	
	
	public void setAlpha(boolean alpha) {
		this.alpha = alpha;
	}
	
	public boolean isAlpha() {
		return this.alpha;
	}
}
