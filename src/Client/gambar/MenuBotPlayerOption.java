package Client.gambar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

public class MenuBotPlayerOption extends Menu {
	private static MenuBotPlayerOption instance;
	private static MenuWidget numberBot, go, deepBrainSnake;
	private static ArrayList<MenuWidget> widgets;

	private MenuBotPlayerOption() {
		super();
	}

	public static void init(Panel root) {
		if (instance == null) {
			instance = new MenuBotPlayerOption();
			widgets = new ArrayList<>();
			numberBot = new MenuEntry(root, "Enter number of bots: ", "100", new Font("Ink Free", Font.BOLD, 20),
					300, 350);

			deepBrainSnake = new MenuEntry(root, "Deep A* Snake: ", "10", new Font("Ink Free", Font.BOLD, 20), 300,
					400);

			go = new MenuString(root, "Play !", new Font("Ink Free", Font.BOLD, 30), MenuString.Side.CENTER, 450);

			numberBot.setColor(Color.YELLOW, Color.WHITE, Color.CYAN, Color.BLUE);
			deepBrainSnake.setColor(Color.YELLOW, Color.WHITE, Color.CYAN, Color.BLUE);
			go.setColor(Color.WHITE, Color.GREEN, Color.CYAN);

			widgets.add(numberBot);
			widgets.add(deepBrainSnake);
			widgets.add(go);
		}
	}

	public static MenuBotPlayerOption getInstance() {
		if (instance == null) {
			throw new AssertionError("You have to call init first");
		}

		return instance;
	}

	@Override
	public void update() {
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
		if (numberBot.isClick(button)) {
		}

		if (deepBrainSnake.isClick(button)) {
		}

		if (go.isClick(button)) {
			Board.addBot(numberBot.getEnteredNumber(), deepBrainSnake.getEnteredNumber());
			Panel.setMenu(MenuBotPlayer.getInstance());
			clear();
		}

	}

	@Override
	public void keyAction(int keycode) {
		if (numberBot.isClicked()) {
			numberBot.getNumber(keycode);
		}

		if (deepBrainSnake.isClicked()) {
			deepBrainSnake.getNumber(keycode);
		}

	}
}
