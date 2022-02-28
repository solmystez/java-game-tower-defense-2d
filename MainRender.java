package project_oop;

import java.awt.Graphics;

public class MainRender {
	private java.awt.image.BufferedImage grass;
	private java.awt.image.BufferedImage dirt;
	private java.awt.image.BufferedImage waterN;
	private java.awt.image.BufferedImage waterNE;
	private java.awt.image.BufferedImage waterE;
	private java.awt.image.BufferedImage waterSE;
	private java.awt.image.BufferedImage waterS;
	private java.awt.image.BufferedImage waterSW;
	private java.awt.image.BufferedImage waterW;
	private java.awt.image.BufferedImage waterNW;
	private java.awt.image.BufferedImage waterMid;
	private java.awt.image.BufferedImage waterLeft;
	private java.awt.image.BufferedImage castleNW;
	private java.awt.image.BufferedImage castleN;
	private java.awt.image.BufferedImage castleNE;
	private java.awt.image.BufferedImage castleW;
	private java.awt.image.BufferedImage castleM;
	private java.awt.image.BufferedImage castleE;
	private java.awt.image.BufferedImage castleSW;
	private java.awt.image.BufferedImage castleS;
	private java.awt.image.BufferedImage castleSE;
	private java.util.List<com.kaarin.game.td.main.Enemy> enemies = new java.util.ArrayList();
	private java.util.List<com.kaarin.game.com.kaarin.game.td.main.Projectile> projectiles = new java.util.ArrayList();
	private java.util.List<com.kaarin.game.td.main.Enemy> enemiesTemp = new java.util.ArrayList();
	private java.util.List<com.kaarin.game.td.main.Tower> towers = new java.util.ArrayList();

	private com.kaarin.game.td.main.HUD hud;
	private com.kaarin.game.td.com.kaarin.game.td.PausedScreen pausedScreen = new com.kaarin.game.com.kaarin.game.td.main.PausedScreen();
	private boolean paused = false;
	private boolean towerClicked = false;
	private boolean startMenuBoolean = true;
	private boolean gameOver = false;
	private boolean gameWon = false;
	private com.kaarin.game.td.main.TempTowerAtMouseLoc tempTowerAtMouseLoc;
	private int typeOfTower = 1;

	private com.kaarin.game.td.com.kaarin.game.td.StartMenu startMenu = new com.kaarin.game.com.kaarin.game.td.main.StartMenu();
	private com.kaarin.game.td.com.kaarin.game.td.GameOverScreen gameOverScreen = new com.kaarin.game.com.kaarin.game.td.main.GameOverScreen();
	private com.kaarin.game.td.com.kaarin.game.td.GameWon gameWonClass = new com.kaarin.game.com.kaarin.game.td.main.GameWon();

	private int[][] map = {

			{ 10, 10, 10, 4, 1, 8, 10, 4 }, { 10, 10, 10, 4, 1, 8, 10, 4 },
			{ 6, 6, 6, 5, 1, 8, 10, 4, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 7, 11, 4, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 2, 2, 2, 3, 1, 0, 8, 4, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 10, 10, 10, 4, 1, 0, 8, 4, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 10, 10, 10, 4, 1, 0, 8, 4, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 10, 10, 10, 4, 1, 0, 8, 4, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 10, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 12, 13, 14, 0, 0, 1 },
			{ 6, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 15, 16, 17, 0, 0, 1 },
			{ 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 18, 19, 20, 0, 0, 1 },
			{ 0, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1 },
			{ 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1 }, { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, new int[20] };

	public MainRender(com.kaarin.game.td.main.HUD hud,
			com.kaarin.game.td.main.TempTowerAtMouseLoc tempTowerAtMouseLoc) {
		this.tempTowerAtMouseLoc = tempTowerAtMouseLoc;
		this.hud = hud;

		com.kaarin.game.td.com.kaarin.game.td.BufferImageLoader loader = new com.kaarin.game.td.com.kaarin.game.td.BufferImageLoader(
				"spritesheet.png");

		grass = loader.grabSprite(1, 1);
		dirt = loader.grabSprite(2, 1);

		waterN = loader.grabSprite(2, 3);
		waterNE = loader.grabSprite(3, 3);
		waterE = loader.grabSprite(3, 4);
		waterSE = loader.grabSprite(3, 5);
		waterS = loader.grabSprite(2, 5);
		waterSW = loader.grabSprite(1, 5);
		waterW = loader.grabSprite(1, 4);
		waterNW = loader.grabSprite(1, 3);
		waterMid = loader.grabSprite(2, 4);
		waterLeft = loader.grabSprite(6, 1);

		castleNW = loader.grabSprite(4, 3);
		castleN = loader.grabSprite(5, 3);
		castleNE = loader.grabSprite(6, 3);

		castleW = loader.grabSprite(4, 4);
		castleM = loader.grabSprite(5, 4);
		castleE = loader.grabSprite(6, 4);

		castleSW = loader.grabSprite(4, 5);
		castleS = loader.grabSprite(5, 5);
		castleSE = loader.grabSprite(6, 5);
	}

	public void render(Graphics g) {
		if (startMenuBoolean) {
			startMenu.draw(g);
		} else if ((!startMenuBoolean) && (!gameOver) && (!gameWon)) {
			g.setColor(java.awt.Color.WHITE);
			g.fillRect(0, 0, 640, 576);
			int x;
			for (int y = 0; y < map.length; y++) {
				for (x = 0; x < map[y].length; x++) {
					if (map[y][x] == 0) {
						g.drawImage(grass, x * 32, y * 32, null);
					} else if (map[y][x] == 1) {
						g.drawImage(dirt, x * 32, y * 32, null);
					} else if (map[y][x] == 2) {
						g.drawImage(waterN, x * 32, y * 32, null);
					} else if (map[y][x] == 3) {
						g.drawImage(waterNE, x * 32, y * 32, null);
					} else if (map[y][x] == 4) {
						g.drawImage(waterE, x * 32, y * 32, null);
					} else if (map[y][x] == 5) {
						g.drawImage(waterSE, x * 32, y * 32, null);
					} else if (map[y][x] == 6) {
						g.drawImage(waterS, x * 32, y * 32, null);
					} else if (map[y][x] == 7) {
						g.drawImage(waterSW, x * 32, y * 32, null);
					} else if (map[y][x] == 8) {
						g.drawImage(waterW, x * 32, y * 32, null);
					} else if (map[y][x] == 9) {
						g.drawImage(waterNW, x * 32, y * 32, null);
					} else if (map[y][x] == 10) {
						g.drawImage(waterMid, x * 32, y * 32, null);
					} else if (map[y][x] == 11) {
						g.drawImage(waterLeft, x * 32, y * 32, null);
					} else if (map[y][x] == 12) {
						g.drawImage(castleNW, x * 32, y * 32, null);
					} else if (map[y][x] == 13) {
						g.drawImage(castleN, x * 32, y * 32, null);
					} else if (map[y][x] == 14) {
						g.drawImage(castleNE, x * 32, y * 32, null);
					} else if (map[y][x] == 15) {
						g.drawImage(castleW, x * 32, y * 32, null);
					} else if (map[y][x] == 16) {
						g.drawImage(castleM, x * 32, y * 32, null);
					} else if (map[y][x] == 17) {
						g.drawImage(castleE, x * 32, y * 32, null);
					} else if (map[y][x] == 18) {
						g.drawImage(castleSW, x * 32, y * 32, null);
					} else if (map[y][x] == 19) {
						g.drawImage(castleS, x * 32, y * 32, null);
					} else if (map[y][x] == 20)
						g.drawImage(castleSE, x * 32, y * 32, null);
				}
			}
			hud.draw(g);

			for (int i = 0; i < towers.size(); i++) {
				((com.kaarin.game.td.main.Tower) towers.get(i)).draw(g);
			}
			for (com.kaarin.game.td.main.Enemy e : enemies) {
				if (e.getAlive()) {
					e.draw(g);
				}
			}

			if (towerClicked) {
				tempTowerAtMouseLoc.setTypeOfTower(typeOfTower);
				tempTowerAtMouseLoc.draw(g);
			}

			if (paused) {
				pausedScreen.draw(g);
			}
		} else if (gameOver) {
			gameOverScreen.draw(g);
		}

		if ((gameWon) && (!gameOver)) {
			gameWonClass.draw(g);
		}
	}

	public void changePaused() {
		hud.changePaused();

		if (paused) {
			paused = false;
		} else {
			paused = true;
		}
	}

	public void receiveEnemies(java.util.List<com.kaarin.game.td.main.Enemy> list) {
		for (int i = 0; i < list.size(); i++)
			enemiesTemp.add((com.kaarin.game.td.main.Enemy) list.get(i));
	}

	public void addEnemy(int i) {
		enemies.add((com.kaarin.game.td.main.Enemy) enemiesTemp.get(i));
	}

	public int[][] getMap() {
		return map;
	}

	public void receiveTower(com.kaarin.game.td.main.Tower tower) {
		towers.add(tower);
	}

	public com.kaarin.game.td.main.HUD getHud() {
		return hud;
	}

	public int getSizeOfWave() {
		return hud.getSizeOfWave();
	}

	public void setTotalOfWaves(int total) {
		hud.setTotalNrOfWaves(total);
	}

	public void increaseWaveNrByOne() {
		hud.increaseWaveNrByOne();
	}

	public void receiveArrows(com.kaarin.game.com.kaarin.game.td.main.Projectile projectile) {
		projectiles.add(projectile);
	}

	public void changeTowerClicked(int typeOfTower) {
		this.typeOfTower = typeOfTower;
		if (!towerClicked) {
			towerClicked = true;
		} else {
			towerClicked = false;
		}
	}

	public boolean getTowerClicked() {
		return towerClicked;
	}

	public int getTypeOfTower() {
		return typeOfTower;
	}

	public java.util.List<com.kaarin.game.td.main.Tower> getTowerList() {
		return towers;
	}

	public void setStartMenu(boolean startMenuBoolean) {
		this.startMenuBoolean = startMenuBoolean;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public java.util.List<com.kaarin.game.td.main.Enemy> getEnemies() {
		return enemies;
	}

	public void setEnemies(java.util.List<com.kaarin.game.td.main.Enemy> enemies) {
		this.enemies = enemies;
	}

	public java.util.List<com.kaarin.game.td.main.Enemy> getEnemiesTemp() {
		return enemiesTemp;
	}

	public void setGameWon(boolean b) {
		gameWon = b;
	}
}
