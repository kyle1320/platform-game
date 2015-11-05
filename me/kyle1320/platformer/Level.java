package me.kyle1320.platformer;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import java.util.Scanner;
import java.io.File;

/**	Stores Tiles in an array to form a playable level.
	@author Kyle Cutler
	@version 1/1/14
*/
public class Level {
	private GameLevel gameLevel;
	private int width, height;
	private Tile[][] tiles;

	private Background background;

	private double playerStartX, playerStartY;

	/**	creates a new Level from the given GameLevel and file name. This should only be called from within GameLevel.
		@param gameLevel The GameLevel associated with this level
		@param fileName The path to the file that contains data for this level
	*/
	Level(GameLevel gameLevel, String fileName) {
		this.gameLevel = gameLevel;
		try {
			processFile(new Scanner(Level.class.getResourceAsStream(fileName)));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Could not read level at " + fileName);
		}
	}

	/**	Reads a file and creates this Level using data in the file.
		@param fin The file to read
	*/
	private void processFile(Scanner in) throws Exception {
		width = in.nextInt();	// level width
		height = in.nextInt();	// and height

		tiles = new Tile[height][width];

		int x, y;
		// read in each tile material and create the tile at that coordinate
		for (y=height-1; y >= 0; y--) {
			for (x=0; x < width; x++) {
				tiles[y][x] = new Tile(Material.values()[in.nextInt()], this, x, y);
			}
		}

		background = Background.values()[in.nextInt()];	// background id

		playerStartX = in.nextDouble();	// player start position x
		playerStartY = in.nextDouble();	// and y

		while (in.hasNext()) {			// read custom tile data
			x = in.nextInt();
			y = in.nextInt();

			tiles[y][x].setData(TileData.read(in));
		}
	}

	/**	Updates each Tile.
	*/
	public void update() {
		for (int y=0; y < height; y++) {
			for (int x=0; x < width; x++) {
				tiles[y][x].update();
			}
		}
	}

	/**	Sets the tile at the given coordinates to air.
	*/
	public void destroyTile(int x, int y) {
		if (x >= 0 && y >= 0 && x < width && y < height) {
			tiles[y][x] = new Tile(Material.air, this, x, y);//.setMaterial(Material.air, true);
		}
	}

	/**	Returns true if the tile has coordinates within bounds and the tile currently at those coordinates is air.
		@return True if the tile has coordinates within bounds and the tile currently at those coordinates is air
	*/
	public boolean canPlaceTile(Tile tile) {
		return canPlaceTile(tile.getTileX(), tile.getTileY());
	}

	/**	Returns true if the given coordinates are within bounds and the tile currently at those coordinates is air.
		@param x The x coordinate to test
		@param y The y coordinate to test
		@return True if the tile has coordinates within bounds and the tile currently at those coordinates is air
	*/
	public boolean canPlaceTile(int x, int y) {
		if (x >= 0 && y >= 0 && x < width && y < height) {
			return tiles[y][x].getMaterial() == Material.air;
		}

		return false;
	}

	/**	Places the given tile in this level if its coordinates are within bounds.
		@param tile The tile to place
	*/
	public void placeTile(Tile tile) {
		int x = tile.getTileX();
		int y = tile.getTileY();

		if (x >= 0 && y >= 0 && x < width && y < height) {
			tiles[y][x] = tile;
		}
	}

	/**	Returns the tile at the given coordinates
		@param x The x coordinate
		@param y The y coordinate
		@return The tile at the given coordinates, or null if the coordinates are out of bounds.
	*/
	public Tile getTileAt(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return null;
		return tiles[y][x];
	}

	/**	Return the level name
		@return The level name
	*/
	public String getName() {
		return gameLevel.toString();
	}

	/**	Returns the GameLevel associated with this level.
		@return The GameLevel associated with this level
	*/
	public GameLevel getGameLevel() {
		return gameLevel;
	}

	/**	Returns this level's background.
		@return This level's background
	*/
	public Background getBackground() {
		return background;
	}

	/**	Returns this level's width.
		@return This level's width
	*/
	public int getWidth() {
		return width;
	}

	/**	Returns this level's height.
		@return This level's height
	*/
	public int getHeight() {
		return height;
	}

	/**	Returns the x coordinate of the player's start position in this level.
		@return The x coordinate of the player's start position in this level
	*/
	public double getPlayerStartX() {
		return playerStartX;
	}

	/**	Returns the y coordinate of the player's start position in this level.
		@return The y coordinate of the player's start position in this level
	*/
	public double getPlayerStartY() {
		return playerStartY;
	}
}