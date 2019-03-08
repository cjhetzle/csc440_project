package main.java.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a single page of the UI containing multiple subpages
 */
public abstract class Page {
	/**
	 * Run logic of the page, print the menu, and return whatever page they pick
	 * 
	 * @return the next page, or null if the program should quit
	 * @throws SQLException 
	 */
	public abstract void run(Context context) throws SQLException;

}
