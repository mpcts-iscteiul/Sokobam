package objects;

import utils.Position;

public class Batery extends SObject {

	public Batery(Position position) {
		super(position);
	}

	public String getName() {
		return "Bateria";
	}

	public int getLevel() {
		return 1;
	}

}

