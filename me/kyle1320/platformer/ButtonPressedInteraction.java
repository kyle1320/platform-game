package me.kyle1320.platformer;

import java.util.ArrayList;

/**	An interaction for pressed buttons. Calls an AppearInteraction once if the player is on top of the button, otherwise the interacting tile's material is set to the material of the unpressed button.
	@author Kyle Cutler
	@version 1/6/14	
*/
public class ButtonPressedInteraction implements Interaction {
	// Static so every instance knows about every pressed Tile.
	private static final ArrayList<Tile> acted = new ArrayList<Tile>();
	private AppearInteraction appear;
	private Material unpressed;

	/**	Creates a new ButtonPressedInteraction that uses the given unpressed button Material.
		@param unpressed The Material of the unpressed button
	*/
	public ButtonPressedInteraction(Material unpressed) {
		appear = new AppearInteraction();
		this.unpressed = unpressed;
	}

	/**	Calls an AppearInteraction on the interacting tile if the player in on top of the pressed button, otherwise sets the tile material to the unpressed button Material
		@param tile The interacting Tile
		@param player The Player interacting with the tile
	*/
	public void action(Tile tile, Player player) {
		if (player.overlaps(tile) && player.getMinY() >= tile.getMaxY() && player.touchDown()) {
			if (!acted.contains(tile)) {
				acted.add(tile);
				appear.action(tile, player);
			}
		} else {
			acted.remove(tile);
			tile.setMaterial(unpressed, false);
		}
	}
}