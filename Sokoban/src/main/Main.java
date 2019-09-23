package main;

import game.SokobanGame;
import gui.ImageMatrixGUI;

public class Main {

	public static void main(String[] args) {
		SokobanGame s = new SokobanGame();
		ImageMatrixGUI.getInstance().addObserver(s);
		ImageMatrixGUI.getInstance().update();
		ImageMatrixGUI.getInstance().go();
	}
	
}
