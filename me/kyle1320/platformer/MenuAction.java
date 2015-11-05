package me.kyle1320.platformer;

/**	An interface that performs an action in a Menu.
	@author Kyle Cutler
	@version 1/1/14
*/
public interface MenuAction {
	/**	Performs the action specified by the implementation.
		@param parent The GamePanel that holds th Menu this action is being called from. Useful for switching views.
	*/
	public void performAction(GamePanel parent);
}