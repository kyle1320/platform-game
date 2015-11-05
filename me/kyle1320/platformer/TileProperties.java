package me.kyle1320.platformer;

/**	Information about the material of a Tile.
	@author Kyle Cutler
	@version 1/1/14
*/
public class TileProperties {
	private final boolean isSolid;
	private final boolean isClimbable;
	private final Interaction interaction;

	/**	Creates a new set of TileProperties with default values of solid being false, climbable being false, and interaction being null.
	*/
	public TileProperties() {
		this(true, false, null);
	}

	/**	Creates a new set of TileProperties with the given solid value, and default other values.
		@param isSolid If the tile is a solid tile
	*/
	public TileProperties(boolean isSolid) {
		this(isSolid, false, null);
	}

	/**	Creates a new set of TileProperties with the given solid and climbable values, and the default interaction value.
		@param isSolid If the tile is a solid tile
		@param isClimbable If the tile is climbable
	*/
	public TileProperties(boolean isSolid, boolean isClimbable) {
		this(isSolid, isClimbable, null);
	}

	/**	Creates a new set of TileProperties with the given interaction, and default other values.
		@param interaction The interaction associated with the tile
	*/
	public TileProperties(Interaction interaction) {
		this(true, false, interaction);
	}

	/**	Creates a new set of TileProperties with the given solid, climbable and interaction values.
		@param isSolid If the tile is a solid tile
		@param isClimbable If the tile is climbable
		@param interaction The interaction associated with the tile
	*/
	public TileProperties(boolean isSolid, boolean isClimbable, Interaction interaction) {
		this.isSolid = isSolid;
		this.isClimbable = isClimbable;
		this.interaction = interaction;
	}

	/**	Returns if the tile is solid.
		@return If the tile is solid
	*/
	public boolean isSolid() {
		return isSolid;
	}

	/**	Returns if the tile is climbable.
		@return If the tile is climbable
	*/
	public boolean isClimbable() {
		return isClimbable;
	}

	/**	Returns the interaction associated with the tile.
		@return The interaction associated with the tile
	*/
	public Interaction getInteraction() {
		return interaction;
	}
}