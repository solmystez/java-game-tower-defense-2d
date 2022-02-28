package project_oop;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class PausedScreen {
	private BufferImageLoader loader = new BufferImageLoader("backgroundPause.png");
	private BufferedImage background;

	public PausedScreen() {
		background = loader.getImg();
	}

	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawImage(background, 0, 0, null);
		g.setFont(new Font("Impact", 1, 45));
		g.drawString("Game Paused...", 200, 200);
	}
}
