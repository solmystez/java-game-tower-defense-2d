package project_oop;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class HUD {
	private int currentWave = 0;
	private int amountOfGold = 350;
	private int towerSellAmount = 666;
	private double countDownDoubleStartValue = 15.1D;

	private final int START_LIFE = 20;
	private boolean gameOver = false;
	private boolean towerClicked = false;
	private boolean isTowerMaxLevel = false;

	private Color pausedColor = Color.BLACK;
	private Color nextWaveColor = Color.BLACK;
	private Color t1Color = Color.BLACK;
	private Color t2Color = Color.BLACK;
	private Color t3Color = Color.BLACK;
	private Color t4Color = Color.BLACK;
	private Color tUpgrade = Color.BLACK;
	private Color tSell = Color.BLACK;
	private boolean isPaused = false;
	private boolean musicPaused = false;
	private boolean countDownStarted = false;

	private String towerInfo1 = " ";
	private String towerInfo2 = " ";
	private String towerInfo3 = " ";

	private String towerClickedOne = "NAME_HERE";
	private String towerClickedTwo = "CURR_DMG_HERE";
	private String towerClickedThree = "CURR_SPEE_HERE";
	private String towerLevel = "CURR_LVL_HERE";
	private int x;
	private int y;
	private int width;

	public HUD(int x, int y, int width, int height, Game game) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		life = 20;
		this.game = game;
		countDownDouble = countDownDoubleStartValue;
		countDownString = ("Next Wave In: " + (int) countDownDouble);

		loader = new BufferImageLoader("spritesheet.png");
		bowTower = loader.grabSprite(2, 2);
		iceTower = loader.grabSprite(4, 2);
		missileTower = loader.grabSprite(7, 3);
		hammerTower = loader.grabSprite(7, 2);
		soundOn = loader.grabSpriteAtASpecificCords(3, 7, 17, 19, 0, 0);
		soundOff = loader.grabSpriteAtASpecificCords(4, 7, 25, 19, 0, 0);
		soundPlus = loader.grabSpriteAtASpecificCords(5, 7, 16, 16, 0, 0);
		soundMinus = loader.grabSpriteAtASpecificCords(5, 7, 16, 2, 16, 0);

		setRectBounds();
	}

	private int height;
	private int life;

	public void setRectBounds() {
		pause = new Rectangle(x + 520, y + 50, 100, 30);
		nextWave = new Rectangle(x + 520, y + 10, 100, 30);
		tower1 = new Rectangle(x + 130, y + 30, 36, 36);
		tower2 = new Rectangle(x + 170, y + 30, 36, 36);
		tower3 = new Rectangle(x + 210, y + 30, 36, 36);
		tower4 = new Rectangle(x + 250, y + 30, 36, 36);
		towerUpgrade = new Rectangle(x + 420, y + 10, 75, 25);
		towerSell = new Rectangle(x + 420, y + 50, 75, 25);

		soundOnOffBounds = new Rectangle(5, 2, 17, 19);
		soundPlusBounds = new Rectangle(35, 2, 16, 16);
		soundMinusBounds = new Rectangle(55, 2, 16, 16);
	}

	private int sizeOfWave;
	private int totalNrOfWaves;
	private int towerUpgradeCost;
	private double countDownDouble;
	private BufferImageLoader loader;
	private BufferedImage bowTower;

	public void draw(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillRect(x, y, width, height);
		g.setColor(Color.BLUE);
		g.drawRect(x, y, width - 1, height - 1);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Impact", 1, 16));
		g.drawString("Life: " + life, x + 10, y + 20);
		g.drawString("Wave: " + currentWave + " / " + totalNrOfWaves, x + 10, y + 50);
		g.drawString("Size: " + sizeOfWave, x + 10, y + 80);

		g.setColor(Color.BLUE);
		g.drawLine(x + 120, y, x + 120, y + 100);
		g.drawLine(x + 122, y, x + 122, y + 100);
		g.setColor(Color.WHITE);
		g.drawLine(x + 121, y, x + 121, y + 100);

		g.setColor(Color.BLUE);
		g.drawLine(x + 295, y, x + 295, y + 100);
		g.drawLine(x + 297, y, x + 297, y + 100);
		g.setColor(Color.WHITE);
		g.drawLine(x + 296, y, x + 296, y + 100);

		if (towerClicked) {
			g.setFont(new Font("Impact", 1, 13));
			g.setColor(tSell);
			g.fillRect(towerSell.x, towerSell.y, towerSell.width, towerSell.height);
			g.setColor(Color.GREEN);
			g.drawRect(towerSell.x, towerSell.y, towerSell.width, towerSell.height);
			g.setColor(Color.BLACK);
			g.drawString(towerClickedOne, x + 300, y + 20);
			g.drawString(towerClickedTwo, x + 300, y + 38);
			g.drawString(towerClickedThree, x + 300, y + 56);
			g.drawString(towerLevel, x + 300, y + 74);

			g.setColor(Color.WHITE);
			g.setFont(new Font("Impact", 1, 11));
			g.drawString("Sell " + towerSellAmount, towerSell.x + 5, towerSell.y + 18);
			if (!isTowerMaxLevel) {

				g.setColor(tUpgrade);
				g.fillRect(towerUpgrade.x, towerUpgrade.y, towerUpgrade.width, towerUpgrade.height);
				g.setColor(Color.GREEN);
				g.drawRect(towerUpgrade.x, towerUpgrade.y, towerUpgrade.width, towerUpgrade.height);
				g.setColor(Color.WHITE);
				g.setFont(new Font("Impact", 0, 13));
				g.drawString("Upgrade " + towerUpgradeCost, towerUpgrade.x + 5, towerUpgrade.y + 18);
			}
		}

		g.setFont(new Font("Impact", 1, 16));
		g.setColor(Color.BLUE);
		g.drawLine(x + 500, y, x + 500, y + 100);
		g.drawLine(x + 502, y, x + 502, y + 100);
		g.setColor(Color.WHITE);
		g.drawLine(x + 501, y, x + 501, y + 100);

		g.setColor(Color.BLACK);

		g.drawString("Gold: " + amountOfGold, x + 130, y + 20);

		g.setFont(new Font("Arial", 1, 12));
		g.drawString(towerInfo1, x + 135, y + 78);
		g.drawString(towerInfo2, x + 135, y + 90);
		g.drawString(towerInfo3, x + 210, y + 90);

		g.setFont(new Font("Impact", 1, 16));

		g.setColor(t1Color);
		g.fillRect(tower1.x, tower1.y, tower1.width, tower1.height);
		g.drawImage(bowTower, tower1.x + 2, tower1.y + 2, null);

		g.setColor(t2Color);
		g.fillRect(tower2.x, tower2.y, tower2.width, tower2.height);
		g.drawImage(iceTower, tower2.x + 2, tower2.y + 2, null);

		g.setColor(t3Color);
		g.fillRect(tower3.x, tower3.y, tower3.width, tower3.height);
		g.drawImage(missileTower, tower3.x + 2, tower3.y + 2, null);

		g.setColor(t4Color);
		g.fillRect(tower4.x, tower4.y, tower4.width, tower4.height);
		g.drawImage(hammerTower, tower4.x + 2, tower4.y + 2, null);

		g.setColor(nextWaveColor);
		g.fillRect(nextWave.x, nextWave.y, nextWave.width, nextWave.height);

		g.setColor(pausedColor);
		g.fillRect(pause.x, pause.y, pause.width, pause.height);
		g.setColor(Color.GREEN);
		g.drawRect(x + 520, y + 10, 100, 30);
		g.drawRect(x + 521, y + 11, 98, 28);

		g.drawRect(x + 520, y + 50, 100, 30);
		g.drawRect(x + 521, y + 51, 98, 30);

		g.setColor(Color.WHITE);
		if (game.getWaveManager().getWaveNr() == 0) {
			g.drawString("Send Wave", x + 532, y + 31);
		} else {
			g.drawString("Next Wave", x + 532, y + 31);
		}
		if (!isPaused) {
			g.drawString("    Pause", x + 532, y + 71);
		} else {
			g.drawString("  Unpause", x + 532, y + 71);
		}

		if (musicPaused) {
			g.drawImage(soundOff, soundOnOffBounds.x, soundOnOffBounds.y, null);
		} else {
			g.drawImage(soundOn, soundOnOffBounds.x, soundOnOffBounds.y, null);
		}
		g.drawImage(soundPlus, soundPlusBounds.x, soundPlusBounds.y, null);
		g.drawImage(soundMinus, soundMinusBounds.x, soundMinusBounds.y + 7, null);

		if (countDownStarted) {
			g.setFont(new Font("Arial", 1, 25));
			g.setColor(Color.BLACK);
			g.drawString(countDownString, 430, 470);
		}
	}

	public void changePaused() {
		if (!isPaused) {
			isPaused = true;
		} else {
			isPaused = false;
		}
	}

	public void reduceLife() {
		life -= 1;
	}

	private BufferedImage iceTower;
	private BufferedImage missileTower;
	private BufferedImage hammerTower;
	private BufferedImage soundOn;

	public int getSizeOfWave() {
		return sizeOfWave;
	}

	public void checkTimer() {
		if (countDownStarted) {
			countDownDouble -= 0.1D;
			updateCountdownTimerString();
			if (countDownDouble <= 0.0D) {
				sendWave();
				countDownStarted = false;
				game.setWaveCountDown(false);
			}
		}
	}

	public void sendWave() {
		game.getWaveManager().send();
	}

	public void setSizeOfWave(int sizeOfWave) {
		this.sizeOfWave = sizeOfWave;
	}

	public void reduceSizeOfWaveByOne() {
		sizeOfWave -= 1;

		if ((sizeOfWave == 0) && (game.getWaveManager().getWavesSize() >= game.getWaveManager().getWaveNr())) {
			countDownStarted = true;
			game.setWaveCountDown(true);
			countDownDouble = countDownDoubleStartValue;

		} else if ((sizeOfWave == 0) && (game.getWaveManager().getWavesSize() == game.getWaveManager().getWaveNr())) {
			game.gameIsWon();
		}
	}

	public void increaseWaveNrByOne() {
		currentWave += 1;
	}

	public void setTotalNrOfWaves(int total) {
		totalNrOfWaves = total;
	}

	public void addGold(int amount) {
		amountOfGold += amount;
	}

	public void reduceGold(int amount) {
		amountOfGold -= amount;
	}

	public void updateCountdownTimerString() {
		countDownString = ("Next Wave In: " + (int) countDownDouble);
	}

	private BufferedImage soundOff;
	private BufferedImage soundMinus;
	private BufferedImage soundPlus;
	private Rectangle pause;

	public void setPauseColorBlack() {
		pausedColor = Color.BLACK;
	}

	public void setPauseColorGray() {
		pausedColor = Color.GRAY;
	}

	public void setNextWaveColorBlack() {
		nextWaveColor = Color.BLACK;
	}

	public void setNextWaveColorGray() {
		nextWaveColor = Color.GRAY;
	}

	public void setT1ColorBlack() {
		t1Color = Color.BLACK;
	}

	public void setT2ColorBlack() {
		t2Color = Color.BLACK;
	}

	public void setT3ColorBlack() {
		t3Color = Color.BLACK;
	}

	public void setT4ColorBlack() {
		t4Color = Color.BLACK;
	}

	public void setT1ColorGray() {
		t1Color = Color.GRAY;
	}

	public void setT2ColorGray() {
		t2Color = Color.GRAY;
	}

	public void setT3ColorGray() {
		t3Color = Color.GRAY;
	}

	public void setT4ColorGray() {
		t4Color = Color.GRAY;
	}

	private Rectangle nextWave;
	private Rectangle tower1;
	private Rectangle tower2;
	private Rectangle tower3;
	private Rectangle tower4;

	public Rectangle getPause() {
		return pause;
	}

	private Rectangle towerUpgrade;

	public void setPause(Rectangle pause) {
		this.pause = pause;
	}

	private Rectangle towerSell;

	public Rectangle getNextWave() {
		return nextWave;
	}

	private Rectangle soundOnOffBounds;

	public void setNextWave(Rectangle nextWave) {
		this.nextWave = nextWave;
	}

	private Rectangle soundPlusBounds;

	public Rectangle getTower1() {
		return tower1;
	}

	private Rectangle soundMinusBounds;

	public void setTower1(Rectangle tower1) {
		this.tower1 = tower1;
	}

	private String countDownString;
	private Game game;

	public Rectangle getTower2() {
		return tower2;
	}

	public void setTower2(Rectangle tower2) {
		this.tower2 = tower2;
	}

	public Rectangle getTower3() {
		return tower3;
	}

	public void setTower3(Rectangle tower3) {
		this.tower3 = tower3;
	}

	public Rectangle getTower4() {
		return tower4;
	}

	public void setTower4(Rectangle tower4) {
		this.tower4 = tower4;
	}

	public void setTowerInfoString(String info1, String info2, String info3) {
		towerInfo1 = info1;
		towerInfo2 = info2;
		towerInfo3 = info3;
	}

	public int getCurrentWave() {
		if (currentWave == 0) {
			return currentWave;
		}
		return currentWave - 1;
	}

	public int getAmountOfGold() {
		return amountOfGold;
	}

	public void setTowerClicked(boolean state) {
		towerClicked = state;
	}

	public String getTowerClickedOne() {
		return towerClickedOne;
	}

	public void setTowerClickedOne(String towerClickedOne) {
		this.towerClickedOne = towerClickedOne;
	}

	public String getTowerClickedTwo() {
		return towerClickedTwo;
	}

	public void setTowerClickedTwo(String towerClickedTwo) {
		this.towerClickedTwo = towerClickedTwo;
	}

	public String getTowerClickedThree() {
		return towerClickedThree;
	}

	public void setTowerClickedThree(String towerClickedThree) {
		this.towerClickedThree = towerClickedThree;
	}

	public String getTowerLevel() {
		return towerLevel;
	}

	public void setTowerLevel(String towerLevel) {
		this.towerLevel = towerLevel;
	}

	public Rectangle getTowerSell() {
		return towerSell;
	}

	public Rectangle getTowerUpgrade() {
		return towerUpgrade;
	}

	public boolean getTowerClicked() {
		return towerClicked;
	}

	public void setTowerSellColorBlack() {
		tSell = Color.BLACK;
	}

	public void setTowerSellColorGray() {
		tSell = Color.GRAY;
	}

	public void setTowerUpgradeColorBlack() {
		tUpgrade = Color.BLACK;
	}

	public void setTowerUpgradeColorGray() {
		tUpgrade = Color.GRAY;
	}

	public void setTowerUpgradeCost(int towerUpgradeCost) {
		this.towerUpgradeCost = towerUpgradeCost;
	}

	public void setTowerSellAmount(int towerSellAmount) {
		this.towerSellAmount = towerSellAmount;
	}

	public void setIsTowerMaxLevel(boolean state) {
		isTowerMaxLevel = state;
	}

	public Rectangle getSoundOnOffBounds() {
		return soundOnOffBounds;
	}

	public void setSoundOnOffBounds(Rectangle soundOnOffBounds) {
		this.soundOnOffBounds = soundOnOffBounds;
	}

	public Rectangle getSoundPlusBounds() {
		return soundPlusBounds;
	}

	public void setSoundPlusBounds(Rectangle soundPlusBounds) {
		this.soundPlusBounds = soundPlusBounds;
	}

	public Rectangle getSoundMinusBounds() {
		return soundMinusBounds;
	}

	public void setSoundMinusBounds(Rectangle soundMinusBounds) {
		this.soundMinusBounds = soundMinusBounds;
	}

	public void changeMusicOnOff() {
		if (musicPaused) {
			musicPaused = false;
		} else
			musicPaused = true;
	}

	public boolean getMusicPaused() {
		return musicPaused;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getStartLife() {
		return 20;
	}

	public void setCurrentWave(int currentWave) {
		this.currentWave = currentWave;
	}

	public void setCountDownStarted(boolean countDownStarted) {
		this.countDownStarted = countDownStarted;
	}
}
