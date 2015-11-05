package me.kyle1320.platformer;

/**	Driver class for the entire game. Creates a new GameWindow and displays it.
	@author Kyle Cutler
	@version 1/1/14
*/
public class PlatformGame {
	/**	Runs the game.
		@param args A String array of program arguments. Ignored.
	*/
	public static void main(String[] args) {
		// load the derby driver so we can connect to the database
		DBConnect.loadDriver();

		GameWindow window = new GameWindow();
		window.display();
	}
}