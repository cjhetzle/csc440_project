package main.java.ui;

import java.sql.SQLException;
import java.util.Random;
import main.java.backend.Utils;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class AddNewEmployees extends MenuPage {

	@Override
	public void run(Context context) throws SQLException {
		System.out.println("Input the following Employee Details:");

		//getting centerID from the currently logged in manager
		String logInUserID = context.getSessionData().get("userId");
		String sqlSelect = "SELECT cid FROM Employee WHERE eid = ?";
		String[] selectParams = {logInUserID};
		List<Map<String,String>> queryResults = new ArrayList<>();
		queryResults = Utils.select(sqlSelect, selectParams);
		String cid = queryResults.get(0).get("cid");

		// Prompting for all fields of an employee
		String name = promptUser(context, "Name: ", val -> val.length() > 0 && val.length() <= 50);
		String street = promptUser(context, "Street Address: ", val -> val.length() > 0 && val.length() <= 30);
		String city = promptUser(context, "City: ", val -> val.length() > 0 && val.length() <= 15);
		String state = promptUser(context, "State: ", val -> val.length() > 0 && val.length() == 2);
		String zip = promptUser(context, "Zip Code: ", val -> val.length() > 0 && val.length() == 5);
		String email = promptUser(context, "Email Address: ", val -> val.length() > 0 && val.length() <= 50);
		String phoneNumber = promptUser(context, "Phone Number (xxx.xxx.xxxx): ", val -> val.length() > 0 && val.length() <= 20);
		String role = "";
		String password = "12345678";

		while (!role.equals("mechanic") && !role.equals("receptionist")) {
			role = promptUser(context, "Role (mechanic or receptionist): ", null);
			role = role.toLowerCase();

			if (!role.equals("mechanic") && !role.equals("receptionist"))
				System.out.println("Incorrect selection, please choose mechanic or receptionist");

			if (role.equals("receptionist")) {
				//check to see if the service center already has a receptionist
				//if it does, do not allow another to be employed
				sqlSelect = "SELECT * FROM Employee WHERE cid = ? && role = 'receptionist'";
				selectParams[0] = cid;
				queryResults = Utils.select(sqlSelect, selectParams);
				if (queryResults.size() != 0) {
					System.out.println("This Service Center cannot employ any more receptionists.");
					System.out.println("Please choose a valid role for this service center.");
					role = "";
				}
			}
		}
		//make sure the date given by the user is in a valid Date format
		String startDate = "";
		boolean validDate = false;
		while(validDate == false) {
			startDate = promptUser(context, "Start Date (yyyy-mm-dd): ", null);
			String dateParts[] = startDate.split("-");
			if (dateParts.length == 3 && dateParts[0].length() == 4
				&& dateParts[1].length() == 2 && dateParts[2].length() == 2)
				validDate = true;
			else
				System.out.println("Invalid date, please enter date in the format specified.");
		}

		boolean validPay = false;
		String pay = "";
		while (validPay == false) {
			pay = promptUser(context, "Pay: ", null);
			try {
				Double.parseDouble(pay);
				validPay = true;
			}
			catch (NumberFormatException e) {
				System.out.println("Invalid input for Pay. Please enter a real number.");
			};
		}

		boolean validID = false;
		//randomly generated employee ID as a string
		Random rand = new Random(System.currentTimeMillis());
		String employeeID = "";
		while (!validID) {
			// starting at 100,000,000, ending at 999,999,999 to guarantee 9 digits
			employeeID = Integer.toString(rand.nextInt(900000000) + 100000000);
			queryResults = Utils.select("SELECT * FROM Employee WHERE eid = ?", new String[] {employeeID});
			if (queryResults.size() == 0)
				validID = true;
		}

		int choice = menu(context, new String[] {"Add", "Go Back"}, new Page[] {null, new ManagerLanding()});

		if (choice == 1) {
			//create INSERT sql command to insert this new employee into the database
			//only if they choose to add
			String sql = "INSERT INTO Employee VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			String params[] = {cid, employeeID, name, street, city, state, zip, email, phoneNumber, role,
					startDate, pay, password};
			Utils.insert(sql, params);
			//print out new employees ID to the manager and prompt for the next action
			System.out.println("The new Employee's ID number is " + employeeID + "\n");

			menu(context, new String[] {"Add another employee", "Go Back"}, new Page[] {new AddNewEmployees(), new ManagerLanding()});
		}
	}
}
