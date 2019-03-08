package main.java.ui;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

import main.java.entities.ServiceAppointment;

public class RescheduleServicePage1 extends MenuPage {

	@Override
	public void run(Context context) throws SQLException {
		List<ServiceAppointment> upcoming = ServiceAppointment.getUpcomingForCustomer(context.getSessionData().get("userId"));
		if (upcoming.size() == 0) {
			System.out.println("No upcoming service appointments.");
			menu(context, new String[] {"Go Back"},
					new Page[] {new CustomerService()});
			return;
		}
		System.out.format(
				"|%-15s|%-12s|%-14s|%-14s|%-50s|\n",
				"License Plate", "Service ID", "Service Date", "Service Type", "Service Details"
			);
		for (int i = 0; i < upcoming.size(); i++) {
			ServiceAppointment appt = upcoming.get(i);
			System.out.format(
					"|%-15s|%-12s|%-14s|%-14s|%-50s|\n",
					appt.getLicense(), i, appt.getApptDate(), appt.getType(), appt.getDetails()
				);
		}
		menu(context, new String[] {"Pick a service", "Go Back"},
				new Page[] {new RescheduleServicePage2(), new CustomerService()});
		if (context.getPage() instanceof CustomerService) {
			return;
		}
		Function<String,Boolean> validator = (String input) -> {
			int serviceID;
			try {
				serviceID = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				return false;
			}
			if (serviceID < 0 || serviceID >= upcoming.size()) {
				return false;
			}
			return true;
		};
		ServiceAppointment selection = upcoming.get(
				Integer.parseInt(promptUser(context, "Enter a Service ID to select the service to reschedule: ", validator))
						);
		context.getSessionData().put("license", selection.getLicense());
		context.getSessionData().put("apptDate", selection.getApptDate());
	}

}
