package me.kyle1320.platformer;

/**	An Interaction that causes the interacting player to pick up a key.
	@author Kyle Cutler
	@version 1/1/14
*/
public class KeyInteraction implements Interaction {
	private KeyType key;

	/**	Creates a new KeyInteraction with the given key type.
		@param key The KeyType to use in this interaction
	*/
	public KeyInteraction(KeyType key) {
		this.key = key;
	}

	/**	Adds a key to the player's key collection if they do not already have a key of that type.
		@param tile The interacting Tile
		@param player The Player interacting with the tile
	*/
	public void action(Tile tile, Player player) {
		if (player.overlaps(tile) && !player.hasKey(key)) {
			player.getKey(key);
			tile.destroy();
		}
	}
}