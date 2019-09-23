package objects;

import utils.Position;

public class Wall extends SObject {

	public Wall(Position position) {
		super(position);
	}

	public String getName() {
		return "Parede";
	}

	public int getLevel() {
		return 2;
	}

}

