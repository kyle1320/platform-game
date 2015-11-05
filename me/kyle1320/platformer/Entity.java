package me.kyle1320.platformer;

import java.awt.Graphics2D;
import java.util.ArrayList;

/**	Represents a moving entity in the game.
	@author Kyle Cutler
	@version 12/31/13
*/
public class Entity extends Collidable implements Updatable {
	private Sprite sprite;
	private GameState game;

	private double movX;
	private double movY;

	private double walkingMovement;

	private boolean touchUp, touchDown, touchLeft, touchRight; // tile contact booleans
	private boolean direction;	// true for right facing, false for left facing
	private boolean dead;

	/**	Creates a new Entity with the given information.
		@param sprite The Sprite that represents this Entity
		@param game The GameState that holds this Entity
		@param posX This Entity's starting X coordinate
		@param posY This Entity's starting Y coordinate
		@param movX This Entity's starting X movement value
		@param movY This Entity's starting y movement value
	*/
	public Entity(Sprite sprite, GameState game, double posX, double posY, double movX, double movY) {
		super(sprite.getShape(), posX, posY);
		this.sprite = sprite;
		this.game = game;

		this.movX = movX;
		this.movY = movY;

		this.walkingMovement = 0;

		this.direction = true;
		this.dead = false;
	}

	/**	Updates this Entity.
	*/
	public void update() {
		//System.out.println(getX() + ", " + getY());
		push(0, -UPDATE_DELAY / 50.0);
		resetTouches();

		moveUntilCollision(UPDATE_DELAY / 1000.0);

		sprite.update();

		//System.out.println(touchUp + ", " + touchDown + ", " + touchLeft + ", " + touchRight);
	}

	/**	"Kills" this Entity. This means that the entity should be removed from the game.
	*/
	public void die() {
		this.dead = true;
	}

	/**	Moves this Entity based on its movement and time.
		@param time The amount of time over which to move
	*/
	public void moveNormally(double time) {
		move((movX + walkingMovement) * time, movY * time);
	}

	/**	Moves this Entity over the given amount of time and collides with any nearby tiles.
		@param time The amount of time over which to move
	*/
	private void moveUntilCollision(double time) {
		double width = game.getLevel().getWidth();
		double height = game.getLevel().getHeight();

		moveNormally(time);										// move first

		handleCollision(pushInBounds(0, 0, width, Double.POSITIVE_INFINITY));		// move inside the screen (unbounded upper y)

		collideWithNearbyTiles();								// then collide with tiles
	}

	/**	Collides this Entity with any nearby tiles.
	*/
	private void collideWithNearbyTiles() {
		int minx = (int)Math.max(0, getMinX());		// get any tiles we overlap with
		int miny = (int)Math.max(0, getMinY());

		int maxx = (int)Math.min(game.getLevel().getWidth() - 1, getMaxX());
		int maxy = (int)Math.min(game.getLevel().getHeight() - 1, getMaxY());

		for (int y=miny; y <= maxy; y++) {
			for (int x=minx; x <= maxx; x++) {
				Tile tile = game.getLevel().getTileAt(x, y);

				if (tile.getProperties().isSolid()) {
					collideWithTile(tile);
				}
			}
		}
	}

	/**	Collides this Entity with a specific tile.
		@param tile The tile to collide with
	*/
	private void collideWithTile(Tile tile) {
		Collision c = collideWith(tile, movX, movY);

		CollisionAxis axis = c.collision;	// sometimes if running into a wall, we may get caught on a block in the wall.
		Tile t;								// we need to test for this here, and if we can't move in the directions we're supposed to,
											// then don't.
		if (axis.north) {
			t = tile.getRelative(0, -1);
			if (t != null && t.getProperties().isSolid() && overlaps(t)) {
				axis.north = false;
				c.y = 0;
			}
		}

		if (axis.south) {
			t = tile.getRelative(0, 1);
			if (t != null && t.getProperties().isSolid() && overlaps(t)) {
				axis.south = false;
				c.y = 0;
			}
		}

		if (axis.east) {
			t = tile.getRelative(1, 0);
			if (t != null && t.getProperties().isSolid() && overlaps(t)) {
				axis.east = false;
				c.x = 0;
			}
		}

		if (axis.west) {
			t = tile.getRelative(-1, 0);
			if (t != null && t.getProperties().isSolid() && overlaps(t)) {
				axis.west = false;
				c.x = 0;
			}
		}

		move(c.x, c.y);

		handleCollision(axis);
	}

	/**	Resets the contact booleans so that they can be recalculated during an update.
	*/
	private void resetTouches() {
		touchUp = false;
		touchDown = false;
		touchLeft = false;
		touchRight = false;
	}

	/**	Changes entity movement and contact booleans based on a collision.
		@param collision The CollisionAxis representing a collision by this Entity
	*/
	private void handleCollision(CollisionAxis collision) {
		if (collision.north || collision.south)
			setMovementY(0);
		if (collision.east || collision.west)
			setMovementX(0);

		touchUp |= collision.north;
		touchDown |= collision.south;
		touchLeft |= collision.west;
		touchRight |= collision.east;
	}

	/**	Draws this Entity on the given Graphics2D object at the given x and y coordinates, and with the given scaling factor.
		@param g The Graphics2D object on which to draw
		@param x The x coordinate to draw this Entity
		@param y The y coordinate to draw this Entity
		@param scale The amount to scale this Entity's actual width and height by before drawing it
	*/
	public void draw(Graphics2D g, double x, double y, double scale) {
		double realWidth = getWidth() * scale;
		double realHeight = getHeight() * scale;

		if (x+realWidth > 0 && x < g.getClipBounds().getWidth() &&
			y+realHeight > 0 && y < g.getClipBounds().getHeight())
			sprite.draw(g, (int)x, (int)(y-realHeight), (int)realWidth, (int)realHeight, !direction);
	}

	/**	Returns whether or not the top of this entity is touching a tile.
		@return True if the top of this entity is touching a tile
	*/
	public boolean touchUp() {
		return touchUp;
	}

	/**	Returns whether or not the bottom of this entity is touching a tile.
		@return True if the bottom of this entity is touching a tile
	*/
	public boolean touchDown() {
		return touchDown;
	}

	/**	Returns whether or not the left side of this entity is touching a tile.
		@return True if the left side of this entity is touching a tile
	*/
	public boolean touchLeft() {
		return touchLeft;
	}

	/**	Returns whether or not the right side of this entity is touching a tile.
		@return True if the right side of this entity is touching a tile
	*/
	public boolean touchRight() {
		return touchRight;
	}

	/**	Sets the movement contibuted by "walking" movement of this entity. Walking movement is along the x axis.
		@param walk The movement to be contibuted by "walking" movement of this entity
	*/
	public void walk(double walk) {
		this.walkingMovement = walk;

		if (walk > 0)
			direction = true;
		else if (walk < 0)
			direction = false;
	}

	/**	"Pushes" this Entity by a given amount along the x and y axis. Pushing means that the x and y values are added to the movement of this Entity.
		@param pushX The amount to push this Entity along the x axis
		@param pushY The amount to push this Entity along the y axis
	*/
	public void push(double pushX, double pushY) {
		this.movX += pushX;
		this.movY += pushY;
	}

	/**	"Pushes" this Entity by a given amount along the x axis.
		@param pushX The amount to push this Entity along the x axis
	*/
	public void pushX(double pushX) {
		this.movX += pushX;
	}

	/**	"Pushes" this Entity by a given amount along the y axis.
		@param pushY The amount to push this Entity along the y axis
	*/
	public void pushY(double pushY) {
		this.movY += pushY;
	}

	/**	Sets this Entity's sprite to the given sprite
		@param sprite the new Sprite to give this Entity
	*/
	public void setSprite(Sprite sprite) {
		if (this.sprite != sprite) {
			sprite.reset();
			this.sprite = sprite;
			setShape(sprite.getShape());
		}
	}

	/**	Sets the game this Entity is currently in.
		@param newGame the GameState to link this Entity to.
	*/
	public void setGame(GameState newGame) {
		this.game = newGame;
	}

	/**	Sets the x and y movement of this Entity.
		@param movX the new x movement for this Entity
		@param movY the new y movement for this Entity
	*/
	public void setMovement(double movX, double movY) {
		this.movX = movX;
		this.movY = movY;
	}

	/**	Sets the x movement of this Entity.
		@param movX the new x movement for this Entity
	*/
	public void setMovementX(double movX) {
		this.movX = movX;
	}

	/**	Sets the y movement of this Entity.
		@param movY the new y movement for this Entity
	*/
	public void setMovementY(double movY) {
		this.movY = movY;
	}

	/**	Return true if this Entity is "falling"; that is, the entity is not touching the ground.
		@return True if this Entity is falling
	*/
	public boolean isFalling() {
		return !touchDown;
	}

	/**	Returns true if this Entity is dead.
		@return True if this entity is dead
	*/
	public boolean isDead() {
		return this.dead;
	}

	/**	Return this Entity's sprite
		@return this Entity's sprite
	*/
	public Sprite getSprite() {
		return sprite;
	}

	/**	Return this Entity's game
		@return the GameState this Entity
	*/
	public GameState getGame() {
		return game;
	}

	/**	Returns this Entity's movement along the x axis
		@return This Entity's movement along the x axis
	*/
	public double getMovementX() {
		return movX;
	}

	/**	Returns this Entity's movement along the y axis
		@return This Entity's movement along the y axis
	*/
	public double getMovementY() {
		return movY;
	}

	/**	Returns the movement contibuted by "walking" by this entity
		@return This Entity's walking movement
	*/
	public double getWalkingMovement() {
		return walkingMovement;
	}
}