package game;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import gui.ImageMatrixGUI;
import gui.ImageTile;
import objects.Batery;
import objects.Box;
import objects.Floor;
import objects.Hole;
import objects.Target;
import objects.Wall;
import utils.Position;

public class SokobanGame implements Observer {

	private Player player;
	private Box auxcaixa;
	private ArrayList<ImageTile> tiles = new ArrayList<>();
	private int level = 1, moves = 0, energy = 100, highscore;

	public SokobanGame() {
		tiles.addAll(buildSampleLevel());
		updateHighScore();
		statusBar();
		ImageMatrixGUI.getInstance().addImages(tiles);
	}

	/*----------------------------------------------------------------------Construtor Cenario---------------------------------*/
	private ArrayList<ImageTile> buildSampleLevel() {
		ImageMatrixGUI.getInstance().clearImages();

		ArrayList<ImageTile> sampleLevelTiles = new ArrayList<ImageTile>();

		Scanner map;
		try {
			map = new Scanner(new File("levels/level" + level + ".txt"));
			for (int y = 0; y != 10; y++) {
				String line = map.nextLine();
				for (int x = 0; x != 10; x++) {
					sampleLevelTiles.add(new Floor(new Position(x, y)));
					switch (line.charAt(x)) {
					case '#':
						sampleLevelTiles.add(new Wall(new Position(x, y)));
						break;
					case 'X':
						sampleLevelTiles.add(new Target(new Position(x, y)));
						break;
					case 'C':
						sampleLevelTiles.add(new Box(new Position(x, y)));
						break;
					case 'O':
						sampleLevelTiles.add(new Hole(new Position(x, y)));
						break;
					case 'b':
						sampleLevelTiles.add(new Batery(new Position(x, y)));
						break;
					case 'Z':
						sampleLevelTiles.add(new Target(new Position(x, y)));
						auxcaixa = new Box(new Position(x, y));
						auxcaixa.setName(true);
						sampleLevelTiles.add(auxcaixa);
						break;
					case 'E':
						player = new Player(new Position(x, y));
						tiles.add(this.player);
						break;
					}
				}
			}
			map.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return sampleLevelTiles;
	}

	/*----------------------------------------------------------------------Atualiza Barra de Status---------------------------*/
	private void statusBar() {
		ImageMatrixGUI.getInstance().setStatusMessage(
				" Level: " + level + "  Moves: " + moves + " Energy: " + energy + "     HighScore: " + highscore);
	}

	/*----------------------------------------------------------------------Verifica se no (x,y) existe o Arg dado-------------*/
	private boolean isThisNext(int x, int y, String arg) {
		boolean test = false;

		for (ImageTile aux : tiles) {
			if (aux.getName() == arg)
				if (aux.getPosition().getX() == x && aux.getPosition().getY() == y) {
					test = true;
					if (arg == "Caixote" || arg == "CaixoteAlvo")
						auxcaixa = (Box) aux;
				}
		}
		return test;
	}

	/*----------------------------------------------------------------------Verifica se o (x,y) esta numa posição Alvo---------*/
	private boolean onAlvo(int x, int y) {
		boolean test = false;

		for (ImageTile aux : tiles) {
			if (aux.getName() == "Alvo")
				if (aux.getPosition().getX() == x && aux.getPosition().getY() == y)
					test = true;
		}
		return test;
	}

	/*----------------------------------------------------------------------Elimina Caixas Dentro Dos Buracos------------------*/
	private void limparBuracos() {
		for (ImageTile aux : tiles) {
			if (aux.getName() == "Buraco")
				if (isThisNext(aux.getPosition().getX(), aux.getPosition().getY(), "Caixote"))
					auxcaixa.setPosition(20, 20);
		}
	}

	/*----------------------------------------------------------------------Verifica Se As Caixas Todas Estao Sobre Os Alvos---*/
	private boolean levelConcluido() {
		int countalvo = 0, countcaixa = 0;

		for (ImageTile aux : tiles) {
			if (aux.getName() == "CaixoteAlvo")
				countcaixa++;
			if (aux.getName() == "Alvo")
				countalvo++;
		}
		return (countalvo == countcaixa);
	}

	/*----------------------------------------------------------------------Mudar Nivel----------------------------------------*/
	private void nextLevel() {
		level++;
		tiles.clear();
		moves = 0;
		energy = 100;
		updateHighScore();
		statusBar();
		tiles.addAll(buildSampleLevel());
		ImageMatrixGUI.getInstance().addImages(tiles);
		ImageMatrixGUI.getInstance().update();
	}

	/*----------------------------------------------------------------------Atualizar Recordes---------------------------------*/
	private void updateHighScore() {
		try {
			Scanner read = new Scanner(new File("score\\highscoreaux.txt"));
			PrintWriter write = new PrintWriter(new File("score\\highscore.txt"));
			for (int l = 1; l <= 7; l++) {
				String line = read.nextLine();
				write.println(line);
			}
			write.close();
			read.close();

			read = new Scanner(new File("score\\highscore.txt"));
			for (int y = 1; y <= level; y++) {
				String line = read.nextLine();
				String[] ln = line.split(":");
				highscore = Integer.parseInt(ln[1]);
			}
			read.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/*----------------------------------------------------------------------Inserir Novo Recorde-------------------------------*/
	private void newHighScore() {
		try {
			Scanner read = new Scanner(new File("score\\highscore.txt"));
			PrintWriter write = new PrintWriter(new File("score\\highscoreaux.txt"));
			for (int l = 1; l <= 7; l++) {
				if (l == level) {
					write.println(level + ":" + moves);
					read.nextLine();
				} else {
					String line = read.nextLine();
					write.println(line);
				}
			}
			read.close();
			write.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/*----------------------------------------------------------------------Movimentar Empilhadora-----------------------------*/
	@Override
	public void update(Observable arg0, Object arg1) {
		int lastKeyPressed = (Integer) arg1;
		// System.out.println("Key pressed " + lastKeyPressed);
		/*
		 * VK_UP, VK_DOWN, VK_LEFT, VK_RIGHT (Move) VK_R(Restart) VK_N(New Game)
		 */
		if (lastKeyPressed == KeyEvent.VK_1 || lastKeyPressed == KeyEvent.VK_2 || lastKeyPressed == KeyEvent.VK_3
				|| lastKeyPressed == KeyEvent.VK_4 || lastKeyPressed == KeyEvent.VK_5 || lastKeyPressed == KeyEvent.VK_6
				|| lastKeyPressed == KeyEvent.VK_7) {
			level = lastKeyPressed - 49;
			nextLevel();
		}

		switch (lastKeyPressed) {
		case KeyEvent.VK_UP:
			if (isThisNext(player.getPosition().getX(), player.getPosition().getY() - 1, "Bateria"))
				energy = 101;
			if (isThisNext(player.getPosition().getX(), player.getPosition().getY() - 1, "Parede"))
				break;
			if (isThisNext(player.getPosition().getX(), player.getPosition().getY() - 1, "Buraco")) {
				level--;
				moves = 999;
				nextLevel();
				break;
			}
			if (isThisNext(player.getPosition().getX(), player.getPosition().getY() - 1, "Caixote")
					|| isThisNext(player.getPosition().getX(), player.getPosition().getY() - 1, "CaixoteAlvo")) {
				if (isThisNext(player.getPosition().getX(), player.getPosition().getY() - 2, "Parede")
						|| isThisNext(player.getPosition().getX(), player.getPosition().getY() - 2, "Caixote")
						|| isThisNext(player.getPosition().getX(), player.getPosition().getY() - 2, "CaixoteAlvo"))
					break;
				auxcaixa.moveUP();
				auxcaixa.setName(onAlvo(auxcaixa.getPosition().getX(), auxcaixa.getPosition().getY()));
			}
			moves++;
			energy -= 2;
			player.moveUP();
			break;

		case KeyEvent.VK_DOWN:
			if (isThisNext(player.getPosition().getX(), player.getPosition().getY() + 1, "Bateria"))
				energy = 101;
			if (isThisNext(player.getPosition().getX(), player.getPosition().getY() + 1, "Parede"))
				break;
			if (isThisNext(player.getPosition().getX(), player.getPosition().getY() + 1, "Buraco")) {
				level--;
				moves = 999;
				nextLevel();
				break;
			}
			if (isThisNext(player.getPosition().getX(), player.getPosition().getY() + 1, "Caixote")
					|| isThisNext(player.getPosition().getX(), player.getPosition().getY() + 1, "CaixoteAlvo")) {
				if (isThisNext(player.getPosition().getX(), player.getPosition().getY() + 2, "Parede")
						|| isThisNext(player.getPosition().getX(), player.getPosition().getY() + 2, "Caixote")
						|| isThisNext(player.getPosition().getX(), player.getPosition().getY() + 2, "CaixoteAlvo"))
					break;
				auxcaixa.moveDOWN();
				auxcaixa.setName(onAlvo(auxcaixa.getPosition().getX(), auxcaixa.getPosition().getY()));
			}
			moves++;
			energy -= 2;
			player.moveDOWN();
			break;

		case KeyEvent.VK_LEFT:
			if (isThisNext(player.getPosition().getX() - 1, player.getPosition().getY(), "Bateria"))
				energy = 101;
			if (isThisNext(player.getPosition().getX() - 1, player.getPosition().getY(), "Parede"))
				break;
			if (isThisNext(player.getPosition().getX() - 1, player.getPosition().getY(), "Buraco")) {
				level--;
				moves = 999;
				nextLevel();
				break;
			}
			if (isThisNext(player.getPosition().getX() - 1, player.getPosition().getY(), "Caixote")
					|| isThisNext(player.getPosition().getX() - 1, player.getPosition().getY(), "CaixoteAlvo")) {
				if (isThisNext(player.getPosition().getX() - 2, player.getPosition().getY(), "Parede")
						|| isThisNext(player.getPosition().getX() - 2, player.getPosition().getY(), "Caixote")
						|| isThisNext(player.getPosition().getX() - 2, player.getPosition().getY(), "CaixoteAlvo"))
					break;
				auxcaixa.moveLEFT();
				auxcaixa.setName(onAlvo(auxcaixa.getPosition().getX(), auxcaixa.getPosition().getY()));
			}
			moves++;
			energy -= 2;
			player.moveLEFT();
			break;

		case KeyEvent.VK_RIGHT:
			if (isThisNext(player.getPosition().getX() + 1, player.getPosition().getY(), "Bateria"))
				energy = 101;
			if (isThisNext(player.getPosition().getX() + 1, player.getPosition().getY(), "Parede"))
				break;
			if (isThisNext(player.getPosition().getX() + 1, player.getPosition().getY(), "Buraco")) {
				level--;
				moves = 999;
				nextLevel();
				break;
			}
			if (isThisNext(player.getPosition().getX() + 1, player.getPosition().getY(), "Caixote")
					|| isThisNext(player.getPosition().getX() + 1, player.getPosition().getY(), "CaixoteAlvo")) {
				if (isThisNext(player.getPosition().getX() + 2, player.getPosition().getY(), "Parede")
						|| isThisNext(player.getPosition().getX() + 2, player.getPosition().getY(), "Caixote")
						|| isThisNext(player.getPosition().getX() + 2, player.getPosition().getY(), "CaixoteAlvo"))
					break;
				auxcaixa.moveRIGHT();
				auxcaixa.setName(onAlvo(auxcaixa.getPosition().getX(), auxcaixa.getPosition().getY()));
			}
			moves++;
			energy -= 2;
			player.moveRIGHT();
			break;

		case KeyEvent.VK_R:
			level--;
			moves = 99;
			nextLevel();
			break;

		case KeyEvent.VK_N:
			level = 0;
			moves = 99;
			nextLevel();
			break;
			
		}

		if (energy <= 0) {
			level--;
			moves = 99;
			nextLevel();
		}
		if (levelConcluido()) {
			if (moves < highscore)
				newHighScore();
			nextLevel();
		}
		statusBar();
		limparBuracos();
	}
}

