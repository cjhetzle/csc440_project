package main.java.ui;

import main.java.entities.Customer;

import java.sql.SQLException;

public class CustomerUpdateProfile extends MenuPage {

	@Override
	public void run(Context context) {
	    Customer customer = new Customer();
	    try {
	        customer.setcustomerID(context.getSessionData().get("userId"));
	        customer.load();
        } catch (SQLException | IndexOutOfBoundsException e) {
	        e.printStackTrace();
	        context.setPage(new CustomerProfile());
	        return;
        }
		int selection = menu(context,
			new String[]{"Name", "Address", "Phone Number", "Password", "Go Back"},
			new Page[]{null, null, null, null, new CustomerProfile()});


        switch (selection) {
            case 1: {
            	String newValue = promptUser(context, "New Name: ", val -> val.length() <= 50);
                customer.setName(newValue);
                break;
            }
            case 2: {
                String newStreet = promptUser(context, "New Street: ", val -> val.length() <= 50);
                String newCity = promptUser(context, "New City: ", val -> val.length() <= 25);
                String newState = promptUser(context, "New State: ", val -> val.length() == 2);
                String newZip = promptUser(context, "New Zip: ", val -> val.length() <= 5);
                customer.setStreet(newStreet);
                customer.setCity(newCity);
                customer.setState(newState);
                customer.setZip(newZip);
                break;
            }
            case 3: {
                String newValue = promptUser(context, "New Phone Number: ", val -> val.length() <= 10);
                customer.setPhone(newValue);
                break;
            }
            case 4: {
                String newValue = promptUser(context, "New Password: ", val -> val.length() <= 100);
                customer.setPassword(newValue);
                break;
            }
            default: {
                // They picked "Go Back", so do nothing
                return;
            }
        }

        try {
            customer.save();
        } catch (SQLException e) {
            System.out.println("Invalid input");
            this.run(context);
        }
        context.setPage(new CustomerProfile());
	}

}
