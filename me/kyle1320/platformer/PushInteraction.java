package me.kyle1320.platformer;

/**	Causes the interacting tile to move away from the player if possible.
	@author Kyle Cutler
	@version 1/1/14
*/
public class PushInteraction implements Interaction {
	/**	<pre>Moves the interacting tile away from the player on whichever side the player is touching it. 
Tile data is read as follows: 
	0:	boolean	Tile can move up 
	1:	boolean	Tile can move down 
	2:	boolean	Tile can move left 
	3:	boolean	Tile can move right 
The default for each is true.</pre>
		@param tile The interacting Tile
		@param player The Player interacting with the tile
	*/
	public void action(Tile tile, Player player) {
		if (player.overlaps(tile)) {
			int nx, ny;
			TileData data = tile.getData();

			if (player.getMaxY() <= tile.getMinY() && player.touchUp() && data.getBoolean(0, true)) {
				nx = tile.getTileX();
				ny = tile.getTileY()+1;
			} else if (player.getMinY() >= tile.getMaxY() && player.touchDown() && data.getBoolean(1, true)) {
				nx = tile.getTileX();
				ny = tile.getTileY()-1;
			} else if (player.getMinX() >= tile.getMaxX() && player.touchLeft() && data.getBoolean(2, true)) {
				nx = tile.getTileX()-1;
				ny = tile.getTileY();
			} else if (player.getMaxX() <= tile.getMinX() && player.touchRight() && data.getBoolean(3, true)) {
				nx = tile.getTileX()+1;
				ny = tile.getTileY();
			} else {
				return;
			}

			tile.move(nx, ny);
		}
	}
}