package objects;

import utils.Position;

public class Target extends SObject {

	public Target(Position position) {
		super(position);
	}

	public String getName() {
		return "Alvo";
	}

	public int getLevel() {
		return 1;
	}

}
