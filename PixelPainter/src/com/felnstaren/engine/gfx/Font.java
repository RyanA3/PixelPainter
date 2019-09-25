package com.felnstaren.engine.gfx;

public class Font {
	
	public static final Font STANDARD = new Font("/resources/fonts/comic_sans.png");
	public static final Font SMALL = new Font("/resources/fonts/dialog_small.png");
	
	private Image font_image;
	private int[] offsets;
	private int[] widths;
	
	public Font(String path) {
		font_image = new Image(path);
		
		offsets = new int[256];
		widths = new int[256];
		
		int unicode = 0;
		
		for(int i = 0; i < font_image.getWidth(); i++) {
			if(font_image.getPixel(i) == 0xff0000ff) { //Beginning of a character: 0 red 0 green 255 blue
				offsets[unicode] = i;
			}
			
			if(font_image.getPixel(i) == 0xff000000) continue;
			
			if(font_image.getPixel(i) == 0xffff0000) { //Ending of a character 255 red 0 green 0 blue
				widths[unicode] = i - offsets[unicode];
				unicode++;
			}
		}
	}
	
	
	
	public Image getFontImage() {
		return this.font_image;
	}
	
	public int getWidth(int loc) {
		return this.widths[loc];
	}
	
	public int[] getWidths() {
		return this.widths;
	}
	
	public int getOffset(int loc) {
		return this.offsets[loc];
	}
	
	public int[] getOffsets() {
		return this.offsets;
	}
}
