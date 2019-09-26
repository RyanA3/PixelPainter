package com.felnstaren.painter;

import com.felnstaren.engine.AbstractApp;
import com.felnstaren.engine.AppContainer;
import com.felnstaren.engine.Renderer;
import com.felnstaren.engine.event.EventHandler;
import com.felnstaren.engine.gfx.Image;
import com.felnstaren.engine.ui.button.Button;
import com.felnstaren.engine.ui.button.ButtonHandler;
import com.felnstaren.engine.ui.button.radio.RadioButtonGroup;
import com.felnstaren.engine.ui.canvas.Brush;
import com.felnstaren.engine.ui.canvas.BrushMode;
import com.felnstaren.engine.ui.canvas.Canvas;
import com.felnstaren.engine.ui.canvas.CanvasPainter;
import com.felnstaren.painter.engine_extension.ColorSelectorButton;
import com.felnstaren.painter.listeners.BrushButtonListener;
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
	private Brush brush;
	
	public PixelPainter() {
		this.hud = new HUD();
	}
	
	

	public void init(AppContainer ac) {
		this.canvas = new Canvas(0, 31, ac.getWidth(), ac.getHeight() - 31);
		this.brush = new Brush(50, 50);
		brush.setMode(BrushMode.FREEHAND);
		
		this.ehandler = new EventHandler();
		ehandler.addListener(new TestListener());
		ehandler.addListener(new CanvasListener());
		ehandler.addListener(new ColorListener());
		ehandler.addListener(new ResetListener(canvas));
		ehandler.addListener(new BrushButtonListener(brush));
		
		this.bhandler = new ButtonHandler(ehandler);
		bhandler.addButton(new Button(2, 18, 30, 10, "brush_size_button", new Image("/resources/icons/bs1.png")));
		bhandler.addButton(new Button(36, 18, 30, 10, "reset", new Image("/resources/icons/reset.png")));
		bhandler.addButton(new Button(70, 18, 30, 10, "brush_mode_button", brush.getMode().getIcon()));
		
		RadioButtonGroup color = new RadioButtonGroup();
		color.addButton(new ColorSelectorButton(50, 1, 6, 6, "color_black", 0xff000000));
		color.addButton(new ColorSelectorButton(58, 1, 6, 6, "color_red", 0xffFF0000));
		color.addButton(new ColorSelectorButton(66, 1, 6, 6, "color_green", 0xff00FF00));
		color.addButton(new ColorSelectorButton(74, 1, 6, 6, "color_blue", 0xff0000FF));
		color.addButton(new ColorSelectorButton(50, 9, 6, 6, "color_yellow", 0xffFFFF00));
		color.addButton(new ColorSelectorButton(58, 9, 6, 6, "color_orange", 0xffFF8800));
		color.addButton(new ColorSelectorButton(66, 9, 6, 6, "color_tr_blue", 0x880000ff));
		bhandler.addRadioGroup(color);
		
		this.cpaint = new CanvasPainter(ehandler);
	}

	public void update(AppContainer ac, float delta_time) {
		bhandler.update(ac);
		
		brush.update(ac, cpaint, canvas);
	}

	public void render(AppContainer ac, Renderer renderer) {
		hud.render(renderer, ac);
		bhandler.render(renderer);
		canvas.render(renderer);
		brush.render(renderer);
		
		renderer.circle(50, 50, 20, Options.color, Options.brush_size);
		renderer.dot(100, 100, 20, Options.color);
	}
	
	
	
	public static void main(String[] args) {
		AppContainer ac = new AppContainer(new PixelPainter());
		ac.start();
	}

}
