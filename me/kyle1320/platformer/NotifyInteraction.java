package me.kyle1320.platformer;

import java.util.ArrayList;

/**	Sets the interacting Player's Message when the player is close enough to the interacting Tile.
	@author Kyle Cutler
	@version 1/1/14
*/
public class NotifyInteraction implements Interaction {
	/**	<pre>Sets the interacting Player's Message when the player is close enough to the interacting Tile. 
Tile data is read as follows: 
	0:	String	The message to display 
	1:	double	Action distance 
	2:	int 	Message display time, in milliseconds<pre>
		@param tile The interacting Tile
		@param player The Player interacting with the tile
	*/
	public void action(Tile tile, Player player) {
		double dist = player.getDistanceFrom(tile);

		if (dist <= tile.getData().getDouble(1, 0.5)) {
			player.getMessage().newMessage(tile.getData().getString(0), tile.getData().getInt(2, 2000));
		}
	}
}