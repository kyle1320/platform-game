package me.kyle1320.platformer;

/**	An Interaction that causes a tile to disappear if the interacting player has the correct key color.
	@author Kyle Cutler
	@version 1/1/14
*/
public class LockInteraction implements Interaction {
	private KeyType key;

	/**	Creates a new LockInteraction that accepts the given key type.
		@param key The KeyType that this interaction accepts
	*/
	public LockInteraction(KeyType key) {
		this.key = key;
	}

	/**	If the interacting player has the correct key type, the interacting tile disappears and the player loses the key.
		@param tile The interacting Tile
		@param player The Player interacting with the tile
	*/
	public void action(Tile tile, Player player) {
		if (player.overlaps(tile))
			if (player.hasKey(key)) {
				tile.destroy();
				player.useKey(key);
			}
	}
}