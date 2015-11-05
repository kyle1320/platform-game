package me.kyle1320.platformer;

import java.sql.*;

/**	Used for connecting to the score database.
	@author Kyle Cutler
	@version 12/31/13
*/
public final class DBConnect {
	// These don't need to ever be changed
	private static final String DBMS = "derby";
	private static final String DB_NAME = "database/ScoreDB";

	/**	Necessary on some machines, must be called before trying to connect to a database.
		@return True if the driver loaded successfully, false if it failed
	*/
	public static boolean loadDriver() {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver was not found");
			return false;
		}

		return true;
	}

	/**	Creates a new connection to the database and returns it.
		@return A connection to the score database
	*/
	public static Connection getConnection() throws SQLException {
		try {
			return DriverManager.getConnection("jdbc:" + DBMS + ":" + DB_NAME);
		} catch (SQLException e) {
			System.out.println("Unable to connect to the database");
			throw e;
		}
	}
}