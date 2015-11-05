package me.kyle1320.platformer;

/**	An interface used to create interactive gameplay beween the player and tiles.
	@author Kyle Cutler
	@version 1/1/14
*/
public interface Interaction {
	/**	Causes some interaction between the given player and tile.
		@param tile The interacting Tile
		@param player The Player interacting with the tile
	*/
	public void action(Tile tile, Player player);
}