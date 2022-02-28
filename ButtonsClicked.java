package project_oop;

import java.awt.Rectangle;
import java.io.PrintStream;
import java.util.List;

import project_oop.main.graphics.MainRender;

public class ButtonsClicked {
	private boolean gamePaused = false;
	private boolean gameStarted = false;
	private Game game;

	public ButtonsClicked(Mover mover, MainRender render, WaveManager waveManager, HUD hud, TowerHandler towerHandler,
			Game game) {
		this.mover = mover;
		this.render = render;
		this.waveManager = waveManager;
		this.hud = hud;
		this.towerHandler = towerHandler;
		sound = new Sound();
		this.game = game;
	}

	private Sound sound;
	private Tower towerTemp;
	private TowerHandler towerHandler;

	public void getHud(HUD hud) {
	}

	public void testIfAnyButtonIsPressed(int releasedX, int releasedY) {
		if ((releasedX < 640) && (releasedX > 0) && (releasedY < 576) && (releasedY > 0) && (gameStarted)) {
			if ((hud.getSoundOnOffBounds().contains(releasedX, releasedY))
					&& (hud.getSoundOnOffBounds().contains(pressedX, pressedY))) {
				hud.changeMusicOnOff();
				if (hud.getMusicPaused()) {
					sound.pause();
				} else if (!hud.getMusicPaused()) {
					sound.play();
				}
			} else if ((hud.getSoundMinusBounds().contains(releasedX, releasedY))
					&& (hud.getSoundMinusBounds().contains(pressedX, pressedY))) {
				sound.changeVolume(-2.0F);
			} else if ((hud.getSoundPlusBounds().contains(releasedX, releasedY))
					&& (hud.getSoundPlusBounds().contains(pressedX, pressedY))) {
				sound.changeVolume(2.0F);
			}

			if ((hud.getPause().contains(releasedX, releasedY)) && (hud.getPause().contains(pressedX, pressedY))) {
				changePaused();
			} else if ((hud.getNextWave().contains(releasedX, releasedY))
					&& (hud.getNextWave().contains(pressedX, pressedY)) && (!gamePaused)) {
				if ((render.getSizeOfWave() == 0) || (waveManager.getWaveNr() == 0)) {

					waveManager.send();
					hud.setCountDownStarted(false);
					game.setWaveCountDown(false);
				} else {
					System.out.println("not yet,  (ButtonsClicked) render.getSizeOFwave ==" + render.getSizeOfWave());
				}
			} else if ((hud.getTower1().contains(releasedX, releasedY))
					&& (hud.getTower1().contains(pressedX, pressedY)) && (!gamePaused)) {
				render.changeTowerClicked(1);
			} else if ((hud.getTower2().contains(releasedX, releasedY))
					&& (hud.getTower2().contains(pressedX, pressedY)) && (!gamePaused)) {
				render.changeTowerClicked(2);
			} else if ((hud.getTower3().contains(releasedX, releasedY))
					&& (hud.getTower3().contains(pressedX, pressedY)) && (!gamePaused)) {
				render.changeTowerClicked(3);
			} else if ((hud.getTower4().contains(releasedX, releasedY))
					&& (hud.getTower4().contains(pressedX, pressedY)) && (!gamePaused)) {
				render.changeTowerClicked(4);
			} else if ((releasedY < 480) && (towerHandler.findIfTowerIsHere(releasedX, releasedY) != 0)
					&& (!gamePaused)) {
				towerTemp = towerHandler.getTowerAtLoc(releasedX, releasedY);
				if (towerTemp.getTowerLevel() == 3) {
					hud.setIsTowerMaxLevel(true);
				} else
					hud.setIsTowerMaxLevel(false);
				System.out.println("hej1   " + towerTemp.getTowerNumber());
				hud.setTowerClicked(true);
				hud.setTowerClickedTwo(towerTemp.getTowerDmgString());
				hud.setTowerClickedOne(towerTemp.getTowerNameString());
				hud.setTowerClickedThree(towerTemp.getTowerSpeedString());
				hud.setTowerLevel(towerTemp.getTowerLevelString());
				hud.setTowerUpgradeCost(towerTemp.getTowerUpgradeCost());
				hud.setTowerSellAmount(towerTemp.getSellAmount());

			} else if (((releasedY >= 576) || (releasedY <= 480)) && (!gamePaused)) {
				hud.setTowerClicked(false);
			} else if ((hud.getTowerClicked()) && (!gamePaused)) {
				if ((hud.getTowerUpgrade().contains(releasedX, releasedY))
						&& (hud.getTowerUpgrade().contains(pressedX, pressedY)) && (towerTemp.getTowerLevel() != 3)) {
					if (hud.getAmountOfGold() >= towerTemp.getTowerUpgradeCost()) {
						hud.addGold(-towerTemp.getTowerUpgradeCost());
						towerTemp.upgradeTower();
						if (towerTemp.getTowerLevel() == 3)
							hud.setIsTowerMaxLevel(true);
						hud.setTowerClickedTwo(towerTemp.getTowerDmgString());
						hud.setTowerClickedOne(towerTemp.getTowerNameString());
						hud.setTowerClickedThree(towerTemp.getTowerSpeedString());
						hud.setTowerLevel(towerTemp.getTowerLevelString());
						hud.setTowerUpgradeCost(towerTemp.getTowerUpgradeCost());
						hud.setTowerSellAmount(towerTemp.getSellAmount());
					} else {
						System.out.println("Aja bajja, ej cash bror!!! (ButtonsClicked)");
					}
				} else if ((hud.getTowerSell().contains(releasedX, releasedY))
						&& (hud.getTowerSell().contains(pressedX, pressedY))) {

					hud.addGold(towerTemp.getSellAmount());
					towerHandler.removeTowerHere(towerTemp.getX(), towerTemp.getY());
					mover.getTowerList().remove(towerTemp);
					render.getTowerList().remove(towerTemp);
					towerHandler.getTowers().remove(towerTemp);
					hud.setTowerClicked(false);
				}
			}
		}
	}

	private WaveManager waveManager;
	private int pressedY;
	private int pressedX;

	private void changePaused() {
		render.changePaused();
		mover.changePaused();
		if (gamePaused) {
			gamePaused = false;
		} else
			gamePaused = true;
		game.getButtonsHoveredOver().changePaused();
	}

	private HUD hud;
	private MainRender render;
	private Mover mover;

	public int getPressedX() {
		return pressedX;
	}

	public void setPressedX(int pressedX) {
		this.pressedX = pressedX;
	}

	public int getPressedY() {
		return pressedY;
	}

	public void setPressedY(int pressedY) {
		this.pressedY = pressedY;
	}

	public Sound getSoundClass() {
		return sound;
	}

	public void setGameStartedToTrue() {
		gameStarted = true;
	}
}
