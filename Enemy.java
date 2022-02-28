package project_oop;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Enemy {
	private long frozenTimer;
	private HUD hud;
	private Rectangle rectangle;
	private boolean isFrozen = false;
	private boolean alive = true;
	private BufferedImage enemy;
	private double healthPoints;

	public Enemy(int x, int y, BufferedImage sprite, int speed, int curDir, double healthPoints, HUD hud,
			int enemyType) {
		enemy = sprite;
		this.speed = speed;
		this.curDir = curDir;
		this.healthPoints = healthPoints;
		this.hud = hud;
		this.enemyType = enemyType;
		rectangle = new Rectangle(x, y, 32, 32);
	}

	public void draw(Graphics g) {
		g.drawImage(enemy, rectangle.x, rectangle.y, null);
	}

	public void increaseX() {
		rectangle.x += speed;
	}

	public void increaseY() {
		rectangle.y += speed;
	}

	public void decreaseX() {
		rectangle.x -= speed;
	}

	public void decreaseY() {
		rectangle.y -= speed;
	}

	public int getX() {
		return rectangle.x;
	}

	public int getY() {
		return rectangle.y;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getCurDir() {
		return curDir;
	}

	public void setCurDir(int curDir) {
		this.curDir = curDir;
	}

	public void setAliveToFalse() {
		if (alive) {
			alive = false;
			hud.reduceSizeOfWaveByOne();
		}
	}

	public boolean getAlive() {
		return alive;
	}

	public Rectangle bounds() {
		return rectangle;
	}

	public boolean isFrozen() {
		return isFrozen;
	}

	public void setFrozen(boolean isFrozen) {
		this.isFrozen = isFrozen;
	}

	private void checkIfHpLessThanZero() {
		if (healthPoints <= 0.0D) {
			setAliveToFalse();
			addGoldForKill();
		}
	}

	public void hitByArrow(double dmg) {
		healthPoints -= dmg;
		checkIfHpLessThanZero();
	}

	private int enemyType;
	private int curDir;
	private int speed;

	public void hitByIce() {
		if (!isFrozen) {
			frozenTimer = System.currentTimeMillis();
			isFrozen = true;
		}
	}

	public void checkFrozenTimer() {
		if ((isFrozen) && (System.currentTimeMillis() - frozenTimer > 3000L))
			isFrozen = false;
	}

	public void hitByMissile(double dmg) {
		healthPoints -= dmg;
		checkIfHpLessThanZero();
	}

	public void hitByHammer(double dmg) {
		healthPoints -= dmg;
		checkIfHpLessThanZero();
	}

	private void addGoldForKill() {
		if (enemyType == 1) {
			hud.addGold(5);
		} else if (enemyType == 2) {
			hud.addGold(10);
		} else if (enemyType == 3) {
			hud.addGold(20);
		}
	}

	public int getEnemyType() {
		return enemyType;
	}
}
