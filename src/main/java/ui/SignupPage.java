package main.java.ui;

import java.sql.SQLException;

import main.java.entities.Customer;

public class SignupPage extends MenuPage {

	@Override
	public void run(Context context) {
		Customer newCustomer = new Customer();
		newCustomer.setCid(promptUser(context, "Service Center ID: ", null));
		newCustomer.setEmailAddress(promptUser(context, "Email: ", null));
		newCustomer.setPassword(promptUser(context, "Password: ", null));
		newCustomer.setName(promptUser(context, "Name: ", null));
		newCustomer.setStreet(promptUser(context, "Street: ", null));
		newCustomer.setCity(promptUser(context, "City: ", null));
		newCustomer.setState(promptUser(context, "State: ", null));
		newCustomer.setZip(promptUser(context, "ZIP: ", null));
		newCustomer.setPhone(promptUser(context, "Phone: ", null));

		menu(context, new String[] {"Sign Up", "Go Back"}, new Page[] {new LoginPage(), new HomePage()});
		// Only call createUser if they picked the "Sign Up" option
		if(context.getPage() instanceof LoginPage) {
			try {
				newCustomer.insert();
				System.out.println("User created. Please log in.");
				return;
			} catch(SQLException e) {
				System.out.println("Invalid fields. Please try again.");
			}
			this.run(context);
		}
	}
}
