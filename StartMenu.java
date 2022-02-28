package project_oop;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class StartMenu {
	private BufferImageLoader loader = new BufferImageLoader("startmenu.png");
	private BufferedImage startmenu;

	public StartMenu() {
		startmenu = loader.getImg();
	}

	public void draw(Graphics g) {
		g.drawImage(startmenu, 0, 0, null);
	}
}
