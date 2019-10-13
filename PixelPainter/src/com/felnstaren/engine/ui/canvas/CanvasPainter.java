package com.felnstaren.engine.ui.canvas;

import com.felnstaren.engine.Renderer;
import com.felnstaren.engine.event.EventHandler;
import com.felnstaren.engine.event.events.CanvasOperationEvent;
import com.felnstaren.engine.gfx.Image;

public class CanvasPainter {
	
	private EventHandler ehand;
	private int[] pixel_operation;
	private int width, height, offX, offY, edited;
	
	public CanvasPainter(EventHandler ehand) {
		this.ehand = ehand;
	}

	
	
	public void prime(Canvas canvas) {
		this.pixel_operation = canvas.getPixels().clone();
		this.width = canvas.getWidth();
		this.height = canvas.getHeight();
		this.offX = canvas.getX();
		this.offY = canvas.getY();
		this.edited = 0;
	}
	
	public void export(Canvas canvas) {
		if(edited == 0) return;
		CanvasOperationEvent operation = new CanvasOperationEvent(canvas);
		operation.setPixels(this.pixel_operation);
		operation.setTotalEdited(edited);
		ehand.trigger(operation);
		if(!operation.isCancelled()) operation.apply();
	}
	
	public int[] getCurrentEdit() {
		return this.pixel_operation;
	}
	
	public void renderCurrentEdit(Renderer renderer) {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(pixel_operation[x + y * width] != 0xffF8F8F8) renderer.hardSetPixel(x + offX, y + offY, pixel_operation[x + y * width]);
			}
		}
	}
	
	
	
	
	public void setPixel(Canvas canvas, int x, int y, int color) {
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		int index = x + y * width;
		
		if(x < 0 || x >= width || y < 0 || y >= height) return;
		if(canvas.getPixels()[index] == color) return;
		
		int new_color = Renderer.blend(color, canvas.getPixels()[index]);
		//CanvasPaintEvent cpe = new CanvasPaintEvent(canvas, x, y, canvas.getPixels()[index], new_color);
		//ehand.trigger(cpe);
		
		//if(cpe.isCancelled()) return;
		
		//canvas.getPixels()[index] = new_color;
		
		//event.setPixel(x, y, new_color);
		
		if(new_color == pixel_operation[index]) return;
		pixel_operation[index] = new_color;
		edited++;
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
	
	
	
	public void dot(Canvas canvas, int x_center, int y_center, int r, int color) {
	    int d = r*2;
	    int size = (d << 2);// fixed point value for size
	    int ray = (size >> 1);
	    int dY2;
	    int ray2 = ray * ray;
	    int posmin,posmax;
	    int Y,X;
	    int x = ((d&1)==1) ? ray : ray - 2;
	    int y = 2;
	    x_center -= (d>>1);
	    y_center -= (d>>1);

	    main: for (;; y+=4)
	    {
	        dY2 = (ray - y) * (ray - y);

	        for (;; x-=4)
	        {
	            if (dY2 + (ray - x) * (ray - x) <= ray2) continue;

	            if (x < y)
	            {
	                Y = (y >> 2);
	                posmin = Y;
	                posmax = d - Y;

	                // Draw inside square and leave
	                while (Y < posmax)
	                {
	                    for (X = posmin; X < posmax; X++)
	                    	setPixel(canvas, x_center+X, y_center+Y, color);
	                    Y++;
	                }
	                break main;
	            }

	            // Draw the 4 borders
	            X = (x >> 2) + 1;
	            Y = y >> 2;
	            posmax = d - X;
	            int mirrorY = d - Y - 1;

	            while (X < posmax)
	            {
	            	setPixel(canvas, x_center+X, y_center+Y, color);
	            	setPixel(canvas, x_center+X, y_center+mirrorY, color);
	            	setPixel(canvas, x_center+Y, y_center+X, color);
	            	setPixel(canvas, x_center+mirrorY, y_center+X, color);
	                X++;
	            }
	            break;
	        }
	    }
	    
	    
	    //if(event.getTotalEdited() == 0) return;
	    //ehand.trigger(event);
	    //if(!event.isCancelled()) event.apply();
	}
	
	public void oDot(Canvas canvas, int x, int y, int r, int color) {
		if(r < 1) setPixel(canvas, x, y, color);
		else dot(canvas, x, y, r, color);
	}

	
	public void circle(Canvas canvas, int x_center, int y_center, int r, int color, int thickness) {
		int x = r, y = 0;
		thickness /= 2;
		
		//oDot(canvas, x_center, y_center, r, color);
		//oDot(canvas, x_center, y_center, r - thickness, 0xffFDFDFD);
		
	    if (r > 0) 
	    { 
	    	oDot(canvas, x_center + r, y_center, thickness, color);
	    	oDot(canvas, x_center, y_center + r, thickness, color);
	    	oDot(canvas, x_center - r, y_center, thickness, color);
	    	oDot(canvas, x_center, y_center - r, thickness, color);
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
	        oDot(canvas, x + x_center, y + y_center, thickness, color);
	        oDot(canvas, -x + x_center, y + y_center, thickness, color);
	        oDot(canvas, x + x_center, -y + y_center, thickness, color);
	        oDot(canvas, -x + x_center, -y + y_center, thickness, color);
	        
	        // If the generated point is on the line x = y then  
	        // the perimeter points have already been printed 
	        if (x != y) 
	        { 
	        	oDot(canvas, y + x_center, x + y_center, thickness, color);
	        	oDot(canvas, -y + x_center, x + y_center, thickness, color);
	        	oDot(canvas, y + x_center, -x + y_center, thickness, color);
	        	oDot(canvas, -y + x_center, -x + y_center, thickness, color);
	        } 
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
	
	
	
	public void clear(Canvas canvas) {
		for(int y = 0; y < canvas.getHeight(); y++) {
			for(int x = 0; x < canvas.getWidth(); x++) {
				setPixel(canvas, x, y, 0xffF8F8F8);
			}
		}
	}
	
}
