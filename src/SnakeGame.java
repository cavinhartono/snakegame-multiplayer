import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedList;

public class SnakeGame extends JPanel implements ActionListener {
  private static final int WIDTH = 600;
  private static final int HEIGHT = 600;
  private static final int TILE_SIZE = 20;

  private ObjectOutputStream out;
  private int clientId;
  private HashMap<Integer, LinkedList<Point>> snakes;
  private Point food;
  private boolean gameOver;

  private static final int UP = 0;
  private static final int RIGHT = 1;
  private static final int DOWN = 2;
  private static final int LEFT = 3;

  public SnakeGame(ObjectOutputStream out) {
    this.out = out;
    this.snakes = new HashMap<>();
    this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    this.setBackground(Color.BLACK);
    this.setFocusable(true);

    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        try {
          int direction = -1;
          if (e.getKeyCode() == KeyEvent.VK_UP)
            direction = UP;
          else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            direction = RIGHT;
          else if (e.getKeyCode() == KeyEvent.VK_DOWN)
            direction = DOWN;
          else if (e.getKeyCode() == KeyEvent.VK_LEFT)
            direction = LEFT;

          if (direction != -1) {
            out.writeObject("MOVE:" + direction);
            out.flush();
          }
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    });
  }

  public void updateGameState(GameState state) {
    this.snakes = state.getSnakes();
    this.food = state.getFood();
    this.gameOver = state.isGameOver();
    this.clientId = state.getClientId();
    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (gameOver) {
      g.setColor(Color.RED);
      g.setFont(new Font("Arial", Font.BOLD, 32));
      g.drawString("Game Over!", WIDTH / 4, HEIGHT / 2);
      return;
    }

    for (int id : snakes.keySet()) {
      g.setColor(id == clientId ? Color.GREEN : Color.BLUE);
      for (Point p : snakes.get(id)) {
        g.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
      }
    }

    if (food != null) {
      g.setColor(Color.RED);
      g.fillRect(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }
}
