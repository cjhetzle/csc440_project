package main.java.ui;

import java.sql.SQLException;

import main.java.entities.Customer;

public class ReceptionistRescheduleService extends RescheduleServicePage1 {

	@Override
	public void run(Context context) {
		String email = promptUser(context, "Customer email address: ", null);
		try {
			Customer customer = new Customer(email);
			customer.load();
			String receptionistID = context.getSessionData().get("userId");
			String receptionistCID = context.getSessionData().get("cid");
			context.getSessionData().put("userId", customer.getCustomerID());
			context.getSessionData().put("cid", customer.getCid());
			super.run(context);
			while (!(context.getPage() instanceof CustomerService) &&
					!(context.getPage() instanceof RescheduleServicePage1)) {
				context.getPage().run(context);
			}
			context.getSessionData().put("userId", receptionistID);
			context.getSessionData().put("cid", receptionistCID);
			if (context.getPage() instanceof CustomerService) {
				context.setPage(new ReceptionistLanding());
			} else {
				context.setPage(new ReceptionistRescheduleService());
			}
		} catch (SQLException | IndexOutOfBoundsException e) {
			System.out.println("Invalid customer email address.");
			menu(context, new String[] {"Go Back", "Try Again"},
					new Page[] {new ReceptionistLanding(), this});
		}
	}

}
