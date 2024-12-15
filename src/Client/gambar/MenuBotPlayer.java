package Client.gambar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Client.model.SnakeSet;
import Client.util.SnakeDirection;

public class MenuBotPlayer extends Menu {
	private static MenuBotPlayer instance;
	private static MenuWidget exitButton, score;
	private static ArrayList<MenuWidget> widgets;

	private MenuBotPlayer() {
		super();
	}

	public static void init(Panel root) {
		if (instance == null) {
			instance = new MenuBotPlayer();
			widgets = new ArrayList<>();

			exitButton = new MenuString(root, "Exit", new Font("Ink Free", Font.BOLD, 30), 900, 50);
			score = new MenuString(root, "Number of snakes left: 0", new Font("Ink Free", Font.BOLD, 30), 350, 50);

			exitButton.setColor(Color.WHITE, Color.GREEN, Color.CYAN);
			score.setColor(Color.WHITE, Color.GREEN, Color.CYAN);

			widgets.add(Board.getInstance());
			widgets.add(exitButton);
			widgets.add(score);

		}
	}

	public static MenuBotPlayer getInstance() {
		if (instance == null) {
			throw new AssertionError("You have to call init first");
		}

		return instance;
	}

	@Override
	public void update() {
		if (Board.getPlayerSnake().isDead()) {
			Panel.setMenu(MenuLoseBotPlayer.getInstance());
			clear();
			return;
		}

		if (SnakeSet.countSnake() - 1 == 0) {
			Panel.setMenu(MenuWinBotPlayer.getInstance());
			clear();
			return;
		}
		score.setText("Number of snakes left: " + (SnakeSet.countSnake() - 1));
		for (MenuWidget widget : widgets) {
			widget.update();
		}

	}

	@Override
	public void draw(Graphics g) {
		for (MenuWidget widget : widgets) {
			widget.active();
			widget.draw(g);
		}

	}

	@Override
	public void clear() {
		for (MenuWidget widget : widgets) {
			widget.disable();
		}

	}

	@Override
	public void mouseAction(int button) {
		if (exitButton.isClick(button)) {
			Board.reset();
			Panel.setMenu(MenuStart.getInstance());
			clear();
		}

	}

	@Override
	public void keyAction(int keycode) {
		switch (keycode) {
			case KeyEvent.VK_LEFT, KeyEvent.VK_A:
				Board.getPlayerSnake().changeDirection(SnakeDirection.LEFT);
				break;
			case KeyEvent.VK_RIGHT, KeyEvent.VK_D:
				Board.getPlayerSnake().changeDirection(SnakeDirection.RIGHT);
				break;
			case KeyEvent.VK_UP, KeyEvent.VK_W:
				Board.getPlayerSnake().changeDirection(SnakeDirection.UP);
				break;
			case KeyEvent.VK_DOWN, KeyEvent.VK_S:
				Board.getPlayerSnake().changeDirection(SnakeDirection.DOWN);
				break;
		}

	}
}
