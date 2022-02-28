package project_oop;

import java.util.List;

public class Wave {
	private Mover mover;
	private com.kaarin.game.td.main.com.kaarin.game.td.MainRender render;
	private int amountGround;
	private int hpGround;
	private int speedGround;
	private int amountAir;
	private int hpAir;
	private int speedAir;
	private int amountBoss;
	private int hpBoss;
	private int speedBoss;
	private List<Enemy> enemies = new java.util.ArrayList();
	private List<Tower> towers;
	private BufferImageLoader loader = new BufferImageLoader("spritesheet.png");
	private java.awt.image.BufferedImage ground;
	private java.awt.image.BufferedImage air;
	private java.awt.image.BufferedImage boss;
	private boolean waveDead = false;
	private int sizeOfWave = 0;

	public Wave(int amountGround, int hpGround, int speedGround, int amountAir, int hpAir, int speedAir, int amountBoss,
			int hpBoss, int speedBoss, Mover mover, com.kaarin.game.td.com.kaarin.game.td.graphics.MainRender render,
			List<Tower> towers, HUD hud) {
		this.amountGround = amountGround;
		this.hpGround = hpGround;
		this.speedGround = speedGround;

		this.amountAir = amountAir;
		this.hpAir = hpAir;
		this.speedAir = speedAir;

		this.amountBoss = amountBoss;
		this.hpBoss = hpBoss;
		this.speedBoss = speedBoss;

		this.mover = mover;
		this.render = render;
		this.towers = towers;

		ground = loader.grabSprite(1, 2);
		air = loader.grabSprite(3, 2);
		boss = loader.grabSprite(4, 1);

		for (int i = 0; i < this.amountGround; i++) {
			enemies.add(new Enemy(128, 0, ground, this.speedGround, 4, this.hpGround, hud, 1));
			sizeOfWave += 1;
		}
		for (int i = 0; i < this.amountAir; i++) {
			enemies.add(new Enemy(0, 96, air, this.speedAir, 1, this.hpAir, hud, 2));
			sizeOfWave += 1;
		}
		for (int i = 0; i < this.amountBoss; i++) {
			enemies.add(new Enemy(128, 0, boss, this.speedBoss, 4, this.hpBoss, hud, 3));
			sizeOfWave += 1;
		}
	}

	public void sendWave() {
		mover.receiveEnemies(enemies, sizeOfWave);
		render.receiveEnemies(enemies);
		for (Tower t : towers) {
			t.receiveEnemyList(enemies);
		}
	}

	public boolean getWaveDead() {
		return waveDead;
	}

	public void setWaveDead(boolean deadOrNot) {
		waveDead = deadOrNot;
	}

	public int getSizeOfWave() {
		return sizeOfWave;
	}

	public List<Enemy> getEnemyList() {
		return enemies;
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}

	public void setEnemies(List<Enemy> enemies) {
		this.enemies = enemies;
	}

	public List<Tower> getTowers() {
		return towers;
	}

	public void setTowers(List<Tower> towers) {
		this.towers = towers;
	}
}
