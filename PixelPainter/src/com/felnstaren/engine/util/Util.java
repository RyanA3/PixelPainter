package com.felnstaren.engine.util;

public class Util {

	public static boolean contains(int rectx, int recty, int width, int height, int x, int y) {
		return x >= rectx &&
				y >= recty &&
				x <= rectx + width &&
				y <= recty + height;
	}
	
}
