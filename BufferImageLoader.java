package project_oop;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import javax.imageio.ImageIO;

public class BufferImageLoader {
	private int spriteSize = 32;
	private BufferedImage img = null;

	public BufferImageLoader(String spriteName) {
		URL url = getClass().getResource(spriteName);
		try {
			img = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Det faila här uppe!");
		}
	}

	public BufferedImage grabSprite(int xLocation, int yLocation) {
		int x = xLocation * spriteSize - spriteSize;
		int y = yLocation * spriteSize - spriteSize;

		BufferedImage sprite = img.getSubimage(x, y, spriteSize, spriteSize);
		return sprite;
	}

	public BufferedImage grabBigSprite(int xLocation, int yLocation, int width, int height) {
		int x = xLocation * spriteSize - spriteSize;
		int y = yLocation * spriteSize - spriteSize;

		BufferedImage sprite = img.getSubimage(x, y, spriteSize * width, spriteSize * height);

		return sprite;
	}

	public BufferedImage grabSpriteAtASpecificCords(int xLocation, int yLocation, int width, int height, int xOffset,
			int yOffset) {
		int x = xLocation * spriteSize - spriteSize;
		int y = yLocation * spriteSize - spriteSize;

		BufferedImage sprite = img.getSubimage(x + xOffset, y + yOffset, width, height);
		return sprite;
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}
}
