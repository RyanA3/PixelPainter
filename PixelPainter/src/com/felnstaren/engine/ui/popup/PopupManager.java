package com.felnstaren.engine.ui.popup;

import java.util.ArrayList;
import java.util.List;

import com.felnstaren.engine.AppContainer;
import com.felnstaren.engine.Renderer;
import com.felnstaren.engine.ui.button.ButtonHandler;

public class PopupManager {

	private ButtonHandler bhand;
	private List<PopUp> popups;
	
	public PopupManager(ButtonHandler bhand) {
		this.popups = new ArrayList<PopUp>();
		this.bhand = bhand;
	}
	
	
	
	public void render(Renderer renderer) {
		if(popups.size() == 0) return;
		
		for(PopUp popup : popups) popup.render(renderer);
	}
	
	public void update(AppContainer ac) {
		List<PopUp> remove = new ArrayList<PopUp>();
		
		for(PopUp popup : popups) {
			popup.update(ac);
			if(popup.isClosed()) remove.add(popup);
		}
		
		for(PopUp popup : remove) close(popup);
	}
	
	
	
	public void addPopup(PopUp popup) {
		popups.add(popup);
	}
	
	
	public boolean hasPopup(String name) {
		for(PopUp popup : popups) if(popup.getTitle().equals(name)) return true;
		return false;
	}
	
	public boolean isOnPopup(int x, int y) {
		for(PopUp popup : popups) if(popup.isInside(x, y)) return true;
		return false;
	}
	
	public boolean isOneGrabbed() {
		for(PopUp popup : popups) if(popup.isGrabbed()) return true;
		return false;
	}
	
	
	
	public void close(PopUp popup) {
		popup.close(bhand);
		popups.remove(popup);
	}
	
	public void close(String title) {
		for(int i = 0; i < popups.size(); i++) {
			if(popups.get(i).getTitle().equals(title)) { 
				popups.get(i).close(bhand);
				popups.remove(i);
				i--;
			}
		}
	}
	
}
