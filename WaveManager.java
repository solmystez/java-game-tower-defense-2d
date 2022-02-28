package project_oop;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import project_oop.main.graphics.MainRender;

public class WaveManager {
	private Mover mover;
	private MainRender render;
	private HUD hud;
	private List<Wave> waves = new ArrayList();
	private List<Tower> towers = new ArrayList();
	private int waveNr = 0;

	public WaveManager(Mover mover, MainRender render, HUD hud) {
		this.mover = mover;
		this.render = render;
		this.hud = hud;

		createListOfWaves();
	}

	private void createListOfWaves() {
		waves.add(new Wave(5, 5, 1, 0, 6, 1, 0, 25, 1, mover, render, towers, hud));
		waves.add(new Wave(5, 5, 1, 2, 6, 1, 0, 25, 1, mover, render, towers, hud));
		waves.add(new Wave(10, 8, 1, 4, 8, 1, 1, 25, 1, mover, render, towers, hud));
		waves.add(new Wave(10, 8, 1, 4, 8, 1, 0, 25, 1, mover, render, towers, hud));
		waves.add(new Wave(15, 8, 1, 8, 8, 1, 4, 25, 1, mover, render, towers, hud));

		waves.add(new Wave(5, 11, 1, 10, 13, 1, 0, 25, 1, mover, render, towers, hud));
		waves.add(new Wave(5, 11, 1, 13, 13, 1, 0, 25, 1, mover, render, towers, hud));
		waves.add(new Wave(7, 15, 1, 15, 18, 1, 0, 25, 1, mover, render, towers, hud));
		waves.add(new Wave(10, 15, 1, 20, 18, 1, 2, 40, 1, mover, render, towers, hud));
		waves.add(new Wave(13, 15, 1, 20, 18, 1, 5, 40, 1, mover, render, towers, hud));

		waves.add(new Wave(15, 20, 2, 10, 25, 2, 0, 20, 2, mover, render, towers, hud));
		waves.add(new Wave(15, 20, 2, 13, 25, 2, 0, 20, 2, mover, render, towers, hud));
		waves.add(new Wave(17, 20, 2, 15, 25, 2, 0, 20, 2, mover, render, towers, hud));
		waves.add(new Wave(20, 25, 2, 20, 30, 2, 2, 55, 2, mover, render, towers, hud));
		waves.add(new Wave(23, 25, 2, 20, 30, 2, 5, 55, 2, mover, render, towers, hud));

		waves.add(new Wave(5, 30, 1, 0, 36, 1, 0, 20, 1, mover, render, towers, hud));
		waves.add(new Wave(5, 30, 4, 3, 36, 1, 2, 80, 1, mover, render, towers, hud));
		waves.add(new Wave(7, 30, 1, 5, 40, 1, 5, 80, 1, mover, render, towers, hud));

		waves.add(new Wave(30, 45, 1, 15, 55, 1, 5, 100, 1, mover, render, towers, hud));
		waves.add(new Wave(45, 45, 1, 25, 55, 2, 10, 120, 1, mover, render, towers, hud));

		render.setTotalOfWaves(waves.size());
	}

	public void send() {
		if (waves.size() > waveNr) {
			((Wave) waves.get(waveNr)).sendWave();

			waveNr += 1;
			render.increaseWaveNrByOne();
		} else {
			System.out.println("slut på waves");
		}
	}

	public void resetWavesAndTowers() {
		for (int i = waves.size() - 1; i > -1; i--) {
			waves.remove(i);
		}

		for (int i = towers.size() - 1; i > -1; i--) {
			towers.remove(i);
		}
		createListOfWaves();
	}

	public int getWaveSize() {
		System.out.println("waveNR: " + waveNr + "   Wave Size: " + ((Wave) waves.get(waveNr)).getSizeOfWave());
		return ((Wave) waves.get(waveNr)).getSizeOfWave();
	}

	public int getWaveNr() {
		return waveNr;
	}

	public Wave getCurrentWave() {
		return (Wave) waves.get(render.getHud().getCurrentWave());
	}

	public void receiveTower(Tower tower) {
		towers.add(tower);
	}

	public List<Tower> getTowers() {
		return towers;
	}

	public void setTowers(List<Tower> towers) {
		this.towers = towers;
	}

	public void setWaveNr(int waveNr) {
		this.waveNr = waveNr;
	}

	public int getWavesSize() {
		return waves.size();
	}
}
