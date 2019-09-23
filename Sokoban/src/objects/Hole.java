package objects;

import utils.Position;

public class Hole extends SObject {

	public Hole(Position position) {
		super(position);
	}

	public String getName() {
		return "Buraco";
	}

	public int getLevel() {
		return 3;
	}
}
