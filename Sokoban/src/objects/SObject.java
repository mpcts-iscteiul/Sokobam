package objects;

import gui.ImageTile;
import utils.Position;

public abstract class SObject implements ImageTile {

	protected Position position;

	public SObject(Position position) {
		this.position = position;
	}

	public Position getPosition() {
		return position;
	}

}
