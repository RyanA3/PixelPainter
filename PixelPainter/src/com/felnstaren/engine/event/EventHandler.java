package com.felnstaren.engine.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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
}
