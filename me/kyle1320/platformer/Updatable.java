package me.kyle1320.platformer;

/**	An interface that specifies that something can be updated, or changes over time.
	@author Kyle Cutler
	@version 1/1/14
*/
public interface Updatable {
	/** Useful in classes that implement this interface, this is the time that should pass between each update.
	*/
	static final int UPDATE_DELAY = 10;

	/**	Updates the object.
	*/
	public void update();
}