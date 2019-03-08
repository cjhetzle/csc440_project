package main.java.ui;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import main.java.backend.Utils;
import main.java.entities.ByMileage;
import main.java.entities.PartDatabase;
import main.java.entities.PartInventory;
import main.java.entities.PartOrder;

public class ScheduleMaintenancePage1 extends MenuPage {

	@Override
	public void run(Context context) throws SQLException {
		menu(context, new String[] {"Find Service Date", "Go Back"},
				new Page[] {new ScheduleMaintenancePage2(), new CustomerScheduleService()});
		if (context.getPage() instanceof ScheduleMaintenancePage2) {
			// What service does this car need?
			ByMileage scheduledService = ByMileage.getScheduledService(
					context.getSessionData().get("license"),
					context.getSessionData().get("mileage")
					);
			if (scheduledService == null) {
				System.out.println("No valid maintenance found for that mileage.");
				context.setPage(new CustomerScheduleService());
				return;
			}
			context.getSessionData().put("bymileage", scheduledService.getServiceID());
			// What parts does that service consume?
			String cid = context.getSessionData().get("cid");
			List<Map<String,String>> parts = ByMileage.getPartQtys(cid, scheduledService.getServiceID());
			Calendar orderDate = PartInventory.getOrderDate(parts, cid);
			if (orderDate != null) {
				SimpleDateFormat format = new SimpleDateFormat("MMM dd yyyy");
				System.out.println("Insufficient parts. Try again after " + format.format(orderDate.getTime()));
				context.setPage(new CustomerScheduleService());
				return;
			}
		}
	}

}
