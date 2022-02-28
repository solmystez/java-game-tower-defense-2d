package project_oop;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import project_oop.main.graphics.MainRender;

public class Mover {
	private List<Enemy> enemies = new ArrayList();
	private List<Enemy> enemiesTemp = new ArrayList();
	private List<Tower> towers = new ArrayList();
	private List<Projectile> projectiles = new ArrayList();
	private int[][] map = new int[15][20];
	private MainRender render;
	private boolean allSpawned = false;
	private long timer;
	private int JagVetInte = 0;
	private Game game;
	private boolean paused = false;
	private boolean startMenu = true;
	private boolean gameOver = false;
	private boolean gameWon = false;
	private long delay = 0L;
	private HUD hud;

	public Mover(Game game, HUD hud) {
		this.game = game;
		this.hud = hud;
	}

	public void move() {
		if ((!paused) && (!startMenu) && (!gameWon)) {
			moveIt();
		}
	}

	public void moveIt() {
		for (int i = 0; i < towers.size(); i++) {
			((Tower) towers.get(i)).update();
		}

		if ((enemiesTemp.size() > 0) && (!allSpawned)) {
			if (System.currentTimeMillis() - timer > 1000L) {
				timer += 1000L;
				spawn();
			}

			if (enemies.size() == enemiesTemp.size()) {
				allSpawned = true;
				System.out.println("all spawned (moverClass)");
				timer = System.currentTimeMillis();
			}
		} else {
			timer = System.currentTimeMillis();
		}

		for (Enemy e : enemies) {
			if (e.getAlive()) {
				int x = e.getX();
				int y = e.getY();
				int curDir = e.getCurDir();
				int thisTile = thisTile(x, y);
				int nextTile = nextTile(x, y, curDir);

				if ((curDir == -1) || (curDir == 5)) {
					if (curDir == -1) {
						e.setAliveToFalse();
						hud.reduceLife();
						if (hud.getLife() <= 0) {
							gameOver = true;
							game.gameOver();
						}

					} else if (curDir == 5) {
						System.out.println("Enemy were Removed. I were spawned but found no way to go.");
						e.setAliveToFalse();
					}
				}

				if (curDir == 0) {
					int newDir = findStartDirection(x, y);
					e.setCurDir(newDir);
					curDir = newDir;
				} else if ((enemies.size() > 0) && (thisTile != nextTile)) {
					int newDir = findNewDir(curDir, x, y);
					e.setCurDir(newDir);
					curDir = newDir;
				}
				if (!e.isFrozen()) {
					if (curDir == 1) {
						e.increaseX();
					} else if (curDir == 2) {
						e.decreaseX();
					} else if (curDir == 3) {
						e.decreaseY();
					} else if (curDir == 4) {
						e.increaseY();
					}
				} else {
					e.checkFrozenTimer();
				}
			}
		}
	}

	private int thisTile(int x, int y) {
		x /= 32;
		y /= 32;
		int tile = map[y][x];

		return tile;
	}

	public void receiveTower(Tower tower) {
		towers.add(tower);
	}

	public void receiveArrow(Projectile projectile) {
		projectiles.add(projectile);
	}

	private void spawn() {
		enemies.add((Enemy) enemiesTemp.get(JagVetInte));
		render.addEnemy(JagVetInte);
		JagVetInte += 1;
	}

	private int nextTile(int x, int y, int curDir) {
		int tile = -1;

		if ((curDir == 1) && (x < 640)) {
			x /= 32;
			y /= 32;
			tile = map[y][(x + 1)];
		} else if ((curDir == 2) && (x > 0)) {
			x--;
			x /= 32;
			y /= 32;
			tile = map[y][x];
		} else if ((curDir == 3) && (y > 0)) {
			y--;
			x /= 32;
			y /= 32;
			tile = map[y][x];
		} else if ((curDir == 4) && (y < 480)) {
			y /= 32;
			x /= 32;
			tile = map[(y + 1)][x];
		}

		return tile;
	}

	public void receiveEnemies(List<Enemy> testar, int sizeOfWave) {
		for (int i = 0; i < testar.size(); i++) {
			enemiesTemp.add((Enemy) testar.get(i));
		}
		allSpawned = false;

		hud.setSizeOfWave(sizeOfWave);
	}

	public void receiveMap(int[][] map) {
		this.map = map;
	}

	public void getRenderObj(MainRender render) {
		this.render = render;
	}

	private int findStartDirection(int x, int y) {
		int newDir = 5;
		x /= 32;
		y /= 32;
		int curMapType = map[y][x];

		if (curMapType == map[y][(x + 1)]) {
			newDir = 1;
		} else if (curMapType == map[y][(x - 1)]) {
			newDir = 2;
		} else if (curMapType == map[(y - 1)][x]) {
			newDir = 3;
		} else if (curMapType == map[(y + 1)][x]) {
			newDir = 4;
		} else {
			System.out.println("Cant find a way, i just spawned and no paths at all could be found.");
		}
		return newDir;
	}

	private int findNewDir(int curDir, int x, int y) {
		int newDir = -1;
		x /= 32;
		y /= 32;
		int curMapType = map[y][x];

		if (curDir == 1) {

			if ((y > 0) && (curMapType == map[(y + 1)][x])) {
				newDir = 4;
			} else if (curMapType == map[(y - 1)][x]) {
				newDir = 3;
			}

		} else if (curDir == 2) {

			if (curMapType == map[(y - 1)][x]) {
				newDir = 3;
			} else if (curMapType == map[(y + 1)][x]) {
				newDir = 4;
			}

		} else if (curDir == 3) {

			if (curMapType == map[y][(x + 1)]) {
				newDir = 1;
			} else if (curMapType == map[y][(x - 1)]) {
				newDir = 2;
			}

		} else if (curDir == 4) {

			if (curMapType == map[y][(x + 1)]) {
				newDir = 1;
			} else if (curMapType == map[y][(x - 1)]) {
				newDir = 2;
			}
		}

		return newDir;
	}

	public void changePaused() {
		if (!paused) {
			paused = true;
			delay = (System.currentTimeMillis() - timer);
			System.out.println("pause");
		} else {
			paused = false;
			timer = (System.currentTimeMillis() - delay);
			System.out.println("unpause");
		}
	}

	public List<Tower> getTowerList() {
		return towers;
	}

	public void setStartMenu(boolean startMenu) {
		this.startMenu = startMenu;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public List<Enemy> getEnemyList() {
		return enemies;
	}

	public List<Enemy> getEnemiesTemp() {
		return enemiesTemp;
	}

	public void resetJagVetInte() {
		JagVetInte = 0;
	}

	public void gameWon() {
		game.gameIsWon();
		gameWon = true;
	}

	public void setGameWon(boolean b) {
		gameWon = b;
	}
}
