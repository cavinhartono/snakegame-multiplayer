import java.net.*;
import java.io.*;
import javax.swing.*;

public class Client {
  private static final String IP_ADDRESS = "192.168.XX.XX";
  private static final int PORT = 5000;

  public static void main(String[] args) {
    try (Socket socket = new Socket(IP_ADDRESS, PORT);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
      JFrame frame = new JFrame("Oray Game Bersama");
      SnakeGame game = new SnakeGame(out);
      frame.add(game);
      frame.pack();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);

      while (true) {
        GameState gameState = (GameState) in.readObject();
        game.updateGameState(gameState);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
