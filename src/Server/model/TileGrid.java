package Server.model;

import java.util.HashSet;
import java.util.Set;
import Client.util.TileType;

public class TileGrid {
	private Tile[][] tiles;
	private int columns;
	private int rows;
	private int unitSize;
	private HashSet<Tile> tilesSet;

	public TileGrid(int columns, int rows, int sizeTile) {
		this.tiles = new Tile[columns][rows];
		this.columns = columns;
		this.rows = rows;
		this.unitSize = sizeTile;
		this.tilesSet = new HashSet<>();
		for (int x = 0; x < columns; x++)
			for (int y = 0; y < rows; y++) {
				this.tiles[x][y] = new Tile(new int[] { x, y }, new int[] { sizeTile, sizeTile });
				tilesSet.add(this.tiles[x][y]);
			}

	}

	public Tile[][] getClone() {
		Tile[][] result = new Tile[this.getColumns()][this.getRows()];
		for (int x = 0; x < this.getColumns(); x++)
			for (int y = 0; y < this.getRows(); y++) {
				Tile tile = this.getTile(x, y);
				result[x][y] = new Tile(tile.getIndex(), tile.getSize());
				result[x][y].setType(tile.getType(), tile.getColor());
			}
		return result;
	}

	public Tile[][] getNew() {
		Tile[][] result = new Tile[this.getColumns()][this.getRows()];
		for (int x = 0; x < this.getColumns(); x++)
			for (int y = 0; y < this.getRows(); y++)
				result[x][y] = new Tile(new int[] { x, y }, new int[] { this.unitSize, this.unitSize });
		return result;
	}

	public Tile[][] getTiles() {
		return this.tiles;
	}

	public Tile getTile(int x, int y) {
		return this.tiles[x][y];
	}

	public void setTile(int x, int y, TileType type) {
		this.tiles[x][y].setType(type);
	}

	public int getRows() {
		return this.rows;
	}

	public int getColumns() {
		return this.columns;
	}

	public Set<Tile> getTilesSet() {
		return this.tilesSet;
	}

	public void reset() {
		for (int x = 0; x < columns; x++)
			for (int y = 0; y < rows; y++) {
				this.tiles[x][y].reset();
			}
	}

}
