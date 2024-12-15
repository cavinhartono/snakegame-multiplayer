package Client.gambar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

public class MenuWinBotPlayer extends Menu {
	private static MenuWinBotPlayer instance;
	private static MenuWidget title, playAgain, exit;
	private static ArrayList<MenuWidget> widgets;

	private MenuWinBotPlayer() {

		super();
	}

	public static void init(Panel root) {
		if (instance == null) {
			instance = new MenuWinBotPlayer();
			widgets = new ArrayList<>();
			Board.init(root);

			title = new MenuString(root, "You Win !", new Font("Ink Free", Font.BOLD, 75), MenuString.Side.CENTER);
			playAgain = new MenuString(root, "Play Again", new Font("Ink Free", Font.BOLD, 30), MenuString.Side.CENTER,
					500);
			exit = new MenuString(root, "Exit", new Font("Ink Free", Font.BOLD, 30), MenuString.Side.CENTER, 550);

			playAgain.setColor(Color.WHITE, Color.GREEN, Color.CYAN);
			exit.setColor(Color.WHITE, Color.GREEN, Color.CYAN);

			widgets.add(title);
			widgets.add(playAgain);
			widgets.add(exit);

		}
	}

	public static MenuWinBotPlayer getInstance() {
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
		if (playAgain.isClick(button)) {
			Board.reset();
			Board.setSizeBoard(MenuSetting.getWidgets().get(4).getEnteredNumber(),
					MenuSetting.getWidgets().get(5).getEnteredNumber());
			Panel.setMenu(MenuBotPlayerOption.getInstance());
			clear();
		}

		if (exit.isClick(button)) {
			Board.reset();
			Panel.setMenu(MenuStart.getInstance());
			clear();
		}

	}

	@Override
	public void keyAction(int keycode) {
		// TODO Auto-generated method stub

	}
}
