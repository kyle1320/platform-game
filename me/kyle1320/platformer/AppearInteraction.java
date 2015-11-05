package me.kyle1320.platformer;

/**	An Interaction that allows tiles to be inserted or replaced within a level.
	@author Kyle Cutler
	@version 12/31/13
*/
public class AppearInteraction implements Interaction {
	private final boolean defaultTile;
	private final int material;
	private final int dx, dy;
	private final boolean disappear;
	private final boolean force;

	/**	Creates a new AppearInteraction that does not place a tile by default.
	*/
	public AppearInteraction() {
		this.defaultTile = false;
		this.material = 0;
		this.dx = 0;
		this.dy = 0;
		this.disappear = false;
		this.force = false;
	}

	/**	Creates a new AppearInteraction that creates a tile with the given default values.
		@param material The index of the material to place
		@param dx The relative x coordinate to place the new tile at
		@param dy The relative y coordinate to place the new tile at
		@param disappear True if the interacting tile should disappear when the new block is placed
		@param force True if the default tile should be placed regardless of whether or not the interacting tile has custom data
	*/
	public AppearInteraction(int material, int dx, int dy, boolean disappear, boolean force) {
		this.defaultTile = true;
		this.material = material;
		this.dx = dx;
		this.dy = dy;
		this.disappear = disappear;
		this.force = force;
	}

	/**	<pre>Performs the interaction between the given tile and player. 
Tile data is read as follows:
	0:	double 	Action distance
	1:	int 	Number of tiles to test
	1:	int 	Number of tiles to place
	2:	boolean	Disappearing
	3:	int 	Test material id
	4:	int 	Test relative x coordinate
	5:	int 	Test relative y coordinate
	6:	String	Test tile data
	7:	int 	Place material id
	8:	int 	Place relative x coordinate
	9:	int 	Place relative y coordinate
	10:	String	Place tile data
	11:	boolean	Force placement
indices 3-6 inclusive are repeated depending on the number of tiles to test
indices 3-7 inclusive are repeated depending on the number of tiles to place
All tests must match on Material for the tiles to be placed.
The default tile is placed if the tests pass and the number of tiles to place is not specified or is 0, or if force is true.</pre>
		@param tile The interacting Tile
		@param player The Player interacting with the tile
	*/
	public void action(Tile tile, Player player) {
		double dist = player.getDistanceFrom(tile);
		TileData data = tile.getData();

		if (dist <= data.getDouble(0, 1e-10)) {
			int tests = data.getInt(1, 0);
			int tiles = data.getInt(2, 0);
			int x = tile.getTileX();
			int y = tile.getTileY();

			for (int i=0; i < tests; i++) {
				Tile testTile = TileParser.parse(tile, data.getString(4+i));
				if (testTile.getMaterial() != tile.getLevel().getTileAt(testTile.getTileX(), testTile.getTileY()).getMaterial())
					return;
			}

			if (defaultTile && (force || tiles == 0)) {
				if (disappear)
					tile.destroy();

				Tile newTile = new Tile(Material.values()[material], tile.getLevel(), x+dx, y+dy);
				
				if (newTile.canBePlaced())
					newTile.place();
			}

			if (tiles > 0) {
				if (data.getBoolean(3))
					tile.destroy();

				for (int i=0; i < tiles; i++) {
					Tile newTile = TileParser.parse(tile, data.getString(tests+4+i*2, ""));
					
					if (data.getBoolean(tests+5+i*2, true) || newTile.canBePlaced()) {
						newTile.place();
					}
				}
			}
		}
	}
}