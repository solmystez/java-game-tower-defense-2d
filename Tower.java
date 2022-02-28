package project_oop;

import java.awt.Rectangle;
import java.util.List;

public class Tower {
	private int x;
	private int y;
	private int typeOfTower;
	private java.awt.image.BufferedImage towerImg;
	private java.awt.image.BufferedImage towerImgTwo;
	private java.awt.image.BufferedImage towerImgThree;
	private BufferImageLoader loader = new BufferImageLoader("spritesheet.png");
	private List<Projectile> projectiles = new java.util.ArrayList();
	private List<Enemy> enemies;
	private Enemy target;
	private long timer;
	private boolean firstCall = true;
	private boolean targetHasBeenFound = false;
	private Rectangle towerRange;
	private double speed;
	private double towerDmg;
	private int towerNumber;
	private int towerLevel = 1;
	private String towerNameString;
	private String towerDmgString;
	private String towerSpeedString;
	private String towerLevelString;
	private int upgradeOne;
	private int upgradeTwo;
	private int sellAmount;
	private int tileSize = 32;

	public Tower(int x, int y, int typeOfTower, List<Enemy> enemies, int towerNumber) {
		this.x = x;
		this.y = y;
		this.typeOfTower = typeOfTower;
		this.enemies = enemies;
		this.towerNumber = towerNumber;

		towerRange = new Rectangle(this.x - 64, this.y - 64, 160, 160);

		if (typeOfTower == 1) {
			towerImg = loader.grabSprite(2, 2);
			towerImgTwo = loader.grabSprite(1, 6);
			towerImgThree = loader.grabSprite(2, 6);
			towerDmg = 2.0D;
			towerNameString = "Bow Tower";
			towerDmgString = ("Current Dmg: " + towerDmg);
			speed = 1000.0D;
			towerSpeedString = ("Firing Speed: " + speed / 1000.0D);
			upgradeOne = 90;
			upgradeTwo = 90;
			sellAmount = 75;

		} else if (typeOfTower == 2) {
			towerImg = loader.grabSprite(4, 2);
			towerImgTwo = loader.grabSprite(3, 6);
			towerImgThree = loader.grabSprite(4, 6);
			towerDmg = 0.0D;
			towerNameString = "Ice Tower";
			towerDmgString = ("Current Dmg: " + towerDmg);
			speed = 4000.0D;
			towerSpeedString = ("Firing Speed: " + speed / 1000.0D);
			upgradeOne = 100;
			upgradeTwo = 120;
			sellAmount = 135;

		} else if (typeOfTower == 3) {
			towerImg = loader.grabSprite(7, 3);
			towerImgTwo = loader.grabSprite(1, 7);
			towerImgThree = loader.grabSprite(2, 7);
			towerDmg = 2.0D;
			towerNameString = "Missile Tower";
			towerDmgString = ("Current Dmg: " + towerDmg);
			speed = 500.0D;
			towerSpeedString = ("Firing Speed: " + speed / 1000.0D);
			upgradeOne = 90;
			upgradeTwo = 100;
			sellAmount = 155;

		} else if (typeOfTower == 4) {
			towerImg = loader.grabSprite(7, 2);
			towerImgTwo = loader.grabSprite(5, 6);
			towerImgThree = loader.grabSprite(6, 6);
			towerDmg = 6.0D;
			towerNameString = "Hammer Tower";
			towerDmgString = ("Current Dmg: " + towerDmg);
			speed = 1000.0D;
			towerSpeedString = ("Firing Speed: " + speed / 1000.0D);

			sellAmount = 200;
			upgradeOne = 150;
			upgradeTwo = 150;
		}
	}

	private float calculateAngle() {
		double angleTemp = Math.atan2(target.getY() - y, target.getX() - x);
		return (float) Math.toDegrees(angleTemp) + 90.0F;
	}

	public void draw(java.awt.Graphics g) {
		for (Projectile p : projectiles) {
			p.draw(g);
		}

		if (towerLevel == 1) {
			g.drawImage(towerImg, x, y, null);
		} else if (towerLevel == 2) {
			g.drawImage(towerImgTwo, x, y, null);
		} else if (towerLevel == 3) {
			g.drawImage(towerImgThree, x, y, null);
		}
	}

	public void update() {
		for (int i = 0; i < projectiles.size(); i++) {
			for (int j = 0; j < enemies.size(); j++) {
				if ((((Enemy) enemies.get(j)).getAlive()) && (((Projectile) projectiles.get(i)).getRectangle()
						.intersects(((Enemy) enemies.get(j)).bounds()))) {
					if (typeOfTower == 1) {
						((Enemy) enemies.get(j)).hitByArrow(towerDmg);
					} else if (typeOfTower == 2) {
						((Enemy) enemies.get(j)).hitByIce();
					} else if (typeOfTower == 3) {
						((Enemy) enemies.get(j)).hitByMissile(towerDmg);
					} else if (typeOfTower == 4) {
						((Enemy) enemies.get(j)).hitByHammer(towerDmg);
					}
					((Projectile) projectiles.get(i)).setArrowDead();
				}
			}
		}

		if (firstCall) {
			firstCall = false;
			timer = System.currentTimeMillis();
		}

		if (!targetHasBeenFound)
			findTarget();
		if (targetHasBeenFound) {
			if (typeOfTower == 1) {

				fireArrow();

			} else if (typeOfTower == 2) {

				fireIceBlock();
			} else if (typeOfTower == 3) {

				fireMissle();
			} else if (typeOfTower == 4) {

				fireHammer();
			}
		}

		moveArrow();
		checkAndRemoveArrow();
	}

	private void fireHammer() {
		if ((checkIfTargetIsStillWithinRange(target)) && (target.getEnemyType() != 2)) {
			if (System.currentTimeMillis() - timer > speed) {
				timer = System.currentTimeMillis();
				projectiles.add(new Projectile(x + 16, y + 16, 5, 4, (int) calculateAngle(), target, false));
			}
		} else
			findNewTarget();
	}

	private void fireMissle() {
		if ((checkIfTargetIsStillWithinRange(target)) && (target.getEnemyType() == 2)) {
			if (System.currentTimeMillis() - timer > speed) {
				timer = System.currentTimeMillis();
				projectiles.add(new Projectile(x + 16, y + 16, 10, 3, (int) calculateAngle(), target, false));
			}
		} else
			findNewTarget();
	}

	private void fireIceBlock() {
		if ((checkIfTargetIsStillWithinRange(target)) && (!target.isFrozen())) {
			if (System.currentTimeMillis() - timer > speed) {
				timer = System.currentTimeMillis();
				projectiles.add(new Projectile(x + 16, y + 16, 10, 2, (int) calculateAngle(), target, true));
			}
		} else {
			findNewTarget();
		}
	}

	private void fireArrow() {
		if (checkIfTargetIsStillWithinRange(target)) {
			if (System.currentTimeMillis() - timer > speed) {
				timer = System.currentTimeMillis();
				projectiles.add(new Projectile(x + 16, y + 16, 20, 1, (int) calculateAngle(), target, false));
			}
		} else {
			findNewTarget();
		}
	}

	private void findNewTarget() {
		targetHasBeenFound = false;
		findTarget();
	}

	private void findTarget() {
		for (Enemy e : enemies) {
			if (typeOfTower == 2) {
				if ((e.getAlive()) && (e.bounds().intersects(towerRange)) && (!e.isFrozen())) {
					target = e;
					targetHasBeenFound = true;
					break;
				}
			} else if ((e.getAlive()) && (e.bounds().intersects(towerRange))) {
				target = e;
				targetHasBeenFound = true;
				break;
			}
		}
	}

	private boolean checkIfTargetIsStillWithinRange(Enemy target) {
		if ((target.bounds().intersects(towerRange)) && (target.getAlive())) {
			return true;
		}
		return false;
	}

	private void moveArrow() {
		for (Projectile p : projectiles) {
			if (p.getArrowNotDeadState()) {
				p.move();
				if (arrowOutsideMap(getRectanglex, getRectangley)) {
					p.setArrowDead();
				}
			}
		}
	}

	private void checkAndRemoveArrow() {
		for (int i = 0; i < projectiles.size(); i++) {
			if (!((Projectile) projectiles.get(i)).getArrowNotDeadState()) {
				projectiles.remove(i);
			}
		}
	}

	private boolean arrowOutsideMap(int x, int y) {
		if ((x > 0) && (x < 640) && (y > 0) && (y < 480)) {
			return false;
		}
		return true;
	}

	public void increaseTowerLevel() {
		if (towerLevel <= 3) {
			towerLevel += 1;
			towerLevelString = (" Lvl: " + towerLevel);
		} else {
			System.out.println("max lvl already (towerClass)");
		}
	}

	public void receiveEnemyList(List<Enemy> testar) {
		enemies = testar;
	}

	public int getTowerNumber() {
		return towerNumber;
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

	public int getTypeOfTower() {
		return typeOfTower;
	}

	public void setTypeOfTower(int typeOfTower) {
		this.typeOfTower = typeOfTower;
	}

	public Rectangle getTowerRange() {
		return towerRange;
	}

	public void setTowerRange(Rectangle towerRange) {
		this.towerRange = towerRange;
	}

	public double getTowerDmg() {
		return towerDmg;
	}

	public void setTowerDmg(double towerDmg) {
		this.towerDmg = towerDmg;
	}

	public void setTowerNumber(int towerNumber) {
		this.towerNumber = towerNumber;
	}

	public String getTowerNameString() {
		return towerNameString;
	}

	public String getTowerDmgString() {
		return towerDmgString;
	}

	public void setTowerDmgString(String towerDmgString) {
		this.towerDmgString = towerDmgString;
	}

	public String getTowerSpeedString() {
		return towerSpeedString;
	}

	public void setTowerSpeedString(String towerSpeedString) {
		this.towerSpeedString = towerSpeedString;
	}

	public void setTowerLevel(int lvl) {
		towerLevel = lvl;
	}

	public int getTowerLevel() {
		return towerLevel;
	}

	public String getTowerLevelString() {
		towerLevelString = ("Lvl: " + towerLevel);
		return towerLevelString;
	}

	public int getTowerUpgradeCost() {
		if (towerLevel == 1)
			return upgradeOne;
		if (towerLevel == 2) {
			return upgradeTwo;
		}
		return 0;
	}

	public void upgradeTower() {
		if (towerLevel == 1) {
			upgradeStatsDependingOnTowerType();
			towerLevel = 2;
			sellAmount += upgradeOne / 2;
		} else if (towerLevel == 2) {
			upgradeStatsDependingOnTowerType();
			towerLevel = 3;
			sellAmount += upgradeTwo / 2;
		} else {
			System.out.println("nope! (tower class)");
		}
	}

	private void upgradeStatsDependingOnTowerType() {
		if (typeOfTower == 1) {
			if (towerLevel == 1) {
				towerDmg = 3.5D;
				towerDmgString = ("Current Dmg: " + towerDmg);
			} else if (towerLevel == 2) {
				towerDmg = 5.5D;
				towerDmgString = ("Current Dmg: " + towerDmg);
			}

		} else if (typeOfTower == 2) {
			if (towerLevel == 1) {
				speed = 3500.0D;
				towerSpeedString = ("Firing Speed: " + speed / 1000.0D);
			} else if (towerLevel == 2) {
				speed = 2500.0D;
				towerSpeedString = ("Firing Speed: " + speed / 1000.0D);
			}
		} else if (typeOfTower == 3) {
			if (towerLevel == 1) {
				towerDmg = 5.0D;
				towerDmgString = ("Current Dmg: " + towerDmg);
			} else if (towerLevel == 2) {
				towerDmg = 7.0D;
				towerDmgString = ("Current Dmg: " + towerDmg);
			}
		} else if (typeOfTower == 4) {
			if (towerLevel == 1) {
				towerDmg = 8.0D;
				towerDmgString = ("Current Dmg: " + towerDmg);
			} else if (towerLevel == 2) {
				towerDmg = 11.0D;
				towerDmgString = ("Current Dmg: " + towerDmg);
			}
		}
	}

	public int getSellAmount() {
		return sellAmount;
	}
}
