package Client.model;

import java.util.HashSet;

import Client.util.GameRandom;
import Client.util.TileType;

public class TileGrid {
	private static Tile[][] tiles;
	private static int columns, rows, unitSize;
	private HashSet<Tile> freeSet = new HashSet<Tile>();

	public TileGrid(int columns, int rows, int sizeTile) {
		TileGrid.tiles = new Tile[columns][rows];
		TileGrid.columns = columns;
		TileGrid.rows = rows;
		TileGrid.unitSize = sizeTile;

		for (int x = 0; x < columns; x++)
			for (int y = 0; y < rows; y++) {
				TileGrid.tiles[x][y] = new Tile(new int[] { x, y }, new int[] { sizeTile, sizeTile });
				freeSet.add(TileGrid.getTile(x, y));
			}

	}

	public TileGrid() {
		for (int x = 0; x < columns; x++)
			for (int y = 0; y < rows; y++)
				freeSet.add(TileGrid.getTile(x, y));
	}

	public static Tile[][] getTiles() {
		return TileGrid.tiles;
	}

	public static Tile getTile(int x, int y) {
		return TileGrid.tiles[x][y];
	}

	public static void setTile(int x, int y, TileType type) {
		TileGrid.tiles[x][y].setType(type);
	}

	public static int getRows() {
		return TileGrid.rows;
	}

	public static int getColumns() {
		return TileGrid.columns;
	}

	public static int getUnitSize() {
		return TileGrid.unitSize;
	}

	public HashSet<Tile> getFreeSet() {
		return this.freeSet;
	}

	public Tile getRandomInFreeSet() {
		Tile tile = GameRandom.getRandomElement(this.freeSet);
		popFreeSetElement(tile);
		return tile;
	}

	public void popFreeSetElement(Tile tile) {
		this.freeSet.remove(tile);
	}

	public void resetFreeSet() {
		this.freeSet.clear();
		for (int x = 0; x < columns; x++)
			for (int y = 0; y < rows; y++)
				freeSet.add(TileGrid.getTile(x, y));
	}

	public static void reset() {
		for (int x = 0; x < columns; x++)
			for (int y = 0; y < rows; y++) {
				TileGrid.tiles[x][y].reset();
			}
	}

}
