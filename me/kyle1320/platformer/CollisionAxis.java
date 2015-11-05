package me.kyle1320.platformer;

/**	Represents which sides of a Shape collided with another Shape during a collision. 
	Note "Axis" is meant to be plural.
	@author Kyle Cutler
	@version 12/31/13
*/
public final class CollisionAxis {
	public boolean north;
	public boolean south;
	public boolean east;
	public boolean west;

	/**	Creates a new CollisionAxis with default false values.
	*/
	public CollisionAxis() {}

	/**	Creates a new CollisionAxis with the given collision direction values.
		@param north True if the object collided on the north side
		@param south True if the object collided on the south side
		@param east True if the object collided on the east side
		@param west True if the object collided on the west side
	*/
	public CollisionAxis(boolean north, boolean south, boolean east, boolean west) {
		this.north = north;
		this.south = south;
		this.east = east;
		this.west = west;
	}
}