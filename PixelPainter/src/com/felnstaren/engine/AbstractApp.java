package com.felnstaren.engine;

public abstract class AbstractApp {

	public abstract void init(AppContainer ac);
	public abstract void update(AppContainer ac, float delta_time);
	public abstract void render(AppContainer ac, Renderer renderer);
	
}
