package Server.model;

import java.awt.Color;
import java.util.LinkedList;

import Client.util.SnakeDirection;
import Client.util.TileType;

public class Snake {
	private LinkedList<Tile> body;
	private Tile head, nextHead;
	private TileGrid grid;
	private Apple apples;
	private Color colorBody, colorHead;
	private SnakeDirection direction;
	private boolean dead;

	public Snake(Tile head, TileGrid grid, Apple apple, Color colorHead, Color colorBody) {
		this.body = new LinkedList<Tile>();
		this.head = head;
		this.grid = grid;
		this.apples = apple;
		this.colorBody = colorBody;
		this.colorHead = colorHead;
		this.direction = SnakeDirection.DOWN;
		this.dead = false;

	}

	public void changeDirection(SnakeDirection direction) {
		if (this.isDead())
			return;

		if (this.direction == SnakeDirection.DOWN && direction == SnakeDirection.UP) {

		}

		else if (this.direction == SnakeDirection.UP && direction == SnakeDirection.DOWN) {

		}

		else if (this.direction == SnakeDirection.LEFT && direction == SnakeDirection.RIGHT) {

		}

		else if (this.direction == SnakeDirection.RIGHT && direction == SnakeDirection.LEFT) {

		} else {
			this.direction = direction;
		}

	}

	public void calculateMoveStep() {
		if (this.isDead())
			return;

		int[] curHead = this.head.getIndex();

		switch (this.direction) {
			case UP:
				if (curHead[1] == 0) {
					this.nextHead = grid.getTile(curHead[0], grid.getRows() - 1);
					break;
				}
				this.nextHead = grid.getTile(curHead[0], curHead[1] - 1);
				break;
			case DOWN:
				if (curHead[1] == grid.getRows() - 1) {
					this.nextHead = grid.getTile(curHead[0], 0);
					break;
				}
				this.nextHead = grid.getTile(curHead[0], curHead[1] + 1);
				break;
			case LEFT:
				if (curHead[0] == 0) {
					this.nextHead = grid.getTile(grid.getColumns() - 1, curHead[1]);
					break;
				}
				this.nextHead = grid.getTile(curHead[0] - 1, curHead[1]);
				break;
			case RIGHT:
				if (curHead[0] == grid.getColumns() - 1) {
					this.nextHead = grid.getTile(0, curHead[1]);
					break;
				}
				this.nextHead = grid.getTile(curHead[0] + 1, curHead[1]);
				break;

			default:
				break;
		}
	}

	public void move() {
		if (this.isDead())
			return;

		this.body.addLast(this.head);

		if (this.nextHead.getType() == TileType.FOOD) {
			this.body.addFirst(this.nextHead);
			apples.removeApple(this.nextHead);
		}

		if (this.nextHead.getType() == TileType.BODY || this.nextHead.getType() == TileType.HEAD) {
			this.dead();
			return;
		}

		this.body.getLast().setType(TileType.BODY, this.colorBody);
		this.body.getFirst().setType(TileType.BACKGROUND);
		this.body.removeFirst();
		this.head = this.nextHead;
		this.head.setType(TileType.HEAD, this.colorHead);
	}

	public Tile getHead() {
		return this.head;
	}

	public int getBodySize() {
		return body.size();
	}

	public void dead() {
		this.dead = true;
		for (Tile tile : body) {
			apples.addApple(tile);
		}
		apples.addApple(head);
	}

	public boolean isDead() {
		return this.dead;
	}

	public String toString() {
		return String.format("[x=%d,y=%d/nexthead(x=%d,y=%d)]", head.getIndexX(), head.getIndexY(), nextHead.getIndexX(),
				nextHead.getIndexY());
	}
}
