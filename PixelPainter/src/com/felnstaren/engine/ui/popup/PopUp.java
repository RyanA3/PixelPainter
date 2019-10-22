package com.felnstaren.engine.ui.popup;

import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Set;

import com.felnstaren.engine.AppContainer;
import com.felnstaren.engine.Input;
import com.felnstaren.engine.Renderer;
import com.felnstaren.engine.gfx.Font;
import com.felnstaren.engine.gfx.Image;
import com.felnstaren.engine.ui.UIElement;
import com.felnstaren.engine.ui.button.Button;
import com.felnstaren.engine.ui.button.ButtonHandler;
import com.felnstaren.engine.ui.canvas.Canvas;
import com.felnstaren.engine.util.Point;
import com.felnstaren.engine.util.Util;

public class PopUp extends UIElement {

	private static Image exit_img = new Image("/resources/icons/exit.png");
	
	private boolean grabbed, closed = false;
	private int bar_height = 10;
	private int grabx, graby = 0;
	
	private String title;
	private Canvas canvas;
	private HashMap<UIElement, Point> elements;
	
	public PopUp(String title, int x, int y, int width, int height) {
		super(x, y, width, height);
		//this.bar_height = bar_height;
		this.title = title;
		this.canvas = new Canvas(x + 1, y + bar_height + 1, width - 1, height - bar_height - 1);
		this.elements = new HashMap<UIElement, Point>();
		
		int[] px = new int[canvas.getPixels().length];
		for(int i = 0; i < px.length; i++) px[i] = 0xff222222;
		
		canvas.setPixels(px);
	}
	
	
	
	public void render(Renderer renderer) {
		renderer.drawRect(x, y, width, height, 0xff777777);
		renderer.line(x, y + bar_height, x + width, y + bar_height, 0xff777777, 1);
		renderer.fillRect(x + 1, y + 1, width - 1, bar_height - 1, 0xffAAAAAA);
		renderer.drawText(title, Font.STANDARD, x, y, 0xff000000);
		
		renderer.drawImage(exit_img, x + width - 9, y + 1);
		
		canvas.render(renderer);
		
		for(UIElement element : elements.keySet()) element.render(renderer);
	}
	
	public void update(AppContainer ac) {
		if(closed) return;
		Input input = ac.getInput();
		
		if(!grabbed) {
			if(input.isButtonDown(MouseEvent.BUTTON1)) {
				if(Util.contains(x + width - 9, y + 1, 9, 9, input.getMouseX(), input.getMouseY())) { 
					closed = true;
					return;
				} else if(this.isInside(input.getMouseX(), input.getMouseY())) {
					System.out.println("grabbed");
					grabbed = true; 
					this.grabx = input.getMouseX() - this.x;
					this.graby = input.getMouseY() - this.y;
				}
			}
		} else {
			if(input.getMouseY() - graby <= 0) return;
			
			this.x = input.getMouseX() - grabx;
			this.y = input.getMouseY() - graby;
			
			canvas.setPosition(x + 1, y + bar_height + 1);
			
			for(UIElement element : elements.keySet()) {
				Point p = elements.get(element);
				element.setPosition(x + p.x, y + p.y);
			}
			
			if(input.isButtonUp(MouseEvent.BUTTON1)) {
				grabbed = false;
				System.out.println("released");
			}
		}
	}
	
	public void close(ButtonHandler bhand) {
		for(UIElement element : elements.keySet()) {
			if(element instanceof Button) {
				System.out.println("removing button");
				bhand.removeButton(((Button) element).getName());
			}
		}
	}
	
	
	
	
	public boolean isGrabbed() {
		return this.grabbed;
	}
	
	public boolean isClosed() {
		return this.closed;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	
	public void addElement(UIElement element) {
		elements.put(element, new Point(element.getX(), element.getY()));
		
		element.setPosition(x + element.getX(), y + element.getY());
	}
	
	public void addButton(ButtonHandler bhand, Button button) {
		bhand.addButton(button);
		elements.put(button, new Point(button.getX(), button.getY()));
		
		button.setPosition(x + button.getX(), y + button.getY());
	}
	
	public Set<UIElement> getElements() {
		return this.elements.keySet();
	}

}
