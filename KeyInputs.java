package project_oop;

import java.awt.event.KeyEvent;

public class KeyInputs extends java.awt.event.KeyAdapter {
	Game game;

	public KeyInputs(Game game) {
		this.game = game;
	}

	public void keyPressed(KeyEvent e) {
		game.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		game.keyReleased(e);
	}
}
