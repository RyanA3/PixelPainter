package com.felnstaren.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

	private AppContainer ac;
	
	//Keeps track of all keys and if they are down
	private final int NUM_KEYS = 256;
	private boolean[] keys = new boolean[NUM_KEYS];
	private boolean[] keys_last = new boolean[NUM_KEYS];
	
	//Keeps track of mouse buttons and if they are down
	private final int NUM_BUTTONS = 5;
	private boolean[] buttons = new boolean[NUM_KEYS];
	private boolean[] buttons_last = new boolean[NUM_KEYS];
	
	private int mouseX, mouseY;
	private int scroll;
	
	public Input(AppContainer ac) {
		this.ac = ac;
		mouseX = 0;
		mouseY = 0;
		scroll = 0;
		
		ac.getWindow().getCanvas().addKeyListener(this);
		ac.getWindow().getCanvas().addMouseMotionListener(this);
		ac.getWindow().getCanvas().addMouseListener(this);
		ac.getWindow().getCanvas().addMouseWheelListener(this);
	}
	
	
	public void update() {
		scroll = 0;
		
		for(int i = 0; i < NUM_KEYS; i++) keys_last[i] = keys[i];
		for(int i = 0; i < NUM_BUTTONS; i++) buttons_last[i] = buttons[i];
	}
	
	
	public boolean isKeyPressed(int key_code) {
		return keys[key_code];
	}
	
	public boolean isKeyUp(int key_code) {
		return keys_last[key_code] && !keys[key_code];
	}
	
	public boolean isKeyDown(int key_code) {
		return !keys_last[key_code] && keys[key_code];
	}
	
	public boolean isButtonPressed(int button) {
		return buttons[button];
	}
	
	public boolean isButtonUp(int button) {
		return buttons_last[button] && !buttons[button];
	}
	
	public boolean isButtonDown(int button) {
		return !buttons_last[button] && buttons[button];
	}
	
	

	@Override
	public void mouseWheelMoved(MouseWheelEvent event) {
		scroll = event.getWheelRotation();
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		mouseX = (int) (event.getX() / ac.getScale());
		mouseY = (int) (event.getY() / ac.getScale());
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		mouseX = (int) (event.getX() / ac.getScale());
		mouseY = (int) (event.getY() / ac.getScale());
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		
	}

	@Override
	public void mouseExited(MouseEvent event) {
		
	}

	@Override
	public void mousePressed(MouseEvent event) {
		buttons[event.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		buttons[event.getButton()] = false;
	}

	@Override
	public void keyPressed(KeyEvent event) {
		keys[event.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent event) {
		keys[event.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent event) {
		
	}
	
	
	
	public int getMouseX() {
		return this.mouseX;
	}
	
	public int getMouseY() {
		return this.mouseY;
	}
	
	public int getScroll() {
		return this.scroll;
	}
	
	
	
	public boolean[] getKeyMap() {
		return this.keys;
	}
}
