package me.kyle1320.platformer;

/**	A tile in a Level.
	@author Kyle Cutler
	@version 1/1/14
*/
public class Tile extends Collidable {
	private Material material;
	private Sprite sprite;
	private TileData data;

	private Level level;
	/*private int x;
	private int y;*/
	
	/**	Creates a new Tile with the given properties.
		@param mat The material this tile is to be made of
		@param level The Level this tile is in
		@param x The x coordinate of this tile in the level
		@param y Th y coordinate of this tile in the level
	*/
	public Tile(Material mat, Level level, int x, int y) {
		super(mat.getSpriteShape(), x, y);
		this.material = mat;

		this.level = level;
		/*this.x = x;
		this.y = y;*/

		this.sprite = mat.getSprite();
		this.data = new TileData();
	}

	/**	Updates this Tile's sprite.
	*/
	public void update() {
		sprite.update();
	}

	/**	Removes this tile from its level.
	*/
	public void destroy() {
		level.destroyTile(getTileX(), getTileY());
	}

	/**	Returns true if this tile can be placed in its level.
		@return True if this tile can be placed in its level.
	*/
	public boolean canBePlaced() {
		return level.canPlaceTile(this);
	}

	/**	Places this tile in its level.
	*/
	public void place() {
		level.placeTile(this);
	}

	public void move(int x, int y) {
		if ((x != getTileX() || y != getTileY()) && level.canPlaceTile(x, y)) {
			destroy();
			setX(x);
			setY(y);
			place();
		}
	}

	/**	Sets the material of this tile.
		@param newMaterial The material to change this tile's material to
		@param eraseData True if this tile's custom data should be erased.
	*/
	public void setMaterial(Material newMaterial, boolean eraseData) {
		this.material = newMaterial;
		this.sprite = newMaterial.getSprite();

		if (eraseData)
			this.data = new TileData();
	}

	/**	sets this tile's data.
		@param data The new data for this tile
	*/
	public void setData(TileData data) {
		this.data = data;
	}

	/**	Returns the Tile at the given position relative to this tile.
		@param x The relative x coordinate
		@param y The relative y coordinate
		@return The tile at the given position relative to this tile in the same level as this tile
	*/
	public Tile getRelative(int x, int y) {
		return level.getTileAt(getTileX() + x, getTileY() + y);
	}

	/**	Returns this tile's data.
		@return This tile's data
	*/
	public TileData getData() {
		return data;
	}

	/**	Returns this tile's material.
		@return This tile's material
	*/
	public Material getMaterial() {
		return material;
	}

	/**	Returns this tile's sprite.
		@return This tile's sprite
	*/
	public Sprite getSprite() {
		return sprite;
	}

	/**	Returns this tile's properties.
		@return This tile's properties
	*/
	public TileProperties getProperties() {
		return material.getProperties();
	}

	/**	Causes this tile to call its interaction with the given player.
		@param player The player to interact with
	*/
	public void interact(Player player) {
		Interaction i = getProperties().getInteraction();

		if (i != null)
			i.action(this, player);
	}

	/**	Returns this tile's level.
		@return This tile's level
	*/
	public Level getLevel() {
		return level;
	}

	/**	Returns this tile's x coordinate as an int.
		@return This tile's x coordinate as an int
	*/
	public int getTileX() {
		return (int)super.getX();
	}

	/**	Returns this tile's y coordinate as an int.
		@return This tile's y coordinate as an int
	*/
	public int getTileY() {
		return (int)super.getY();
	}

	public String toString() {
		return "Tile at (" + getTileX() + ", " + getTileY() + ") of " + getMaterial();
	}
}