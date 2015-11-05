package me.kyle1320.platformer;

/**	Represents a score in the score database.
	@author Kyle Cutler
	@version 1/1/14
*/
public final class Score {
	private int id;
	private String name;
	private int score;
	private String levelName;

	/**	Creates a new Score with the given id, name, score, and level name. Meant to be used by the Score DAO.
		@param id The id given to this score in the database
		@param name The name of the player who achieved this score
		@param score The value of the score achieved
		@param levelName The name of the level on which this score was achieved
	*/
	public Score(int id, String name, int score, String levelName) {
		this.id = id;
		this.name = name;
		this.score = score;
		this.levelName = levelName;
	}

	/**	Creates a new Score with the given name, score, and level name.
		@param name The name of the player who achieved this score
		@param score The value of the score achieved
		@param levelName The name of the level on which this score was achieved
	*/
	public Score(String name, int score, String levelName) {
		this(0, name, score, levelName);
	}

	/**	Sets the name of the player who achieved this score.
		@param name The player name
	*/
	public void setName(String name) {
		this.name = name;
	}

	/**	Sets the value of this score.
		@param score The score value
	*/
	public void setScore(int score) {
		this.score = score;
	}

	/**	Sets the level name of this score.
		@param levelName the new level name
	*/
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	/**	Returns the id of this score. Meant to be used by the Score DAO.
		@return The id of this score
	*/
	public int getId() {
		return id;
	}

	/**	Returns the name of the player who achieved this score.
		@return The player name
	*/
	public String getName() {
		return name;
	}

	/**	Returns the value of this score.
		@return The score value
	*/
	public int getScore() {
		return score;
	}

	/**	Returns the level name of this score.
		@return the level name
	*/
	public String getLevelName() {
		return levelName;
	}
}