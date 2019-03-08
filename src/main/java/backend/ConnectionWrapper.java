package main.java.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Singleton database connection wrapper.
 * Makes sure only one is created and closes it automatically (in finalize)
 */
public class ConnectionWrapper {
	private static ConnectionWrapper INSTANCE;
	private Connection connection;
	
	private ConnectionWrapper() {
		String jdbcURL = "jdbc:mysql://localhost:3306/cars?serverTimezone=America/New_York";
		// Load the driver
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}

	    String user = "acme";
	    String passwd = "croesus";
        try {
        	this.connection = DriverManager.getConnection(jdbcURL, user, passwd);
        } catch(SQLException e) {
        	e.printStackTrace();
        }
	}
	
	public Connection getConnection() {
        return this.connection;
    }
	public static ConnectionWrapper getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new ConnectionWrapper();
		}
        return INSTANCE;
	}
	
	/**
	 * Java calls this before garbage collection.
	 * Exceptions in finalize() are ignored, and it's
	 * fine to ignore the exception from close()
	 */
	public void finalize() throws SQLException {
		this.connection.close();
	}
}
