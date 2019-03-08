package main.java.ui;

import java.sql.SQLException;

import main.java.backend.Authentication;
import main.java.entities.Customer;
import main.java.entities.Employee;

public class LoginPage extends MenuPage {
	@Override
	public void run(Context context) throws SQLException {
		String userId = promptUser(context, "User ID: ", null);
		String pass = promptUser(context, "Password: ", null);
		menu(context, new String[] {"Sign-In", "Go Back"}, new Page[] {null, new HomePage()});
		// Only call tryLogin if they picked the "Log In" option
		if (context.getPage() == null) {
			String role = Authentication.getRole(userId, pass);
			if (role != null) {
				context.getSessionData().put("role", role);
				if (role.equals("customer")) {
					context.setPage(new CustomerLanding());
					Customer customer = new Customer(userId);
					customer.load();
					context.getSessionData().put("userId", customer.getCustomerID());
					context.getSessionData().put("cid", customer.getCid());
					return;
				} else if (role.equals("receptionist")) {
					context.setPage(new ReceptionistLanding());
					context.getSessionData().put("userId", userId);
					Employee e = new Employee(userId);
					e.load();
					context.getSessionData().put("cid", e.getCid());
					return;
				} else if (role.equals("manager")) {
					context.setPage(new ManagerLanding());
					context.getSessionData().put("userId", userId);
					Employee e = new Employee(userId);
					e.load();
					context.getSessionData().put("cid", e.getCid());
					return;
				}
			}
			System.out.println("User ID or password was invalid. Please try again.");
			this.run(context);
		}
	}

}
