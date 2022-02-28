package project_oop;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import project_oop.main.graphics.MainRender;

public class TowerHandler {
	private Tower tower;
	private Mover mover;
	private MainRender render;
	private Game game;
	private int[][] map;
	private int[][] towerMap = new int[15][20];
	private int towerNumber = 1;
	private List<Tower> towers = new ArrayList();

	public TowerHandler(Mover mover, MainRender render, Game game) {
		this.mover = mover;
		this.render = render;
		this.game = game;
		map = render.getMap();

		for (int y = 0; y < towerMap.length; y++) {
			for (int x = 0; x < towerMap[y].length; x++) {
				towerMap[y][x] = 0;
			}
		}
	}

	public void tower(int x, int y, int typeOfTower) {
		tower = new Tower(x, y, typeOfTower, game.getWaveManager().getCurrentWave().getEnemyList(), towerNumber);
		towers.add(tower);
		System.out.println("Tower Spawned (TH Class)");
		mover.receiveTower(tower);
		render.receiveTower(tower);
		game.getWaveManager().receiveTower(tower);
		towerMap[(y / 32)][(x / 32)] = towerNumber;
		towerNumber += 1;
	}

	public boolean checkIfTowerCanBePlacedHere(int x, int y) {
		if ((towerMap[(y / 32)][(x / 32)] == 0) && (map[(y / 32)][(x / 32)] == 0)) {
			return false;
		}
		return true;
	}

	public int findIfTowerIsHere(int x, int y) {
		x /= 32;
		y /= 32;

		return towerMap[y][x];
	}

	public Tower getTowerAtLoc(int x, int y) {
		for (Tower t : towers) {
			if ((t.getX() / 32 == x / 32) && (t.getY() / 32 == y / 32)) {
				return t;
			}
		}

		System.out.println("it failed here. TH class");
		return tower;
	}

	public int[][] getMap() {
		return map;
	}

	public void setMap(int[][] map) {
		this.map = map;
	}

	public int[][] getTowerMap() {
		return towerMap;
	}

	public void setTowerMap(int[][] towerMap) {
		this.towerMap = towerMap;
	}

	public int getTowerNumber() {
		return towerNumber;
	}

	public void setTowerNumber(int towerNumber) {
		this.towerNumber = towerNumber;
	}

	public List<Tower> getTowers() {
		return towers;
	}

	public void setTowers(List<Tower> towers) {
		this.towers = towers;
	}

	public void removeTowerHere(int x, int y) {
		x /= 32;
		y /= 32;

		towerMap[y][x] = 0;
	}
}
