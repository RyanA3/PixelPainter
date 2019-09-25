package com.felnstaren.painter;

import com.felnstaren.engine.AppContainer;
import com.felnstaren.engine.Renderer;

public class HUD {
	
	public HUD() {
		
	}
	
	

	public void render(Renderer renderer, AppContainer ac) {
		renderer.drawRect(0, 0, ac.getWidth() - 1, 30, 0xffAAAAAA);
		renderer.fillRect(1, 1, ac.getWidth() - 2, 29, 0xffCCCCCC);
	}
	
}
