package com.felnstaren.painter.listeners;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.felnstaren.engine.event.EventMethod;
import com.felnstaren.engine.event.Listener;
import com.felnstaren.engine.event.events.ButtonPressEvent;
import com.felnstaren.engine.gfx.Image;
import com.felnstaren.engine.ui.button.Button;
import com.felnstaren.engine.ui.button.ButtonHandler;
import com.felnstaren.engine.ui.canvas.Canvas;
import com.felnstaren.engine.ui.popup.PopUp;
import com.felnstaren.engine.ui.popup.PopupManager;

public class ExportButtonListener extends Listener {

	private Canvas canvas;
	private PopupManager popman;
	private ButtonHandler bhand;
	
	public ExportButtonListener(Canvas canvas, PopupManager popman, ButtonHandler bhand) {
		this.canvas = canvas;
		this.popman = popman;
		this.bhand = bhand;
	}
	
	
	@EventMethod
	public void onExport(ButtonPressEvent event) {
		if(event.getButton().getName().equals("export_button")) {
			if(popman.hasPopup("Save")) return;
			
			PopUp popup = new PopUp("Save", 25, 25, 100, 70);
			popup.addButton(bhand, new Button(84, 12, 14, 14, "save_button", new Image("/resources/icons/confirm_export.png")));
			popman.addPopup(popup);
			
		} else if(event.getButton().getName().equals("save_button")) {
			System.out.println("saving image");
		
			BufferedImage image = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
			image.setRGB(0, 0, canvas.getWidth(), canvas.getHeight(), canvas.getPixels(), 0, canvas.getWidth());
			String path = ClassLoader.getSystemClassLoader().getResource(".").getPath();
			try {
				File file = new File(path + "\\pixelpicture.png");
				ImageIO.write(image, "png", file);
				popman.close("Save");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("error creating image");
			}
		}
	}
}
