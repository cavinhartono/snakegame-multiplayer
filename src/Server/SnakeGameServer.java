package Server;

import java.io.IOException;

import Server.service.BoardManager;
import Server.service.Server;

public class SnakeGameServer {
	public static void main(String[] args) throws IOException {

		BoardManager.getInstace().start();
		System.out.println("Server started");
		Server.start(3333);

	}

}
