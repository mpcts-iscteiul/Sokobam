package utils;

import java.awt.event.KeyEvent;

/**
 * @author POO2016
 * 
 *         Cardinal directions
 *
 */
public enum Direction {
	LEFT(new Vector2D(-1, 0)), UP(new Vector2D(0, -1)), RIGHT(new Vector2D(1, 0)), DOWN(new Vector2D(0, 1));

	private Vector2D vector;

	Direction(Vector2D vector) {
		this.vector = vector;
	}

	public Vector2D asVector() {
		return vector;
	}

	public static Direction directionFor(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_DOWN:
			return DOWN;
		case KeyEvent.VK_UP:
			return UP;
		case KeyEvent.VK_LEFT:
			return LEFT;
		case KeyEvent.VK_RIGHT:
			return RIGHT;
		}
		throw new IllegalArgumentException();
	}

	public static boolean isDirection(int lastKeyPressed) {
		return lastKeyPressed >= KeyEvent.VK_LEFT && lastKeyPressed <= KeyEvent.VK_DOWN;
	}

	public Direction inverse() {
		switch (this) {
		case UP:
			return DOWN;
		case DOWN:
			return UP;
		case RIGHT:
			return LEFT;
		case LEFT:
			return RIGHT;
		}
		return null;
	}
}
