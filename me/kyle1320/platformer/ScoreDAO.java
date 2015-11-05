package me.kyle1320.platformer;

import java.sql.*;
import java.util.ArrayList;

/**	Connects to the score database to add and retrieve scores.
	@author Kyle Cutler
	@version 1/1/14
*/
public final class ScoreDAO {
	// Static constants representing column names, these won't change so long as the database doesn't.
	private static final String TABLE_NAME = "Scores";
	private static final String ID_COLUMN = "id";
	private static final String NAME_COLUMN = "name";
	private static final String SCORE_COLUMN = "score";
	private static final String LEVEL_NAME_COLUMN = "level_name";

	/**	Returns an ArrayList of scores in the database up to the given maximum number
		@param max The maximum number of scores the resulting ArrayList should contain
		@return an ArrayList of scores retrieved from the database
	*/
	public static ArrayList<Score> getAllScores(int max) throws SQLException {
		ArrayList<Score> scores = new ArrayList<Score>(max);

		Connection conn = DBConnect.getConnection();
		PreparedStatement stat = conn.prepareStatement("select * from " + TABLE_NAME + " order by " + SCORE_COLUMN + " desc");
		ResultSet result = stat.executeQuery();
		int count = 0;

		while(result.next() && count++ < max){
			Score score = new Score(result.getInt(ID_COLUMN), result.getString(NAME_COLUMN), result.getInt(SCORE_COLUMN), result.getString(LEVEL_NAME_COLUMN));
			scores.add(score);
		}

		conn.close();
		return scores;
	}

	/**	Adds the given score to the database
		@param score The score to add to the database
	*/
	public static void addScore(Score score) throws SQLException {
		Connection conn = DBConnect.getConnection();
		PreparedStatement stat = conn.prepareStatement("insert into " + TABLE_NAME + "(" + NAME_COLUMN + ", " + SCORE_COLUMN + ", " + LEVEL_NAME_COLUMN + ") values(?, ?, ?)");
		
		stat.setString(1, score.getName());
		stat.setInt(2, score.getScore());
		stat.setString(3, score.getLevelName());
		stat.executeUpdate();

		conn.close();
	}
}