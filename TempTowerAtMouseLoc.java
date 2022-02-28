package project_oop;

import java.awt.Graphics;

public class TempTowerAtMouseLoc {
	private BufferImageLoader loader;
	private java.awt.image.BufferedImage hammerTower;
	private java.awt.image.BufferedImage missileTower;
	private java.awt.image.BufferedImage iceTower;
	private java.awt.image.BufferedImage bowTower;
	private int typeOfTower;
	private int y = 800;
	private int x = 800;

	public TempTowerAtMouseLoc() {
		loader = new BufferImageLoader("spritesheet.png");

		bowTower = loader.grabSprite(2, 2);
		iceTower = loader.grabSprite(4, 2);
		missileTower = loader.grabSprite(7, 3);
		hammerTower = loader.grabSprite(7, 2);
	}

	public void draw(Graphics g) {
		x /= 32;
		y /= 32;

		x *= 32;
		y *= 32;

		if (y >= 480) {
			y = 448;
		}
		if (typeOfTower == 1) {
			g.drawImage(bowTower, x, y, null);
		} else if (typeOfTower == 2) {
			g.drawImage(iceTower, x, y, null);
		} else if (typeOfTower == 3) {
			g.drawImage(missileTower, x, y, null);
		} else if (typeOfTower == 4) {
			g.drawImage(hammerTower, x, y, null);
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setTypeOfTower(int typeOfTower) {
		this.typeOfTower = typeOfTower;
	}
}
