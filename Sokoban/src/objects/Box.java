package objects;

import gui.ImageMatrixGUI;
import utils.Position;

public class Box extends SObject implements ActiveObject {

	private String imageName;

	public Box(Position position) {
		super(position);
		this.imageName = "Caixote";
	}

	public String getName() {
		return imageName;
	}

	public void setPosition(int x, int y) {
		this.position = new Position(x, y);
	}

	public void setName(boolean test) {
		if (test == true) {
			this.imageName = "CaixoteAlvo";
			ImageMatrixGUI.getInstance().update();
		} else {
			this.imageName = "Caixote";
			ImageMatrixGUI.getInstance().update();
		}
	}

	public int getLevel() {
		return 2;
	}
	
	@Override
	public void moveUP() {
		position = new Position(position.getX(), position.getY() - 1);
		ImageMatrixGUI.getInstance().update();
	}

	@Override
	public void moveDOWN() {
		position = new Position(position.getX(), position.getY() + 1);
		ImageMatrixGUI.getInstance().update();
	}

	@Override
	public void moveLEFT() {
		position = new Position(position.getX() - 1, position.getY());
		ImageMatrixGUI.getInstance().update();
	}

	@Override
	public void moveRIGHT() {
		position = new Position(position.getX() + 1, position.getY());
		ImageMatrixGUI.getInstance().update();
	}

}
