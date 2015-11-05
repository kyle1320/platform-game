package me.kyle1320.platformer;

/**	An Interaction for coins that adds the coin value to the player score
	@author Kyle Cutler
	@version 12/31/13
*/
public class CoinInteraction implements Interaction {
	private int value;

	/**	Creates a new CoinInteraction with the given coin value
		@param value The coin value
	*/
	public CoinInteraction(int value) {
		this.value = value;
	}

	/**	Adds the coin value to the player score if they are close enough and makes the coin disappear.
		@param tile The interacting Tile
		@param player The Player interacting with the tile
	*/
	public void action(Tile tile, Player player) {
		if (player.overlaps(tile)) {
			player.addPoints(value);
			tile.destroy();
		}
	}
}