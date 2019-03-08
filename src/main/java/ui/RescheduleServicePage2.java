package main.java.ui;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import main.java.backend.Utils;
import main.java.entities.Schedules;
import main.java.entities.ServiceAppointment;

public class RescheduleServicePage2 extends MenuPage {

	@Override
	public void run(Context context) throws SQLException {
		menu(context, new String[] {"Reschedule Date", "Go Back"},
				new Page[] {new CustomerService(), new RescheduleServicePage1()});
		if (context.getPage() instanceof RescheduleServicePage1) {
			return;
		}
		String license = context.getSessionData().get("license");
		String apptDate = context.getSessionData().get("apptDate");
		ServiceAppointment toReschedule = new ServiceAppointment(license, apptDate);
		toReschedule.load();
		try {
			List<Schedules> firstOption = Schedules.rescheduleAppointment(
					toReschedule,
					Utils.parseDay(toReschedule.getApptDate()),
					context.getSessionData().get("cid"),
					toReschedule.getFault() == null);
			List<Schedules> secondOption = Schedules.rescheduleAppointment(
					toReschedule,
					Utils.parseDay(firstOption.get(0).getDateScheduled()),
					context.getSessionData().get("cid"),
					toReschedule.getFault() == null);
			
			int choice = menu(context, new String[] {
					Schedules.formatOption(firstOption),
					Schedules.formatOption(secondOption)
			}, new Page[] {new CustomerService(), new CustomerService()});
			
			List<Schedules> selected;
			if (choice == 1) {
				selected = firstOption;
			} else {
				selected = secondOption;
			}
			toReschedule.delete();
			toReschedule.setApptDate(selected.get(0).getDateScheduled());
			toReschedule.insert();
			for (Schedules s : selected) {
				s.insert();
			}
		} catch (NumberFormatException | ParseException e) {
			e.printStackTrace();
		}
	}

}
