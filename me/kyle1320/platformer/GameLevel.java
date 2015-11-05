package me.kyle1320.platformer;

/**	An enumeration of levels that can be loaded from and played in the game.
	@author Kyle Cutler
	@version 12/31/13
*/
public enum GameLevel {
	tutorial("Tutorial", "/levels/tutorial.txt"),
	level1("Level 1", "/levels/level1.txt"),
	test("Test Level", "/levels/test.txt"),
	test2("Test Level 2", "/levels/test2.txt"),
	test3("Test Level 3", "/levels/test3.txt");

	private String name;
	private String fileName;

	/**	Create a new GameLevel with the given level name and file name
		@param name The name for the level
		@param fileName The file path from which to load the level
	*/
	private GameLevel(String name, String fileName) {
		this.name = name;
		this.fileName = fileName;
	}

	/**	Creates a new level and returns it
		@return The newly created level
	*/
	public Level getLevel() {
		return new Level(this, fileName);
	}

	/**	Returns the level name
		@return The level name
	*/
	public String toString() {
		return name;
	}
}