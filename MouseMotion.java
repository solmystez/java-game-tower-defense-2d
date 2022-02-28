package project_oop;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MouseMotion extends MouseMotionAdapter {
	Game game;

	public MouseMotion(Game game) {
		this.game = game;
	}

	public void mouseMoved(MouseEvent e) {
		game.mouseMoved(e);
	}

	public void mouseReleased(MouseEvent e) {
		game.mouseReleased(e);
	}

	public void mouseDragged(MouseEvent e) {
		game.mouseDragged(e);
	}
}
