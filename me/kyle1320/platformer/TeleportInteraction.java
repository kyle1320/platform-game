package me.kyle1320.platformer;

/**	An interaction that teleports the interacting player to coordinates relative to the interacting tile.
	@author Kyle Cutler
	@version 1/1/14
*/
public class TeleportInteraction implements Interaction {
	/**	<pre>Teleports the interacting player if they are close enough to the interacting tile. 
Tile data is read as follows:
	0:	double	Action distance
	1:	int 	Relative tile x coordinate
	2:	int 	Relative tile y coordinate
If both the x and y coordinates are 0 or are not specified, the player is not teleported.</pre>
		@param tile The interacting Tile
		@param player The Player interacting with the tile
	*/
	public void action(Tile tile, Player player) {
		double dist = player.getDistanceFrom(tile);
		TileData data = tile.getData();

		if (dist <= data.getInt(0, 0)) {
			int dx = data.getInt(1, 0);
			int dy = data.getInt(2, 0);

			if (dx == 0 && dy == 0)
				return;

			player.setX(tile.getTileX()+dx);
			player.setY(tile.getTileY()+dy);
		}
	}
}