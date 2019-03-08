package main.java.ui;

import java.sql.SQLException;

import main.java.entities.Customer;

public class ReceptionistInvoices extends CustomerInvoices {

	@Override
	public void run(Context context) {
		String email = promptUser(context, "Customer email address: ", null);
		try {
			Customer customer = new Customer(email);
			customer.load();
			String receptionistID = context.getSessionData().get("userId");
			context.getSessionData().put("userId", customer.getCustomerID());
			super.run(context);
			context.getSessionData().put("userId", receptionistID);
			context.setPage(new ReceptionistLanding());
		}
		catch (SQLException | IndexOutOfBoundsException e){
			System.out.println("Invalid customer email address.");
			run(context);
			return;
		}

	}

}
