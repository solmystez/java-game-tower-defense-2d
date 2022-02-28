package project_oop;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class GameWon {
	private BufferImageLoader loader = new BufferImageLoader("gamewon.png");
	private BufferedImage img;

	public GameWon() {
		img = loader.getImg();
	}

	public void draw(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}
}
