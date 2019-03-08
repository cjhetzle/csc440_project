package main.java.backend;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * All helper methods that involve users and authentication
 */
public class Authentication {

    /**
     * Get a user's role if it exists
     *
     * @param userId For customers: email address. For employees: eid.
     * @param password the password
     * @return "customer" || "manager" || "receptionist" || "mechanic"
     */
	public static String getRole(String userId, String password) {
		try {
			List<Map<String,String>> rows = Utils.select(
				"SELECT 'customer' AS role FROM Customer WHERE EXISTS "
				+ "(SELECT * FROM Customer WHERE emailAddress = ? AND password = ?) "
				+ "UNION SELECT role FROM Employee WHERE eid = ? AND password = ?",
				new String[] {userId, password, userId, password}
			);
			if (rows.size() == 1) {
				return rows.get(0).get("role");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isCustomer(String email) {
	    try {
			List<Map<String,String>> rows = Utils.select(
				"SELECT 1 FROM Customer WHERE emailAddress = ?",
				new String[] {email}
			);
			return rows.size() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
    }

	public static void createUser(String name, String email, String address,
            String phoneNum, String password) throws SQLException {
        Utils.insert(
            "INSERT INTO Customer VALUES (default,?,?,?,?,?)",
            new String[] {name, email, address, phoneNum, password}
        );
	}

}
