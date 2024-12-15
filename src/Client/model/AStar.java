package Client.model;

import java.util.*;

import Client.util.GameRandom;
import Client.util.TileType;

public class AStar {
	private HashSet<TileAStar> openSet, closedSet;
	private ArrayList<TileAStar> path;

	private TileAStar current;
	private TileAStar[][] map;

	private Tile head, end;
	private Snake snake;
	private int deep;
	private int xResult, yResult;

	AStar(Snake snake, int deep) {
		this.snake = snake;
		this.head = snake.getHead();
		this.openSet = new HashSet<TileAStar>();
		this.closedSet = new HashSet<TileAStar>();
		this.path = new ArrayList<TileAStar>();
		this.map = new TileAStar[TileGrid.getColumns()][TileGrid.getRows()];
		this.deep = deep;
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[0].length; y++) {
				map[x][y] = new TileAStar(TileGrid.getTile(x, y));
			}
		}

		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[0].length; y++) {
				map[x][y].addNeighbors(map);
			}
		}

		this.end = null;

	}

	public void getPath() {
		this.findApple();
		this.head = this.snake.getHead();
		openSet.clear();
		closedSet.clear();
		path.clear();
		openSet.add(map[head.getIndexX()][head.getIndexY()]);

		for (int i = 0; i < deep; i++) {
			if (openSet.size() > 0) {
				current = openSet.iterator().next();
				for (TileAStar element : openSet)
					if (element.f < current.f)
						current = element;
			}

			if (current.tile == end) {
				break;
			}

			openSet.remove(current);
			closedSet.add(current);

			for (TileAStar neighbor : current.neighbors) {
				if (!closedSet.contains(neighbor) && neighbor.tile.getType() != TileType.BODY) {
					double tempG = current.g + 1;
					if (openSet.contains(neighbor)) {
						if (tempG < neighbor.g)
							neighbor.g = tempG;
					}

					else {
						neighbor.g = tempG;
						openSet.add(neighbor);
					}

					neighbor.h = heuristic(neighbor);
					neighbor.f = neighbor.g + neighbor.h;
					neighbor.previous = current;
				}
			}

			path.clear();
			while (current.previous != null) {
				path.add(current);
				current = current.previous;
			}
		}

		if (path.size() == 0) {
			xResult = current.i;
			yResult = current.j;
		} else {
			xResult = path.get(path.size() - 1).i;
			yResult = path.get(path.size() - 1).j;
		}

		resetTile();

	}

	private void findApple() {
		if (this.end == null) {
			this.end = GameRandom.getRandomElement(Apple.getApples());
			return;
		}

		if (!Apple.getApples().contains(end)) {
			end = GameRandom.getRandomElement(Apple.getApples());
		}
	}

	public Tile getResult() {
		return TileGrid.getTile(xResult, yResult);
	}

	private double heuristic(TileAStar neighbor) {
		double d = Math.pow(neighbor.i - end.getIndexX(), 2) + Math.pow(neighbor.j - end.getIndexY(), 2);
		return Math.pow(d, 2);
	}

	private void resetTile() {
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[0].length; y++) {
				map[x][y].reset();
			}
		}
	}

}
