package game;

import gui.ImageMatrixGUI;
import objects.ActiveObject;
import objects.SObject;
import utils.Position;

public class Player extends SObject implements ActiveObject {

	private String imageName;

	public Player(Position position) {
		super(position);
		imageName = "Empilhadora_D";
	}

	public String getName() {
		return imageName;
	}

	public int getLevel() {
		return 10;
	}

	@Override
	public void moveUP() {
		position = new Position(position.getX(), position.getY() - 1);
		ImageMatrixGUI.getInstance().update();
		imageName = "Empilhadora_U";
	}

	@Override
	public void moveDOWN() {
		position = new Position(position.getX(), position.getY() + 1);
		ImageMatrixGUI.getInstance().update();
		imageName = "Empilhadora_D";
	}

	@Override
	public void moveLEFT() {
		position = new Position(position.getX() - 1, position.getY());
		ImageMatrixGUI.getInstance().update();
		imageName = "Empilhadora_L";
	}

	@Override
	public void moveRIGHT() {
		position = new Position(position.getX() + 1, position.getY());
		ImageMatrixGUI.getInstance().update();
		imageName = "Empilhadora_R";
	}
}

