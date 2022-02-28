package project_oop;

import java.awt.event.MouseEvent;

public class MouseInputs implements java.awt.event.MouseListener {
	Game game;

	public MouseInputs(Game game) {
		this.game = game;
	}

	public void mouseClicked(MouseEvent e) {
		game.mouseClicked(e);
	}

	public void mouseReleased(MouseEvent e) {
		game.mouseReleased(e);
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent e) {
		game.mousePressed(e);
	}
}
