package main.java.ui;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FirstExample {

    static final String jdbcURL = "jdbc:mysql://localhost:3306/cars?serverTimezone=America/New_York";

    public static void main(String[] args) {
        // Load the driver
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}

	    String user = "acme";
	    String passwd = "croesus";
        Connection conn = null;
        Statement stmt = null;
        ResultSet resultSet = null;
        try {
			// Get a connection from the first driver in the
			// DriverManager list that recognizes the URL jdbcURL
			conn = DriverManager.getConnection(jdbcURL, user, passwd);
	
			// Create a statement object that will be sending your
			// SQL statements to the DBMS
			stmt = conn.createStatement();
	
			// Make sure the COFFEES table isn't present
			try {
				stmt.executeUpdate("DROP TABLE COFFEES");
				System.out.println("Dropped the old COFFEES table");
			} catch (SQLException e) {
				// Continue
			}
			
			System.out.println("Creating new COFFEES table");
			stmt.executeUpdate("CREATE TABLE COFFEES " +
				   "(COF_NAME VARCHAR(32), SUP_ID INTEGER, " +
				   "PRICE FLOAT, SALES INTEGER, TOTAL INTEGER)");
	
			// Populate the COFFEES table
			stmt.executeUpdate(
				"INSERT INTO COFFEES VALUES " + 
				"('Colombian', 101, 7.99, 0, 0), " + 
				"('French_Roast', 49, 8.99, 0, 0), " + 
				"('Espresso', 150, 9.99, 0, 0), " +
				"('Colombian_Decaf', 101, 8.99, 0, 0), " +
				"('French_Roast_Decaf', 49, 9.99, 0, 0)"
			);
	
			// Get data from the COFFEES table
			resultSet = stmt.executeQuery("SELECT COF_NAME, PRICE FROM COFFEES");

			// Now resultSet contains the rows of coffees and prices from
			// the COFFEES table. To access the data, use the method
			// NEXT to access all rows in resultSet, one row at a time
	
			while (resultSet.next()) {
			    String s = resultSet.getString("COF_NAME");
			    float n = resultSet.getFloat("PRICE");
			    System.out.println(s + "   " + n);
			}
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
        	try { resultSet.close(); } catch (Exception e) { /* ignored */ }
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
            try { conn.close(); } catch (Exception e) { /* ignored */ }
	    }
    }
}