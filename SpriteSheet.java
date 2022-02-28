package project_oop;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	public BufferedImage spritesheet;

	public SpriteSheet(BufferedImage ss) {
		spritesheet = ss;
	}

	public BufferedImage grabSprite(int x, int y, int width, int height) {
		BufferedImage sprite = spritesheet.getSubimage(x, y, width, height);

		return sprite;
	}
}
