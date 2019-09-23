package utils;

public class Vector2D {
	private int x;
	private int y;
	
	public Vector2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	public Vector2D plus(Vector2D v) {
		return new Vector2D(getX() + v.getX(), getY() + v.getY());
	}

	public Vector2D minus(Vector2D v) {
		return new Vector2D(getX() - v.getX(), getY() - v.getY());
	}

	@Override
	public String toString() {
		return "<" + x + ", " + y + ">";
	}

}
