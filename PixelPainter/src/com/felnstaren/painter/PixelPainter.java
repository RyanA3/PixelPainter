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
import com.felnstaren.engine.ui.canvas.UndoManager;
import com.felnstaren.painter.engine_extension.ColorSelectorButton;
import com.felnstaren.painter.listeners.BrushModeListener;
import com.felnstaren.painter.listeners.CanvasOperationListener;
import com.felnstaren.painter.listeners.ColorListener;
import com.felnstaren.painter.listeners.KeybindListener;
import com.felnstaren.painter.listeners.ResetListener;
import com.felnstaren.painter.listeners.BrushSizeListener;
import com.felnstaren.painter.listeners.UndoButtonListener;

public class PixelPainter extends AbstractApp {
	
	private HUD hud;
	private EventHandler ehandler;
	private ButtonHandler bhandler;
	private Canvas canvas;
	private CanvasPainter cpaint;
	private Brush brush;
	private UndoManager uman;
	
	public PixelPainter() {
		this.hud = new HUD();
	}
	
	

	public void init(AppContainer ac) {
		this.canvas = new Canvas(0, 31, ac.getWidth(), ac.getHeight() - 31);
		this.brush = new Brush(50, 50);
		brush.setMode(BrushMode.FREEHAND);

		this.uman = new UndoManager();
		this.ehandler = new EventHandler();
		this.bhandler = new ButtonHandler(ehandler);
		this.cpaint = new CanvasPainter(ehandler);
		
		cpaint.prime(canvas);
		
		ehandler.addListener(new BrushSizeListener());
		ehandler.addListener(new ColorListener());
		ehandler.addListener(new ResetListener(cpaint, canvas));
		ehandler.addListener(new BrushModeListener(brush));
		ehandler.addListener(new CanvasOperationListener(uman));
		ehandler.addListener(new UndoButtonListener(uman));
		ehandler.addListener(new KeybindListener(uman));
		
		bhandler.addButton(new Button(2, 18, 30, 10, "brush_size_button", new Image("/resources/icons/bs1.png")));
		bhandler.addButton(new Button(36, 18, 30, 10, "reset", new Image("/resources/icons/reset.png")));
		bhandler.addButton(new Button(70, 18, 30, 10, "brush_mode_button", brush.getMode().getIcon()));
		bhandler.addButton(new Button(ac.getWidth() - 33, 2, 30, 10, "undo_button", new Image("/resources/icons/undo.png")));
		bhandler.addButton(new Button(ac.getWidth() - 33, 14, 30, 10, "redo_button", new Image("/resources/icons/redo.png")));
		
		RadioButtonGroup color = new RadioButtonGroup();
		color.addButton(new ColorSelectorButton(50, 1, 6, 6, "color_black", 0xff000000));
		color.addButton(new ColorSelectorButton(58, 1, 6, 6, "color_red", 0xffFF0000));
		color.addButton(new ColorSelectorButton(66, 1, 6, 6, "color_green", 0xff00FF00));
		color.addButton(new ColorSelectorButton(74, 1, 6, 6, "color_blue", 0xff0000FF));
		color.addButton(new ColorSelectorButton(50, 9, 6, 6, "color_yellow", 0xffFFFF00));
		color.addButton(new ColorSelectorButton(58, 9, 6, 6, "color_orange", 0xffFF8800));
		color.addButton(new ColorSelectorButton(66, 9, 6, 6, "color_tr_blue", 0x880000ff));
		color.addButton(new ColorSelectorButton(74, 9, 6, 6, "color_white", 0xffF8F8F8));
		bhandler.addRadioGroup(color);
	}

	public void update(AppContainer ac, float delta_time) {
		bhandler.update(ac);
		brush.update(ac, cpaint, canvas);
		ehandler.update(ac.getInput());
	}

	public void render(AppContainer ac, Renderer renderer) {
		hud.render(renderer, ac);
		bhandler.render(renderer);
		canvas.render(renderer);
		cpaint.renderCurrentEdit(renderer);
		brush.render(renderer);
		
		//renderer.circle(70, 50, 20, Options.color, Options.brush_size);
		//renderer.dot(120, 70, 20, Options.color);
		//renderer.dot(100, 100, 20, Options.color);
	}
	
	
	
	public static void main(String[] args) {
		AppContainer ac = new AppContainer(new PixelPainter());
		ac.start();
	}

}
