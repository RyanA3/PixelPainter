package com.felnstaren.painter;

import java.awt.event.MouseEvent;

import com.felnstaren.engine.AbstractApp;
import com.felnstaren.engine.AppContainer;
import com.felnstaren.engine.Renderer;
import com.felnstaren.engine.event.EventHandler;
import com.felnstaren.engine.gfx.Image;
import com.felnstaren.engine.ui.button.Button;
import com.felnstaren.engine.ui.button.ButtonHandler;
import com.felnstaren.engine.ui.button.radio.RadioButtonGroup;
import com.felnstaren.engine.ui.canvas.Canvas;
import com.felnstaren.engine.ui.canvas.CanvasPainter;
import com.felnstaren.painter.engine_extension.ColorSelectorButton;
import com.felnstaren.painter.listeners.CanvasListener;
import com.felnstaren.painter.listeners.ColorListener;
import com.felnstaren.painter.listeners.ResetListener;
import com.felnstaren.painter.listeners.TestListener;

public class PixelPainter extends AbstractApp {
	
	private HUD hud;
	private EventHandler ehandler;
	private ButtonHandler bhandler;
	private Canvas canvas;
	private CanvasPainter cpaint;
	
	private int x1, y1, x2, y2 = 0;
	
	public PixelPainter() {
		this.hud = new HUD();
	}
	
	

	public void init(AppContainer ac) {
		this.canvas = new Canvas(0, 31, ac.getWidth(), ac.getHeight() - 31);
		this.ehandler = new EventHandler();
		ehandler.addListener(new TestListener());
		ehandler.addListener(new CanvasListener());
		ehandler.addListener(new ColorListener());
		ehandler.addListener(new ResetListener(canvas));
		
		this.bhandler = new ButtonHandler(ehandler);
		bhandler.addButton(new Button(2, 18, 30, 10, "brush_size_button", new Image("/resources/icons/bs1.png")));
		bhandler.addButton(new Button(36, 18, 30, 10, "reset", new Image("/resources/icons/reset.png")));
		
		RadioButtonGroup color = new RadioButtonGroup();
		color.addButton(new ColorSelectorButton(50, 1, 6, 6, "color_black", 0xff000000));
		color.addButton(new ColorSelectorButton(58, 1, 6, 6, "color_red", 0xffFF0000));
		color.addButton(new ColorSelectorButton(66, 1, 6, 6, "color_green", 0xff00FF00));
		color.addButton(new ColorSelectorButton(74, 1, 6, 6, "color_blue", 0xff0000FF));
		color.addButton(new ColorSelectorButton(50, 9, 6, 6, "color_yellow", 0xffFFFF00));
		color.addButton(new ColorSelectorButton(58, 9, 6, 6, "color_orange", 0xffFF8800));
		color.addButton(new ColorSelectorButton(66, 9, 6, 6, "color_tr_blue", 0x110000ff));
		bhandler.addRadioGroup(color);
		
		this.cpaint = new CanvasPainter(ehandler);
	}

	public void update(AppContainer ac, float delta_time) {
		bhandler.update(ac);
		x2 = x1;
		y2 = y1;
		x1 = ac.getInput().getMouseX();
		y1 = ac.getInput().getMouseY();
		
		if(ac.getInput().isButtonPressed(MouseEvent.BUTTON1)) {
			if(ac.getInput().getMouseX() < 0 || ac.getInput().getMouseX() >= ac.getWidth() || ac.getInput().getMouseY() < 0 || ac.getInput().getMouseY() >= ac.getHeight()) return;
			
			if(canvas.isInside(ac.getInput().getMouseX(), ac.getInput().getMouseY())) {
				int offX1 = x1 - canvas.getX();
				int offY1 = y1 - canvas.getY();
				int offX2 = x2 - canvas.getX();
				int offY2 = y2 - canvas.getY();
				
				//canvas.dot(offX, offY, Options.brush_size, Options.color);
				//cpaint.dot(canvas, offX1, offY1, Options.brush_size, Options.color);
				if(offX1 == offX2 && offY1 == offY2) {
					if(Options.brush_size > 1) cpaint.dot(canvas, offX1, offY1, Options.brush_size / 2, Options.color);
					else cpaint.setPixel(canvas, offX1, offY1, Options.color);
				}
				cpaint.line(canvas, offX1, offY1, offX2, offY2, Options.color, Options.brush_size);
			}
		}
	}

	public void render(AppContainer ac, Renderer renderer) {
		hud.render(renderer, ac);
		bhandler.render(renderer);
		canvas.render(renderer);
	}
	
	
	
	public static void main(String[] args) {
		AppContainer ac = new AppContainer(new PixelPainter());
		ac.start();
	}

}
