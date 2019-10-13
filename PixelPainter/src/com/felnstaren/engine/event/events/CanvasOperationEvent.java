package com.felnstaren.engine.event.events;

import java.util.ArrayList;
import java.util.List;

import com.felnstaren.engine.Renderer;
import com.felnstaren.engine.ui.canvas.Canvas;

public class CanvasOperationEvent extends CanvasEvent {
	
	private int[] pixels;
	private int[] old_pixels;
	private int edited = 0;

	public CanvasOperationEvent(Canvas canvas) {
		super(canvas);
		this.pixels = canvas.getPixels().clone();
		this.old_pixels = canvas.getPixels().clone();
	}
	
	
	public void apply() {
		canvas.setPixels(pixels);
	}
	
	public void undo() {
		canvas.setPixels(old_pixels);
	}
	
	
	
	public int[] getNewPixels() {
		return this.pixels;
	}
	
	public int[] getOldPixels() {
		return this.old_pixels;
	}
	
	public List<Integer> getChangedPixelLocations() {
		List<Integer> changed = new ArrayList<Integer>();
		
		for(int i = 0; i < pixels.length; i++) {
			if(pixels[i] != old_pixels[i]) {
				changed.add(i);
			}
		}
		
		return changed;
	}
	
	public int getTotalEdited() {
		return this.edited;
	}
	
	public void setTotalEdited(int edited) {
		this.edited = edited;
	}
	
	
	
	public void setPixel(int x, int y, int color) {
		if(x < 0 || x >= canvas.getWidth() || y < 0 || y >= canvas.getHeight()) return;
		
		int index = x + y * canvas.getWidth();
		int old_color = old_pixels[index];
		int new_color = Renderer.blend(color, old_color);
		
		pixels[index] = new_color;
		
		edited++;
	}
	
	public void setPixels(int[] px) {
		this.pixels = px.clone();
	}

}
