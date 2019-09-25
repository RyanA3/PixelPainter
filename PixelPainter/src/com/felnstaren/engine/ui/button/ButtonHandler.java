package com.felnstaren.engine.ui.button;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.felnstaren.engine.AppContainer;
import com.felnstaren.engine.Renderer;
import com.felnstaren.engine.event.EventHandler;
import com.felnstaren.engine.event.events.ButtonPressEvent;
import com.felnstaren.engine.event.events.ButtonReleaseEvent;
import com.felnstaren.engine.event.events.RadioButtonSelectEvent;
import com.felnstaren.engine.ui.button.radio.RadioButton;
import com.felnstaren.engine.ui.button.radio.RadioButtonGroup;

public class ButtonHandler {

	private List<Button> buttons;
	private List<RadioButtonGroup> groups;
	
	private EventHandler ehand;
	
	public ButtonHandler(EventHandler ehand) {
		this.ehand = ehand;
		
		buttons = new ArrayList<Button>();
		groups = new ArrayList<RadioButtonGroup>();
	}
	
	
	
	public void addButton(Button button) {
		buttons.add(button);
	}
	
	public void addRadioGroup(RadioButtonGroup group) {
		groups.add(group);
		for(Button button : group.getButtons()) {
			buttons.add(button);
		}
	}
	
	
	
	public void update(AppContainer ac) {
		int mx = ac.getInput().getMouseX();
		int my = ac.getInput().getMouseY();
		
		for(Button button : buttons) {
			if(!button.isInside(mx, my)) continue;
			
			if(!button.isPressed() && ac.getInput().isButtonPressed(MouseEvent.BUTTON1)) {
				if(button instanceof RadioButton) {
					RadioButton rbutton = (RadioButton) button;
					
					for(RadioButtonGroup group : groups) {
						if(!group.hasButton(rbutton)) continue;
						group.setPressed(rbutton);
						ehand.trigger(new RadioButtonSelectEvent(rbutton));
					}
				} else {
					button.setPressed(true);
					if(button.isPressed()) 	ehand.trigger(new ButtonPressEvent(button));
				}
			}
			else if(button.isPressed() && ac.getInput().isButtonUp(MouseEvent.BUTTON1) && !(button instanceof RadioButton)) {
				button.setPressed(false);
				if(!button.isPressed()) ehand.trigger(new ButtonReleaseEvent(button));
			}
		}
	}
	
	public void render(Renderer renderer) {
		for(Button button : buttons) {
			button.render(renderer);
		}
	}
	
}
