package me.kyle1320.platformer;

import java.util.ArrayList;
import java.util.Iterator;

/**	Holds the variables necessary to play the game.
	@author Kyle Cutler
	@version 12/31/13
*/
public class GameState implements Updatable {
	private long time;
	private boolean finished;

	private Level level;
	private Player player;
	private ArrayList<Entity> entities;

	/**	Creates a new GameState from the given level, creating a new Player.
		@param level The level to play
	*/
	public GameState(Level level) {
		this.time = 0;
		this.finished = false;

		this.level = level;
		this.player = new Player(level.getPlayerStartX(), level.getPlayerStartY(), this);
		this.entities = new ArrayList<Entity>();
	}

	/**	Updates the level and entities, including the player.
	*/
	public void update() {
		time += UPDATE_DELAY;

		level.update();
		player.update();

		int cloudCount = 0;
		Entity e;
		Iterator<Entity> it = entities.iterator();
		while (it.hasNext()) {
			e = it.next();
			e.update();		// update the entity

			if (e instanceof Cloud)
				cloudCount++;

			if (e.isDead())	// if the entity died
				it.remove();// remove it
		}

		// If there are too few clouds on the screen create a new cloud about every second
		if (cloudCount < level.getWidth()/2 && Math.random() < UPDATE_DELAY / 1000.0)
			addEntity(new Cloud(this));
	}
	
	/**	Adds an Entity to this game.
		@param e The entity to add
	*/
	public void addEntity(Entity e) {
		entities.add(e);
	}

	/**	Marks this GameState as having completed the level.
	*/
	public void finishLevel() {
		finished = true;
	}

	/**	Returns true if the current level has been completed.
		@return True if the current level has been completed
	*/
	public boolean isFinished() {
		return finished;
	}

	/**	Returns the time played.
		@return a long repesenting the amount of time played in milliseconds
	*/
	public long getTime() {
		return time;
	}

	/**	Returns the current Level.
		@return the current level
	*/
	public Level getLevel() {
		return level;
	}

	/**	Returns the current Player.
		@return the current player
	*/
	public Player getPlayer() {
		return player;
	}

	/**	Returns an ArrayList of Entities in this game, not including the player.
		@return An ArrayList of Entities in this game
	*/
	public ArrayList<Entity> getEntities() {
		return entities;
	}
}