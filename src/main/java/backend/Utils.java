package main.java.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper methods for database access
 */
public class Utils {
	public static List<Map<String,String>> select (String sql, String[] params) throws SQLException {
		Connection conn = ConnectionWrapper.getInstance().getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.clearParameters();
		for (int i = 0; i < params.length; i++) {
			// Why is this non-zero-indexed...?
			stmt.setString(i + 1, params[i]);
		}
		ResultSet resultSet = stmt.executeQuery();

        List<Map<String,String>> results = new ArrayList<>();
		while (resultSet.next()) {
            int cols = resultSet.getMetaData().getColumnCount();
            Map<String,String> row = new HashMap<>();
            for(int i = 0; i < cols; i++){
                row.put(resultSet.getMetaData().getColumnLabel(i + 1), resultSet.getString(i + 1));
            }
            results.add(row);
        }
		stmt.close();
		return results;
	}

	/**
	 * Runs a query that doesn't return any rows (insert, update, drop, create, etc)
	 * @param sql the sql command
	 * @param params a list of values, which replace '?'s in the query
	 * @throws SQLException if the query fails
	 */
	public static void insert(String sql, String[] params) throws SQLException {
		try {
		Connection conn = ConnectionWrapper.getInstance().getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.clearParameters();
		for (int i = 0; i < params.length; i++) {
			// If the param is an empty string, replace it with null so not null
			// constraints will still trigger
			stmt.setString(i + 1, params[i] != null && params[i].length() == 0 ? null : params[i]);
		}
		stmt.executeUpdate();
		stmt.close();
		}
		catch (java.sql.SQLIntegrityConstraintViolationException e)
		{
			throw new SQLException();
		}
	}


    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    public static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }
    
    public static String formatDay(Calendar day) {
    	return (new SimpleDateFormat("yyyy-MM-dd")).format(day.getTime());
    }

	public static Calendar parseDay(String date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime((new SimpleDateFormat("yyyy-MM-dd")).parse(date));
		return calendar;
	}

	public static String formatTime(Calendar time) {
    	return (new SimpleDateFormat("HH:mm")).format(time.getTime());
	}

	public static Calendar parseTime(String time) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime((new SimpleDateFormat("HH:mm")).parse(time));
		return calendar;
	}

	public static Calendar addBusinessDay(Calendar instance) {
		do {
			instance.add(Calendar.DATE, 1);
		} while (instance.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
				|| instance.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
		return instance;
	}
	
	public static Calendar addBusinessDays(Calendar instance, int days) {
		for (int i = 0; i < days; i++) {
			instance = addBusinessDay(instance);
		}
		return instance;
	}

	public static int getDifferenceInBusinessDays(Calendar dateInitial, Calendar dateActual) {
		// set time fields to 0 so they don't interfere with comparison
		try {
			dateActual = parseDay(formatDay(dateActual));
		} catch (ParseException e) { }
		int i = 0;
		while (dateInitial.compareTo(dateActual) < 0) {
			i++;
			addBusinessDay(dateInitial);
		}
		return i;
	}
}
