package com.felnstaren.engine;

import com.felnstaren.engine.gfx.Font;

public class AppContainer implements Runnable {

	private Thread thread;
	private Window window;
	private Renderer renderer;
	private Input input;
	private AbstractApp app;
	
	private boolean running = false;
	private final double UPDATE_CAP = 1.0/60.0;
	
	//320w x 240h, 4f scale
	private int width = 320, height = 208;
	private float scale = 3f;
	private String title = "Pixel Painter";
	
	
	public AppContainer(AbstractApp app) {
		this.app = app;
	}
	
	
	
	public void start() {
		window = new Window(this);
		renderer = new Renderer(this);
		input = new Input(this);
		
		thread = new Thread(this);
		thread.run();
	}
	
	public void stop() {
		
	}
	
	public void run() {
		running = true;
		
		boolean render = false;
		double first_time = 0;
		double last_time = System.nanoTime() / 1000000000.0;
		double passed_time = 0;
		double unprocessed_time = 0;
		
		double frame_time = 0;
		int frames = 0;
		int fps = 0;
		
		app.init(this);
		
		while(running) {
			render = false; //set to true to disable frame cap
			
			first_time = System.nanoTime() / 1000000000.0;
			passed_time = first_time - last_time;
			last_time = first_time;
			
			unprocessed_time += passed_time;
			frame_time += passed_time;
			
			while(unprocessed_time >= UPDATE_CAP) {
				unprocessed_time -= UPDATE_CAP;
				render = true;
				
				//Update game
				app.update(this, (float) (UPDATE_CAP));
				
				input.update();
				
				if(frame_time >= 1.0) {
					frame_time = 0;
					fps = frames;
					frames = 0;
				}
			}
			
			if(render) {
				//Render game
				renderer.clear();
				app.render(this, renderer);
				renderer.process();
				renderer.drawText("Fps: " + fps, Font.STANDARD, 0, 1, 0xff000000);
				window.update();
				frames++;
			} else {
				try { Thread.sleep(1); }
				catch (InterruptedException e) { e.printStackTrace(); }
			}
		}
		
		dispose();
	}
	
	
	private void dispose() {
		
	}
	
	
	
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	

	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}

	
	public float getScale() {
		return scale;
	}
	
	public void setScale(float scale) {
		this.scale = scale;
	}

	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public Window getWindow() {
		return this.window;
	}
	
	
	public Input getInput() {
		return this.input;
	}
	
	
	public Renderer getRenderer() {
		return this.renderer;
	}

}
