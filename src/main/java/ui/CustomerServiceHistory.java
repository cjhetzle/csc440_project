package main.java.ui;

import main.java.backend.ServiceHistory;
import main.java.entities.Schedule;

import java.sql.SQLException;
import java.util.List;

public class CustomerServiceHistory extends MenuPage {

	@Override
	public void run(Context context) {
		String license = promptUser(
			context, "License Plate (or '0' to cancel): ",
			val -> (val.equals("0") || ServiceHistory.isCustomersCar(context.getSessionData().get("userId"), val))
		);
		if (license.equals("0")) {
			context.setPage(new CustomerService());
			return;
		}
		try {
			List<Schedule> history = ServiceHistory.getServiceHistoryForCar(license);
			CustomerServiceHistory.printServiceHistory(history);
		} catch (SQLException e) {
			// This shouldn't happen
			System.out.println("Invalid service history data");
			e.printStackTrace();
			context.setPage(new HomePage());
			return;
		}
		menu(context, new String[] {"Go Back"}, new Page[] {new CustomerService()});
	}

	/**
	 * Prints a list of Schedules. Also combines adjacent Schedules if they
	 * have the same license and serviceID
	 * @param history List<Schedule> to print
	 */
	public static void printServiceHistory(List<Schedule> history) {
		System.out.format(
			"|%-10s|%-8s|%-50s|%-20s|%-16s|%-16s|%-8s|\n",
			"Service ID", "License", "Service Type", "Mechanic Name", "Start", "End", "Status"
		);
		for (int i = 0; i < history.size(); i++) {
			Schedule sched = history.get(i);
			System.out.format(
				"|%-10s|%-8s|%-50s|%-20s|%-15s",
				sched.getServiceID(), sched.getLicense(), sched.getServiceName(),
				sched.getMechanicName(), sched.getDateScheduled() + " " + sched.getTimeSlot()
			);
			// Skip to the last record with the same dateScheduled, serviceID, and license to find
			// when the service was/will be completed
			String startTime = sched.getDateScheduled() + " " + sched.getTimeSlot();
			while (i + 1 < history.size()
					&& history.get(i + 1).getDateScheduled().equals(sched.getDateScheduled())
					&& history.get(i + 1).getServiceID().equals(sched.getServiceID())
					&& history.get(i + 1).getLicense().equals(sched.getLicense())
			) {
				i++;
			}
			sched = history.get(i);
			String endTime = sched.getDateScheduled() + " " + sched.getNextTimeSlot();
			System.out.format(
				"|%-15s|%-8s|\n",
				endTime, sched.getStatus(startTime, endTime)
			);
		}
	}
}
