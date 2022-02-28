package project_oop;

import java.awt.Rectangle;

public class ButtonsHoveredOver {
	private HUD hud;
	private boolean gamePaused = false;

	public ButtonsHoveredOver() {
	}

	public void getHud(HUD hud) {
		this.hud = hud;
	}

	public void testIfMouseIsOver(int x, int y) {
		if (hud.getPause().contains(x, y)) {
			hud.setPauseColorGray();
		} else if ((hud.getNextWave().contains(x, y)) && (!gamePaused)) {
			hud.setNextWaveColorGray();
		} else if ((hud.getTower1().contains(x, y)) && (!gamePaused)) {
			hud.setT1ColorGray();
			hud.setTowerInfoString("Bow-Tower, Air/Ground", "Low DMG", "Cost: 150 Gold");
		} else if ((hud.getTower2().contains(x, y)) && (!gamePaused)) {
			hud.setT2ColorGray();
			hud.setTowerInfoString("Ice-Tower, Air/Ground", "Stuns Target", "Cost: 270 Gold");
		} else if ((hud.getTower3().contains(x, y)) && (!gamePaused)) {
			hud.setT3ColorGray();
			hud.setTowerInfoString("Missile-Tower, Air", "High DMG", "Cost: 310 Gold");
		} else if ((hud.getTower4().contains(x, y)) && (!gamePaused)) {
			hud.setT4ColorGray();
			hud.setTowerInfoString("Hammer-Tower, Ground", "High DMG", "Cost: 400 Gold");
		} else if (hud.getTowerSell().contains(x, y)) {
			hud.setTowerSellColorGray();
		} else if (hud.getTowerUpgrade().contains(x, y)) {
			hud.setTowerUpgradeColorGray();
		} else {
			resetAllColors();
		}
	}

	private void resetAllColors() {
		hud.setPauseColorBlack();
		hud.setNextWaveColorBlack();
		hud.setT1ColorBlack();
		hud.setT2ColorBlack();
		hud.setT3ColorBlack();
		hud.setT4ColorBlack();
		hud.setTowerSellColorBlack();
		hud.setTowerUpgradeColorBlack();
		hud.setTowerInfoString(" ", " ", " ");
	}

	public void changePaused() {
		if (gamePaused) {
			gamePaused = false;
		} else {
			gamePaused = true;
		}
	}
}
