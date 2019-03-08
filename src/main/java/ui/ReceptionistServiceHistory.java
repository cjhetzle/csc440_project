package main.java.ui;

import main.java.backend.Authentication;
import main.java.backend.ServiceHistory;
import main.java.entities.Customer;
import main.java.entities.Schedule;

import java.sql.SQLException;
import java.util.List;

public class ReceptionistServiceHistory extends MenuPage {

	@Override
	public void run(Context context) {
		String email = promptUser(context, "Customer email address: ", Authentication::isCustomer);
		Customer customer = new Customer(email);
		try {
			customer.load();
			List<Schedule> history = ServiceHistory.getServiceHistoryForCustomer(
				customer.getCustomerID(), context.getSessionData().get("cid")
			);
			if (history.size() == 0) {
				System.out.println(
					"No service history for this customer at this service center (" + context.getSessionData().get("cid") + ")");
			} else {
				CustomerServiceHistory.printServiceHistory(history);
			}
		} catch (SQLException e) {
			System.out.println("Invalid service history");
			e.printStackTrace();
			context.setPage(new HomePage());
			return;
		}
		menu(context, new String[] {"Go Back"}, new Page[] {new ReceptionistLanding()});
	}
}
