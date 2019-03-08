package main.java.ui;

import main.java.backend.Authentication;
import main.java.entities.Customer;

import java.sql.SQLException;

public class EmployeeViewCustomerProfile extends MenuPage {

	@Override
	public void run(Context context) {
		String email = promptUser(context, "Customer Email: ", Authentication::isCustomer);
		try {
			Customer customer = new Customer(email);
			customer.load();
			// Application flow doesn't mention checking the cid, so just continue
			context.getSessionData().put("selectedCustomerID", customer.getCustomerID());
			context.setPage(new CustomerViewProfile());
		} catch (SQLException | IndexOutOfBoundsException e) {
			e.printStackTrace();
			context.setPage(new EmployeeViewCustomerProfile());
		}
	}
}
