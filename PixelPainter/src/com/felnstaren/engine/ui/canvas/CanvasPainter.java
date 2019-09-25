package com.felnstaren.engine.ui.canvas;

import com.felnstaren.engine.event.EventHandler;
import com.felnstaren.engine.event.events.CanvasPaintEvent;
import com.felnstaren.engine.gfx.Image;

public class CanvasPainter {
	
	private EventHandler ehand;
	
	public CanvasPainter(EventHandler ehand) {
		this.ehand = ehand;
	}

	
	
	public void setPixel(Canvas canvas, int x, int y, int color) {
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		
		if(x < 0 || x >= width || y < 0 || y >= height) return;
		
		if(canvas.getPixels()[x + y * width] == color) return;
		
		CanvasPaintEvent cpe = new CanvasPaintEvent(canvas, x, y, canvas.getPixels()[x + y * width], color);
		
		ehand.trigger(cpe);
		
		if(cpe.isCancelled()) return;
		
		int alpha = ((color >> 24) & 0xff);
		int index = x + y * width;
		
		if(alpha == 0 || color == 0xffff00ff) return;
		
		if(alpha == 255) canvas.getPixels()[index] = color;
		else {
			int pcolor = canvas.getPixels()[index];
			
			int r = ((pcolor >> 16) & 0xff) - (int)((((pcolor >> 16) & 0xff) - ((color >> 16) & 0xff)) * (alpha/255f));
			int g = ((pcolor >> 8) & 0xff) - (int)((((pcolor >> 8) & 0xff) - ((color >> 8) & 0xff)) * (alpha/255f));
			int b = (pcolor & 0xff) - (int)((((pcolor) & 0xff) - ((color) & 0xff)) * (alpha/255f));
			
			canvas.getPixels()[index] = (r << 16 | g << 8 | b);
		}
	}
	
	public void drawImage(Canvas canvas, Image image, int offX, int offY) {
		//if(image.isAlpha() && !processing) return;
		
		int pw = canvas.getWidth();
		int ph = canvas.getHeight();
		
		//Dont render if it is completely off screen
		if(offX  < -image.getWidth()) return;
		if(offY < -image.getHeight()) return;
		if(offX  >= pw) return;
		if(offY >= ph) return;
		
		int newY = 0;
		int newX = 0;
		int new_width = image.getWidth();
		int new_height = image.getHeight();
		
		//Clipping off pixels that are off of the screen
		if(new_width + offX > pw) new_width -= (new_width + offX - pw);
		if(new_height + offY > pw) new_height -= (new_height + offY - ph);
		if(newX + offX < 0) newX -= offX;
		if(newY + offY < 0) newY -= offY;
		
		
		for(int y = newY; y < new_height; y++) {
			for(int x = newX; x < new_width; x++) {
				setPixel(canvas, x + offX, y + offY, image.getPixel(x + y * image.getWidth()));
			}
		}
	}
	
	
	
	public void dot(Canvas canvas, int x, int y, int r, int color) {
		int d = r*r;

		for (int i = y-r; i <= y+r; i++) {
		    for (int j = x; (j-x)*(j-x) + (i-y)*(i-y) <= d; j--) setPixel(canvas, j, i, color);
		    for (int j = x+1; (j-x)*(j-x) + (i-y)*(i-y) <= d; j++) setPixel(canvas, j, i, color);
		}
	}
	
	
	
	public void line(Canvas canvas, int x0, int y0, int x1, int y1, int color, int width) {
		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);
		
		int sx = x0 < x1 ? 1 : -1;
		int sy = y0 < y1 ? 1 : -1;
		
		int err = dx - dy;
		int err2;
		
		while(true) {
			if(x0 == x1 && y0 == y1) break;
			
			err2 = 2 * err;
			if(err2 > -1 * dy) {
				err -= dy;
				x0 += sx;
			}
			
			if(err2 < dx) {
				err += dx;
				y0 += sy;
			}
			
			if(width > 1) dot(canvas, x0, y0, width / 2, color);
			else setPixel(canvas, x0, y0, color);
		}
	}
	
}
