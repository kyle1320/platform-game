package me.kyle1320.platformer;

import java.awt.event.KeyEvent;

/**	The Entity that the user controls in the game.
	@author Kyle Cutler
	@version 1/1/14
*/
public class Player extends Entity {
	// Static constants mapping movement keys.
	private static final int LEFT_KEY = KeyEvent.VK_A;
	private static final int RIGHT_KEY = KeyEvent.VK_D;
	private static final int UP_KEY = KeyEvent.VK_W;
	private static final int DOWN_KEY = KeyEvent.VK_S;
	private static final int JUMP_KEY = KeyEvent.VK_SPACE;

	// Player speed won't change according to the instance.
	private static final double MOVE_SPEED = 3.0;

	private Sprite standSprite;
	private Sprite moveSprite;							
	private Sprite jumpSprite;
	private Sprite crouchSprite;
	private Sprite climbSprite;

	// actual movement booleans
	private boolean jumping, crouching, climbing;

	private Message message;
	private boolean[] keys;
	private int points;

	/**	Creates a new player with the given properties.
		@param x The x coordinate of this player
		@param y The y coordinate of this player
	*/
	public Player(double x, double y, GameState game) {
		super(GameSprite.player1Stand.getSprite(), game, x, y, 0, 0);

		initializeSprites();
		setSprite(standSprite);

		reset();
	}

	/**	Initializes the sprites to be used when drawing this player.
	*/
	private void initializeSprites() {
		standSprite = GameSprite.player1Stand.getSprite();
		moveSprite = GameSprite.player1Move.getSprite();
		jumpSprite = GameSprite.player1Jump.getSprite();
		crouchSprite = GameSprite.player1Crouch.getSprite();
		climbSprite = GameSprite.player1Climb.getSprite();
	}

	/**	Updates this player.
	*/
	public synchronized void update() {
		message.update();	// update the display message

		playerMovement();	// do player movement (jump, climb, key movement)
		determineSprite();	// determine what the player should look like

		super.update();		// do other entity movement

		interactWithTiles();// once we're in the right place, interact with the tiles around us (signs, keys etc.)
	}

	/**	<pre>Determines player movement. Player movement is determined by the following rules:
	1.	The player moves left if the user is pressing the left key but not the right key
	2.	The player moves right if the user is pressing the right key but not the left key 
	3.	The player cannot jump or climb while crouching. 
	4.	The player cannot jump while in the air, climbing, crouching, or while they can climb.
	5.	The player stops jumping or climbing when they touch the ground. 
	6.	The player crouches when the user presses the down key or the player is too tall for the space. 
	7.	The player moves up a ladder if the user is pressing the up key, and down if they are not. 
	8.	The player can crouch while in the air, but not while on a ladder.</pre>
	*/
	private void playerMovement() {
		if (touchDown()) {						// if we're on the ground
			jumping = false;					// stop jumping
			climbing = false;					// and stop climbing
		}

		if (crouching && tooTallForSpace())		// if we're crouching and we can't stand up
			crouch();							// keep crouching
		else if (!downKeyPressed())				// otherwise if the user isn't pressing the down key
			crouching = false;					// stop crouching

		if (downKeyPressed())					// if the user is pressing the down key
			crouch();							// crouch
		else if (jumpKeyPressed())				// otherwise if the user is pressing the jump key
			jump();								// jump

		if (!climbing)							// if we're not climbing right now
			if (upKeyPressed() || isFalling())	// if we're trying to climb or we're in the air (and may have landed on a ladder)
				climb();						// try to start climbing

		if (canClimbUp())						// if we're still on a ladder
			if (upKeyPressed())	{				// if we're trying to move up
				if (climbing)					// and we're climbing (should be true)
					setMovementY(3);			// move up
			} else {							// otherwise
				if (getMovementY() > 0)			// if we're still moving up
					setMovementY(0);			// stop moving up
				else if (getMovementY() < -2.5)	// otherwise if we're falling too fast
					setMovementY(-2.5);			// move down at a constant speed
			}
		else									// if we can't climb
			climbing = false;					// stop climbing
		
		int mx = (rightKeyPressed()?1:0) - (leftKeyPressed()?1:0);	// get the direction we're moving by user input
		walk(mx * MOVE_SPEED);				// move that direction
	}

	/**	Sets the player jumping if possible.
	*/
	private void jump() {
		if (!crouching && !climbing && !jumping && touchDown() && !canClimbUp()) {// can't jump while crouching or climbing and can't double jump / jump in mid-air
			jumping = true;		// start jumping
			push(0, 7.0);		// move up (the actual jump)
		}
	}

	/**	Sets the player climbing if possible.
	*/
	private void climb() {
		if (!(crouching && touchDown()) && canClimbUp()) {	// climb if we can, unless we're crouching on the ground
			crouching = false;	// can't be crouching while climbing
			jumping = false;	// can't be jumping while climbing
			climbing = true;	// start climbing
		}
	}

	/**	Sets the player crouching if possible.
	*/
	private void crouch() {
		if (!climbing) {			// if we're not climbing (can't crouch on a ladder...)
			crouching = true;		// start crouching
		}
	}

	/**	Determines which sprite should be displayed by this player and changes to it.
	*/
	private void determineSprite() {
		if (crouching) {
			setSprite(crouchSprite);
		}else if (climbing) {
			setSprite(climbSprite);
		}else if (jumping) {
			setSprite(jumpSprite);
		}else if (getWalkingMovement() != 0) {
			setSprite(moveSprite);
		}else {
			setSprite(standSprite);
		}
	}

	/**	Returns true if the player is too tall to stand up in the space they are in.
		@return True if the player is too tall to stand up in the space they are in
	*/
	private boolean tooTallForSpace() {
		double height = standSprite.getShape().getHeight();
		Tile topLeft = getGame().getLevel().getTileAt((int)getMinX(), (int)(getMinY() + height));
		Tile topRight = getGame().getLevel().getTileAt((int)getMaxX(), (int)(getMinY() + height));

		boolean leftSolid = topLeft != null && topLeft.getProperties().isSolid();
		boolean rightSolid = topRight != null && topRight.getProperties().isSolid();

		if (!(leftSolid || rightSolid))
			return false;

		double miny = leftSolid ? rightSolid ? Math.min(topLeft.getMinY(), topRight.getMinY()) : topLeft.getMinY() : rightSolid ? topRight.getMinY() : 0;

		return getMinY() + height > miny;
	}

	/**	Returns true if the player is over a climbable object.
		@return True if the player is over a climbable object
	*/
	private boolean canClimbUp() {
		Tile bottom = getGame().getLevel().getTileAt((int)getMidX(), (int)getY());

		return bottom != null && bottom.getProperties().isClimbable();
	}

	/**	Calls interactions with tiles within a square radius of 2.
	*/
	private void interactWithTiles() {
		GameState game = getGame();

		int minx = (int)Math.max(0, getX() - 2);
		int miny = (int)Math.max(0, getY() - 2);

		int maxx = (int)Math.min(game.getLevel().getWidth() - 1, (getX() + getWidth() + 2));
		int maxy = (int)Math.min(game.getLevel().getHeight() - 1, (getY() + getHeight() + 2));

		for (int y=miny; y <= maxy; y++) {
			for (int x=minx; x <= maxx; x++) {
				Tile tile = game.getLevel().getTileAt(x, y);
				
				tile.interact(this);
			}
		}
	}

	/**	Returns true if the user is pressing the up movement key.
		@return True if the user is pressing the up movement key
	*/
	public boolean upKeyPressed() {
		return Input.isKeyPressed(UP_KEY);
	}

	/**	Returns true if the user is pressing the down movement key.
		@return True if the user is pressing the down movement key
	*/
	public boolean downKeyPressed() {
		return Input.isKeyPressed(DOWN_KEY);
	}

	/**	Returns true if the user is pressing the left movement key.
		@return True if the user is pressing the left movement key
	*/
	public boolean leftKeyPressed() {
		return Input.isKeyPressed(LEFT_KEY);
	}

	/**	Returns true if the user is pressing the right movement key.
		@return True if the user is pressing the right movement key
	*/
	public boolean rightKeyPressed() {
		return Input.isKeyPressed(RIGHT_KEY);
	}

	/**	Returns true if the user is pressing the jump movement key.
		@return True if the user is pressing the jump movement key
	*/
	public boolean jumpKeyPressed() {
		return Input.isKeyPressed(JUMP_KEY);
	}

	/**	Returns true if the player has the given key type.
		@param type The KeyType to check
		@return True if the player has the given key type
	*/
	public boolean hasKey(KeyType type) {
		return keys[type.ordinal()];
	}

	/**	Adds the given key type to the player's keys.
		@param type They KeyType to add
	*/
	public void getKey(KeyType type) {
		keys[type.ordinal()] = true;
	}

	/**	Removes the given key type from the player's keys.
		@param type They KeyType to remove
	*/
	public void useKey(KeyType type) {
		keys[type.ordinal()] = false;
	}

	/**	Resets the player's position to its default state.
	*/
	public void reset() {
		jumping = crouching = climbing = false;

		message = new Message();
		keys = new boolean[KeyType.values().length];
		points = 0;
	}

	/**	Returns the Message being displayed to this player.
		@return The Message being displayed to this player
	*/
	public Message getMessage() {
		return message;
	}

	/**	Adds a given number of points to this player's score.
		@param points The number of points to add
	*/
	public void addPoints(int points) {
		this.points += points;
	}

	/**	Returns this player's score.
		@return This player's score
	*/
	public int getPoints() {
		return points;
	}
}