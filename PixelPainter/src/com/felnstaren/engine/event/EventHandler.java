package com.felnstaren.engine.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.felnstaren.engine.Input;
import com.felnstaren.engine.event.events.KeyDownEvent;
import com.felnstaren.engine.event.events.KeyUpEvent;

public class EventHandler {

	private List<Listener> listeners;
	
	public EventHandler() {
		listeners = new ArrayList<Listener>();
	}
	
	
	
	public void addListener(Listener listener) {
		this.listeners.add(listener);
	}
	
	
	
	public void trigger(Event event) {
		for(Listener listener : listeners) {
			for(Method method : listener.getClass().getDeclaredMethods()) {
				if(method.getDeclaredAnnotation(EventMethod.class) != null) {
					try {
						Class<?> mclass = method.getParameters()[0].getType();
						Class<? extends Event> iclass = event.getClass();
						Object t = listener.clone();
						
						if(mclass.equals(iclass) || iclass.getSuperclass().equals(mclass)) {
							method.invoke(t, event);
						}
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	
	
	
	public void update(Input in) {
		for(int k = 0; k < in.getKeyMap().length; k++) {
			if(in.isKeyDown(k)) trigger(new KeyDownEvent(in.getKeyMap(), k));
			if(in.isKeyUp(k)) trigger(new KeyUpEvent(in.getKeyMap(), k));
		}
	}
}
