package project_oop;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class GameOverScreen {
	private BufferImageLoader loader = new BufferImageLoader("gameover.png");
	private BufferedImage img;

	public GameOverScreen() {
		img = loader.getImg();
	}

	public void draw(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}
}
