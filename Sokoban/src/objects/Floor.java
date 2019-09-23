package objects;

import utils.Position;

public class Floor extends SObject {

	public Floor(Position position) {
		super(position);
	}

	public String getName() {
		return "Chao";
	}

	public int getLevel() {
		return 0;
	}

}
