package me.kyle1320.platformer;

/**	An Interaction for buttons that changes the button material to its pressed form
	@author Kyle Cutler
	@version 12/31/13
*/
public class ButtonInteraction implements Interaction {
	private Material pressed;

	/**	Creates a new ButtonInteraction with the given material representing the button in its pressed state
		@param pressed The Material that represents the button in its pressed form
	*/
	public ButtonInteraction(Material pressed) {
		this.pressed = pressed;
	}

	/**	Changes the interacting tile to its pressed form if the player is on top of the button, and calls the pressed buttons interact event.
		@param tile The interacting Tile
		@param player The Player interacting with the tile
	*/
	public void action(Tile tile, Player player) {
		if (player.overlaps(tile) && player.getMinY() >= tile.getMaxY() && player.touchDown()) {
			tile.setMaterial(pressed, false);
			tile.interact(player);
		}
	}
}