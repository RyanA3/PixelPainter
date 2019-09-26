package com.felnstaren.engine;

import java.awt.image.DataBufferInt;

import com.felnstaren.engine.gfx.Font;
import com.felnstaren.engine.gfx.Image;
import com.felnstaren.engine.ui.canvas.Canvas;
import com.felnstaren.engine.ui.canvas.CanvasPainter;

public class Renderer {
	private int pixel_width, pixel_height;
	private int[] pixels;

	private boolean processing = false;
	
	public Renderer(AppContainer ac) {
		pixel_width = ac.getWidth();
		pixel_height = ac.getHeight();
		pixels = ((DataBufferInt) ac.getWindow().getImage().getRaster().getDataBuffer()).getData();
	}
	
	
	
	
	
	public void clear() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	public void process() {
		processing = true;
		
		processing = false;
	}
	
	public void setPixel(int x, int y, int value, int pw, int ph, int[] px) {
		int alpha = ((value >> 24) & 0xff);
		
		if(x < 0 || x >= pw || y < 0 || y >= ph || alpha == 0 || value == 0xffff00ff) return; //0xffff00ff is a color (255 red, 0 green, 255 blue), this lets it work as transparent
		
		int index = x + y * pw;
		
		
		if(alpha == 255) {
			px[index] = value;
		} else {
			int pcolor = px[index];
			
			int r = ((pcolor >> 16) & 0xff) - (int)((((pcolor >> 16) & 0xff) - ((value >> 16) & 0xff)) * (alpha/255f));
			int g = ((pcolor >> 8) & 0xff) - (int)((((pcolor >> 8) & 0xff) - ((value >> 8) & 0xff)) * (alpha/255f));
			int b = (pcolor & 0xff) - (int)((((pcolor) & 0xff) - ((value) & 0xff)) * (alpha/255f));
			
			px[index] = (r << 16 | g << 8 | b);
		}
	}
	
	public void hardSetPixel(int x, int y, int value) {
		pixels[x + y * pixel_width] = value;
	}
	
	public void setPixel(int x, int y, int value) {
		setPixel(x, y, value, pixel_width, pixel_height, pixels);
	}
	
	
	
	public void drawImage(Image image, int offX, int offY, int pw, int ph, int[] pixels, CanvasPainter cpaint, Canvas canvas) {
		if(image.isAlpha() && !processing) return;
		
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
				if(cpaint != null) cpaint.setPixel(canvas, x, y, image.getPixels()[x + y * image.getWidth()]);
				else setPixel(x + offX, y + offY, image.getPixels()[x + y * image.getWidth()], pw, ph, pixels);
			}
		}
	}
	
	public void drawImage(Image image, int offX, int offY) {
		drawImage(image, offX, offY, pixel_width, pixel_height, pixels, null, null);
	}
	
	
	
	public void drawText(String text, Font font, int offX, int offY, int color) {
		int offset = 0;
		
		for(int i = 0; i < text.length(); i++) {
			int unicode = text.codePointAt(i);
			
			for(int y = 0; y < font.getFontImage().getHeight(); y++) {
				for(int x = 0; x < font.getWidth(unicode); x++) {
					if(font.getFontImage().getPixel((x + font.getOffset(unicode)) + y * font.getFontImage().getWidth()) == 0xffffffff) {
						setPixel(x + offX + offset, y + offY, color);
					}
				}
			}
			
			offset += font.getWidth(unicode);
		}
	}
	
	
	
	public void fillRect(int offX, int offY, int width, int height, int color) {
		if(offX < -width) return;
		if(offY < -height) return;
		if(offX >= pixel_width) return;
		if(offY >= pixel_height) return;
		
		int newX = 0;
		int newY = 0;
		int new_height = height;
		int new_width = width;
		
		if(offX < 0) newX -= offX;
		if(offY < 0) newY -= offY;
		if(new_width + offX >= pixel_width) new_width -= new_width + offX - pixel_width;
		if(new_height + offY >= pixel_height) new_height -= new_height + offY - pixel_height;
		
		for(int y = newY; y < new_height; y++) {
			for(int x = newX; x < new_width; x++) {
				setPixel(x + offX, y + offY, color);
			}
		}
	}
	
	public void drawRect(int x, int y, int width, int height, int color) {
		//Render the outlines
		for(int offY = 0; offY < height; offY++) {
			setPixel(x, y + offY, color);
			setPixel(x + width, y + offY, color);
		}
		
		for(int offX = 0; offX < width; offX++) {
			setPixel(x + offX, y, color);
			setPixel(x + offX, y + height, color);
		}
	}
	
	
	
	public void dot(int x, int y, int r, int color) {
		//https://stackoverflow.com/questions/40779343/java-loop-through-all-pixels-in-a-2d-circle-with-center-x-y-and-radius
		int r2 = r*r;
		// iterate through all x-coordinates
		for (int i = y-r; i <= y+r; i++) {
		    // test upper half of circle, stopping when top reached
		    for (int j = x; (j-x)*(j-x) + (i-y)*(i-y) <= r2; j--) {
		        setPixel(j, i, color, pixel_width, pixel_height, pixels);
		    }
		    // test bottom half of circle, stopping when bottom reached
		    for (int j = x+1; (j-x)*(j-x) + (i-y)*(i-y) <= r2; j++) {
		        setPixel(j, i, color, pixel_width, pixel_height, pixels);
		    }
		}
	}

	
	public void circle(int x_center, int y_center, int r, int color, int thickness) {
		int x = r, y = 0;
		
	    if (r > 0) 
	    { 
	    	setPixel(x_center + r, y_center, color);
	    	setPixel(x_center, y_center + r, color);
	    	setPixel(x_center - r, y_center, color);
	    	setPixel(x_center, y_center - r, color);
	    } 
		
		int P = 1 - r; 
	    while (x > y) 
	    {  
	        y++; 
	          
	        // Mid-point is inside or on the perimeter 
	        if (P <= 0) 
	            P = P + 2*y + 1; 
	              
	        // Mid-point is outside the perimeter 
	        else
	        { 
	            x--; 
	            P = P + 2*y - 2*x + 1; 
	        } 
	          
	        // All the perimeter points have already been printed 
	        if (x < y) 
	            break; 
	          
	        // Printing the generated point and its reflection 
	        // in the other octants after translation 
	        setPixel(x + x_center, y + y_center, color);
	        setPixel(-x + x_center, y + y_center, color);
	        setPixel(x + x_center, -y + y_center, color);
	        setPixel(-x + x_center, -y + y_center, color);
	          
	        // If the generated point is on the line x = y then  
	        // the perimeter points have already been printed 
	        if (x != y) 
	        { 
	        	setPixel(y + y_center, x + x_center, color);
	        	setPixel(-y + y_center, x + x_center, color);
	        	setPixel(y + y_center, -x + x_center, color);
	        	setPixel(-y + y_center, -x + x_center, color);
	        } 
	    }  
	}
	
	
	
	public void line(int x0, int y0, int x1, int y1, int color, int width) {
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
			
			if(width > 1) dot(x0, y0, width / 2, color);
			else setPixel(x0, y0, color);
		}
	}
}
