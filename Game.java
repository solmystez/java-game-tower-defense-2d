package project_oop;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

import project_oop.main.graphics.MainRender;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	BufferImageLoader loader = new BufferImageLoader("spritesheet.png");
	List<Enemy> enemies = new ArrayList();

	public static final int WIDTH = 640;

	public static final int HEIGHT = 576;
	public static final String TITLE = "TD Game";
	public static final double TARGET_UPS = 60.0D;
	private int counter = 0;
	private Thread thread;
	private boolean running = false;
	private boolean started = false;
	private boolean gameWon = false;
	private boolean waveCountDown = false;

	private JFrame f;
	private MainRender render;
	private HUD hud;
	private Mover mover;
	private WaveManager waveManager;
	private TowerHandler towerHandler;
	private MouseMotion mouseMotion;
	private MouseInputs mouseInputs;
	private ButtonsClicked buttonsClicked;
	private ButtonsHoveredOver buttonsHoveredOver;
	private TempTowerAtMouseLoc tempTowerAtMouseLoc;
	private boolean gameOver = false;
	BufferedImage enemySprite;

	public Game() {
		f = new JFrame();
		Dimension dimension = new Dimension(640, 576);
		setPreferredSize(dimension);
		setMaximumSize(dimension);
		setMinimumSize(dimension);

		hud = new HUD(0, 480, 640, 96, this);
		mover = new Mover(this, hud);
		tempTowerAtMouseLoc = new TempTowerAtMouseLoc();
		render = new MainRender(hud, tempTowerAtMouseLoc);
		waveManager = new WaveManager(mover, render, hud);
		enemySprite = loader.grabSprite(1, 2);
		mover.receiveMap(render.getMap());
		mover.getRenderObj(render);
		towerHandler = new TowerHandler(mover, render, this);
		buttonsClicked = new ButtonsClicked(mover, render, waveManager, hud, towerHandler, this);
		buttonsHoveredOver = new ButtonsHoveredOver();

		mouseMotion = new MouseMotion(this);
		mouseInputs = new MouseInputs(this);
		buttonsHoveredOver.getHud(render.getHud());
		buttonsClicked.getHud(render.getHud());
	}

	private synchronized void start() {
		if (running) {
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	private synchronized void stop() {
		if (!running) {
			return;
		}
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}

	public void run() {
		initialize();

		long last = System.nanoTime();
		double ns = 1.6666666666666666E7D;
		double delta = 0.0D;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();

		while (running) {
			long now = System.nanoTime();
			delta += (now - last) / ns;
			last = now;

			if (delta >= 1.0D) {
				update();
				updates++;
				delta -= 1.0D;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000L) {
				timer += 1000L;
				f.setTitle("TD Game | UPS: " + updates + "  FPS: " + frames);
				updates = 0;
				frames = 0;
			}
		}

		stop();
	}

	public void update() {
		if (!gameOver) {
			mover.move();
		}
		if ((waveCountDown) && (!gameOver)) {
			counter += 1;
			if (counter >= 6) {
				hud.checkTimer();
				counter = 0;
			}
		}
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();

		render.render(g);

		g.dispose();
		bs.show();
	}

	public void initialize() {
		addKeyListener(new KeyInputs(this));
		addMouseMotionListener(mouseMotion);
		addMouseListener(mouseInputs);
	}

	public void gameOver() {
		gameOver = true;
		render.setGameOver(true);
	}

	public static void main(String[] args) {
		Game game = new Game();

		f.setResizable(false);
		f.setTitle("TD Game");
		f.setVisible(true);
		f.add(game);
		f.pack();
		f.setDefaultCloseOperation(3);
		f.setLocationRelativeTo(null);

		game.start();
	}

	public void keyPressed(KeyEvent e) {
		if (((gameOver) || (!started) || (gameWon)) && (e.getKeyCode() == 10)) {
			if ((gameOver) || (gameWon)) {
				hud.setCountDownStarted(false);
				waveCountDown = false;

				if (gameWon) {
					render.setGameWon(false);
					gameWon = false;
					mover.setGameWon(false);
				}

				towerHandler.setTowerNumber(1);
				waveManager.setWaveNr(0);
				hud.addGold(-hud.getAmountOfGold() + 350);
				hud.setLife(hud.getStartLife());
				hud.setSizeOfWave(0);

				for (int i = towerHandler.getTowers().size() - 1; i > -1; i--) {
					Tower t = (Tower) towerHandler.getTowers().get(i);
					towerHandler.removeTowerHere(t.getX(), t.getY());
					towerHandler.getTowers().remove(i);
					mover.getTowerList().remove(i);
					render.getTowerList().remove(i);
				}

				for (int i = render.getEnemies().size() - 1; i > -1; i--) {
					render.getEnemies().remove(i);
					mover.getEnemyList().remove(i);
				}

				waveManager.resetWavesAndTowers();

				for (int i = mover.getEnemiesTemp().size() - 1; i > -1; i--) {
					mover.getEnemiesTemp().remove(i);
				}

				for (int i = render.getEnemiesTemp().size() - 1; i > -1; i--) {
					render.getEnemiesTemp().remove(i);
				}

				render.setGameOver(false);
				mover.setGameOver(false);
				mover.resetJagVetInte();
				hud.setCurrentWave(0);

				gameOver = false;
			} else if (!started) {
				mover.setStartMenu(false);
				render.setStartMenu(false);
				buttonsClicked.getSoundClass().play();
				buttonsClicked.setGameStartedToTrue();
			}
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
		int xx = e.getX();
		int yy = e.getY();
		if (render.getTowerClicked()) {
			tempTowerAtMouseLoc.setX(xx);
			tempTowerAtMouseLoc.setY(yy);
		}

		buttonsHoveredOver.testIfMouseIsOver(xx, yy);
	}

	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		buttonsHoveredOver.testIfMouseIsOver(x, y);
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
		if ((!gameOver) && (!gameWon)) {
			buttonsClicked.testIfAnyButtonIsPressed(e.getX(), e.getY());
		}

		if ((render.getTowerClicked()) && (e.getButton() == 1) && (e.getY() < 480)) {
			int type = render.getTypeOfTower();
			if ((type == 1) && (hud.getAmountOfGold() >= 150)) {
				if (!towerHandler.checkIfTowerCanBePlacedHere(tempTowerAtMouseLoc.getX(), tempTowerAtMouseLoc.getY())) {
					towerHandler.tower(tempTowerAtMouseLoc.getX(), tempTowerAtMouseLoc.getY(), type);
					hud.reduceGold(150);
				} else {
					System.out.println("Tower already exists at: " + tempTowerAtMouseLoc.getX() / 32 + " , "
							+ tempTowerAtMouseLoc.getY() / 32 + ", or its not a grassTile!");
				}
			} else if ((type == 2) && (hud.getAmountOfGold() >= 270)) {
				if (!towerHandler.checkIfTowerCanBePlacedHere(tempTowerAtMouseLoc.getX(), tempTowerAtMouseLoc.getY())) {
					towerHandler.tower(tempTowerAtMouseLoc.getX(), tempTowerAtMouseLoc.getY(), type);
					hud.reduceGold(270);
				} else {
					System.out.println("Tower already exists at: " + tempTowerAtMouseLoc.getX() / 32 + " , "
							+ tempTowerAtMouseLoc.getY() / 32 + ", or its not a grassTile!");
				}
			} else if ((type == 3) && (hud.getAmountOfGold() >= 310)) {
				if (!towerHandler.checkIfTowerCanBePlacedHere(tempTowerAtMouseLoc.getX(), tempTowerAtMouseLoc.getY())) {
					towerHandler.tower(tempTowerAtMouseLoc.getX(), tempTowerAtMouseLoc.getY(), type);
					hud.reduceGold(310);
				} else {
					System.out.println("Tower already exists at: " + tempTowerAtMouseLoc.getX() / 32 + " , "
							+ tempTowerAtMouseLoc.getY() / 32 + ", or its not a grassTile!");
				}
			} else if ((type == 4) && (hud.getAmountOfGold() >= 400)) {
				if (!towerHandler.checkIfTowerCanBePlacedHere(tempTowerAtMouseLoc.getX(), tempTowerAtMouseLoc.getY())) {
					towerHandler.tower(tempTowerAtMouseLoc.getX(), tempTowerAtMouseLoc.getY(), type);
					hud.reduceGold(400);
				} else {
					System.out.println("Tower already exists at: " + tempTowerAtMouseLoc.getX() / 32 + " , "
							+ tempTowerAtMouseLoc.getY() / 32 + ", or its not a grassTile!");
				}
			} else {
				System.out.println("not enough gold for tower type: " + type);
			}
		} else if ((render.getTowerClicked()) && (e.getButton() != 1)) {

			render.changeTowerClicked(2);

			tempTowerAtMouseLoc.setX(800);
			tempTowerAtMouseLoc.setY(800);
		}
	}

	public void mousePressed(MouseEvent e) {
		buttonsClicked.setPressedX(e.getX());
		buttonsClicked.setPressedY(e.getY());
	}

	public WaveManager getWaveManager() {
		return waveManager;
	}

	public void gameIsWon() {
		render.setGameWon(true);
		gameWon = true;
		mover.setGameWon(true);
	}

	public ButtonsHoveredOver getButtonsHoveredOver() {
		return buttonsHoveredOver;
	}

	public void setWaveCountDown(boolean waveCountDown) {
		this.waveCountDown = waveCountDown;
	}
}
