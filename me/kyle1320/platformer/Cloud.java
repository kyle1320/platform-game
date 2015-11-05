package me.kyle1320.platformer;

import java.awt.Graphics2D;

/**	A cloud that spawns above a level and moves across the level.
	@author Kyle Cutler
	@version 1/12/14
*/
public class Cloud extends Entity {
	/**	Creates a new Cloud.
		@param game The game this cloud is to be added to.
	*/
	public Cloud(GameState game) {
				// choose a random cloud sprite
		super(	GameSprite.values()[(int)(Math.random()*3)+GameSprite.cloud1.ordinal()].getSprite(),
						 // spawn 1-2 blocks above the level
				game, 0, game.getLevel().getHeight()+Math.random()+1, 0, 0);

		// random movement from -2 to -1 or 1 to 2
		double movement = ((int)(Math.random()+0.5)*2-1)*(Math.random()+1);

		setMovementX(movement);

		if (movement > 0)	// come from the left side of the screen
			setX(-getWidth()+0.01);
		else				// come from the right side of the screen
			setX(game.getLevel().getWidth()-0.01);
	}

	/**	Clouds need custom update handling, since they do not collide or fall like normal entities. This method just moves the cloud.
	*/
	@Override
	public void update() {
		moveNormally(UPDATE_DELAY / 1000.0);

		if (getMaxX() < 0 || getMinX() > getGame().getLevel().getWidth())
			die();
	}
}