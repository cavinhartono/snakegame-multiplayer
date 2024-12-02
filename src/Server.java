import java.net.*;
import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
  private static final int PORT = 5000;
  private static ConcurrentHashMap<Integer, ObjectOutputStream> clients = new ConcurrentHashMap<>();
  private static GameState gameState = new GameState();

  public static void main(String[] args) throws IOException {
    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      System.out.println("Server started on port " + PORT);

      int clientId = 0;
      while (true) {
        Socket clientSocket = serverSocket.accept();
        System.out.println("New client connected: " + clientSocket.getInetAddress());
        clientId++;
        new Thread(new ClientHandler(clientSocket, clientId)).start();
      }
    }
  }

  static class ClientHandler implements Runnable {
    private Socket socket;
    private int clientId;

    public ClientHandler(Socket socket, int clientId) {
      this.socket = socket;
      this.clientId = clientId;
    }

    @Override
    public void run() {
      try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
          ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

        clients.put(clientId, out);

        while (true) {
          String input = (String) in.readObject(); // Handle input (e.g., directions)
          synchronized (gameState) {
            gameState.update(clientId, input);
            broadcastGameState();
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        clients.remove(clientId);
      }
    }
  }

  private static void broadcastGameState() {
    synchronized (gameState) {
      for (ObjectOutputStream out : clients.values()) {
        try {
          out.writeObject(gameState);
          out.flush();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
