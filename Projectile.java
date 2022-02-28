package project_oop;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Projectile {
	private float yVelocity = 0.0F;
	private float xVelocity = 0.0F;

	private boolean arrowNotDead = true;
	private boolean freeze = false;

	private BufferImageLoader loader = new BufferImageLoader("spritesheet.png");
	private int x;
	private int y;
	private int speed;
	private int angle;

	public Projectile(int x, int y, int speed, int typeOfProjectile, int angle, Enemy target, boolean freeze) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.angle = angle;
		this.target = target;
		this.freeze = freeze;

		if (typeOfProjectile == 1) {
			width = 8;
			height = 14;
			projectileImg = loader.grabSpriteAtASpecificCords(7, 4, width, height, 0, 0);
		} else if (typeOfProjectile == 2) {
			width = 14;
			height = 14;
			projectileImg = loader.grabSpriteAtASpecificCords(7, 5, width, height, 0, 0);
		} else if (typeOfProjectile == 3) {
			width = 5;
			height = 20;
			projectileImg = loader.grabSpriteAtASpecificCords(7, 4, width, height, 15, 0);
		} else if (typeOfProjectile == 4) {
			width = 12;
			height = 20;
			projectileImg = loader.grabSpriteAtASpecificCords(7, 4, width, height, 20, 0);
		}

		rectangle = new Rectangle(x, y, width, height);
		calculateDirection();
	}

	private void calculateDirection() {
		float xDistanceFromTarget = Math.abs(target.getX() + 16 - x);
		float yDistanceFromTarget = Math.abs(target.getY() + 16 - y);
		float totalDistanceFromTaget = xDistanceFromTarget + yDistanceFromTarget;
		float xPercentOfMovement = xDistanceFromTarget / totalDistanceFromTaget;
		xVelocity = xPercentOfMovement;
		yVelocity = (1.0F - xPercentOfMovement);

		if (target.getX() < x)
			xVelocity *= -1.0F;
		if (target.getY() < y)
			yVelocity *= -1.0F;
	}

	private int width;
	private int height;
	private Rectangle rectangle;
	private BufferedImage projectileImg;
	private Enemy target;

	public void move() {
		increaseX(xVelocity * speed);
		increaseY(yVelocity * speed);
	}

	public void draw(Graphics g) {
		AffineTransform at = AffineTransform.getTranslateInstance(rectangle.getX(), rectangle.getY());
		at.rotate(Math.toRadians(angle));
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);

		g2d.drawImage(projectileImg, at, null);
	}

	private void increaseX(float xSpeed)
  {
    Rectangle tmp4_1 = rectangle;41x = ((int)(41x + xSpeed));
  }

	private void increaseY(float ySpeed) {
    Rectangle tmp4_1 = rectangle;41y = ((int)(41y + ySpeed));
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	public void setArrowDead() {
		arrowNotDead = false;
	}

	public boolean getArrowNotDeadState() {
		return arrowNotDead;
	}
}
