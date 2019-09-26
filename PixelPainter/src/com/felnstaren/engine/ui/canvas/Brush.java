package com.felnstaren.engine.ui.canvas;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.felnstaren.engine.AppContainer;
import com.felnstaren.engine.Input;
import com.felnstaren.engine.Renderer;
import com.felnstaren.engine.gfx.Image;
import com.felnstaren.engine.ui.UIElement;
import com.felnstaren.painter.Options;

public class Brush extends UIElement {
	
	private BrushMode mode;
	
	private int x1, y1, x2, y2 = -1;

	public Brush(int x, int y) {
		super(x, y, 5, 5);
		this.mode = BrushMode.FREEHAND;
	}
	
	
	
	public void update(AppContainer ac, CanvasPainter cpaint, Canvas canvas) {
		Input in = ac.getInput();
		x = in.getMouseX();
		y = in.getMouseY();
		
		switch(mode) {
			case FREEHAND: {
				x2 = x1;
				y2 = y1;
				x1 = x;
				y1 = y;
				
				if(!in.isButtonPressed(MouseEvent.BUTTON1)) break;
				if(x < 0 || x >= ac.getWidth() || y < 0 || y >= ac.getHeight()) break;
					
				if(canvas.isInside(x, y)) {
					int offX1 = x1 - canvas.getX();
					int offY1 = y1 - canvas.getY();
					int offX2 = x2 - canvas.getX();
					int offY2 = y2 - canvas.getY();

					if(offX1 == offX2 && offY1 == offY2) {
						if(Options.brush_size > 1) cpaint.dot(canvas, offX1, offY1, Options.brush_size / 2, Options.color);
						else cpaint.setPixel(canvas, offX1, offY1, Options.color);
					} else cpaint.line(canvas, offX1, offY1, offX2, offY2, Options.color, Options.brush_size);
				}
				break;
			}
			case LINE: {
				if(in.isKeyDown(KeyEvent.VK_ESCAPE)) reset();
				
				if(!in.isButtonDown(MouseEvent.BUTTON1)) break;
				if(!canvas.isInside(x, y)) break;
				if(x1 == -1 && y1 == -1) {
					x1 = x;
					y1 = y;
					System.out.println("set point 1");
				} else if(x2 == -1 && y2 == -1) {
					x2 = x;
					y2 = y;
					System.out.println("set point 2");
					
					int offX1 = x1 - canvas.getX();
					int offY1 = y1 - canvas.getY();
					int offX2 = x2 - canvas.getX();
					int offY2 = y2 - canvas.getY();
					
					cpaint.line(canvas, offX1, offY1, offX2, offY2, Options.color, Options.brush_size);
					reset();
					System.out.println("drew line");
				}
				break;
			}
			default:
				break;
		}
	}
	
	public void render(Renderer renderer) {
		Image img = mode.getIcon();
		
		switch(mode) {
			case FREEHAND: {
				renderer.drawImage(img, this.x - 1, this.y - 9);
				break;
			}
			case LINE: {
				renderer.dot(x, y, 1, Options.color);
				if(x1 != -1 && y1 != -1) renderer.line(x1, y1, x, y, Options.color, Options.brush_size);
				break;
			}
			default: break;
		}
	}
	
	
	
	public void reset() {
		x1 = -1;
		y1 = -1;
		x2 = -1;
		y2 = -1;
	}
	
	
	
	public void setMode(BrushMode mode) {
		this.mode = mode;
		reset();
	}
	
	public BrushMode getMode() {
		return this.mode;
	}

}
