package me.kyle1320.platformer;

/**	An Interaction that ends the level the interacting player is currently in.
	@author Kyle Cutler
	@version 12/31/13
*/
public class FinishInteraction implements Interaction {
	/**	Ends the game the interacting player is in when they overlap the interacting tile.
		@param tile The interacting Tile
		@param player The Player interacting with the tile
	*/
	public void action(Tile tile, Player player) {
		if (player.overlaps(tile))
			player.getGame().finishLevel();
	}
}