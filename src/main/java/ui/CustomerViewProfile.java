package main.java.ui;

import main.java.entities.Customer;
import main.java.entities.Vehicle;
import main.java.backend.Profile;

import java.sql.SQLException;

public class CustomerViewProfile extends MenuPage {

	@Override
	public void run(Context context) {
	    // Needed to support employees viewing customer profiles
	    String customerID;
		Page backPage;
        if (context.getSessionData().get("role").equals("customer")) {
            customerID = context.getSessionData().get("userId");
            backPage = new CustomerProfile();
        } else {
            customerID = context.getSessionData().get("selectedCustomerID");
            if (context.getSessionData().get("role").equals("receptionist")) {
                backPage = new ReceptionistLanding();
            } else {
                backPage = new ManagerLanding();
            }
        }

		Customer customer;
        try {
            customer = Profile.getCustomerProfile(customerID);
        } catch (SQLException e) {
            e.printStackTrace();
            context.setPage(backPage);
            return;
        }
        System.out.println("Customer ID: " + customer.getCustomerID());
        System.out.println("Name: " + customer.getName());
        System.out.println("Address: " +
                String.join(" ", customer.getStreet(), customer.getCity(), customer.getState(), customer.getZip()));
        System.out.println("Email Address: " + customer.getEmailAddress());
        System.out.println("Phone Number: " + customer.getPhone());
        for (Vehicle v : customer.getVehicles()) {
            System.out.println("Vehicle: ");
            System.out.println("    License Plate: " + v.getLicense());
            System.out.println("    Make: " + v.getMake());
            System.out.println("    Model: " + v.getModel());
            System.out.println("    Year: " + v.getYear());
            System.out.println("    Purchase Date: " + v.getDatePurchased());
        }
        menu(context, new String[] {"Go Back"}, new Page[] {backPage});
	}
}
