package me.kyle1320.platformer;

/**	An option, or button, in a Menu.
	@author Kyle Cutler
	@version 1/1/14
*/
public class MenuOption {
	private String text;
	private MenuAction action;

	/**	Creates a new MenuOption with the given text and action.
		@param text The text to display on this option
		@param action The action to perform when this option is clicked
	*/
	public MenuOption(String text, MenuAction action) {
		this.text = text;
		this.action = action;
	}

	/**	Returns this option's text
		@return This option's text
	*/
	public String getText() {
		return text;
	}

	/**	Calls the MenuAction in this option with the specified GamePanel as its parameter
		@param parent The parent to call the action with
	*/
	public void performAction(GamePanel parent) {
		action.performAction(parent);
	}
}