import java.awt.Point;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

public class GameState implements Serializable {
  private static final long serialVersionUID = 1L;

  private HashMap<Integer, LinkedList<Point>> snakes;
  private Point food;
  private boolean gameOver;
  private int clientId;

  public GameState() {
    this.snakes = new HashMap<>();
    this.food = new Point(10, 10);
    this.gameOver = false;
  }

  public HashMap<Integer, LinkedList<Point>> getSnakes() {
    return snakes;
  }

  public void setSnakes(HashMap<Integer, LinkedList<Point>> snakes) {
    this.snakes = snakes;
  }

  public Point getFood() {
    return food;
  }

  public void setFood(Point food) {
    this.food = food;
  }

  public boolean isGameOver() {
    return gameOver;
  }

  public void setGameOver(boolean gameOver) {
    this.gameOver = gameOver;
  }

  public int getClientId() {
    return clientId;
  }

  public void setClientId(int clientId) {
    this.clientId = clientId;
  }

  public void update(int clientId, String input) {

    LinkedList<Point> snake = snakes.get(clientId);
    if (snake == null)
      return;

    String[] parts = input.split(":");
    if (parts[0].equals("MOVE")) {
      int direction = Integer.parseInt(parts[1]);
      Point head = snake.getFirst();
      Point newHead = switch (direction) {
        case 0 -> new Point(head.x, head.y - 1); // Tombol UP
        case 1 -> new Point(head.x + 1, head.y); // Tombol RIGHT
        case 2 -> new Point(head.x, head.y + 1); // Tombol DOWN
        case 3 -> new Point(head.x - 1, head.y); // Tombol LEFT
        default -> head;
      };
      snake.addFirst(newHead);
      snake.removeLast();
    }
  }
}
