package main.java.ui;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import main.java.backend.Utils;
import main.java.entities.Schedules;
import main.java.entities.ServiceAppointment;

public class ScheduleRepairPage2 extends MenuPage {

	@Override
	public void run(Context context) throws SQLException {
		menu(context, new String[] {"Repair on Date", "Go Back"},
				new Page[] {new ScheduleRepairPage2(), new ScheduleRepairPage1()});
		if (context.getPage() instanceof ScheduleRepairPage1) {
			return;
		}
		
		
		try {
			List<Schedules> firstOption = Schedules.scheduleRepair(
					Calendar.getInstance(),
					context.getSessionData().get("cid"),
					context.getSessionData().get("fault"),
					context.getSessionData().get("mechanic"),
					context.getSessionData().get("license"));
			
			List<Schedules> secondOption = Schedules.scheduleRepair(
					Utils.parseDay(firstOption.get(0).getDateScheduled()),
					context.getSessionData().get("cid"),
					context.getSessionData().get("fault"),
					context.getSessionData().get("mechanic"),
					context.getSessionData().get("license"));
			
			int choice = menu(context, new String[] {
					Schedules.formatOption(firstOption),
					Schedules.formatOption(secondOption)
			}, new Page[] {new CustomerScheduleService(), new CustomerScheduleService()});
			
			List<Schedules> selected;
			if (choice == 1) {
				selected = firstOption;
			} else {
				selected = secondOption;
			}
			ServiceAppointment sa = new ServiceAppointment(
					selected.get(0).getLicense(),
					selected.get(0).getDateScheduled(),
					context.getSessionData().get("fault"),
					context.getSessionData().get("mechanic"),
					context.getSessionData().get("mileage")
					);
			sa.insert();
			for (Schedules s : selected) {
				s.insert();
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
