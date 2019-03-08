package main.java.ui;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import main.java.entities.ByMileage;
import main.java.entities.Fault;
import main.java.entities.PartInventory;
import main.java.entities.Recommends;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class ScheduleRepairPage1 extends MenuPage {

	@Override
	public void run(Context context) throws SQLException {
		List<String> options = Fault.getAllFaults();
		options.add("Go Back");
		Page[] pages = new Page[options.size()];
		pages[pages.length - 1] = new CustomerScheduleService();
		int choice = menu(context, options.toArray(new String[0]), pages);
		if (context.getPage() != null) {
			return;
		}
		String fault = options.get(choice - 1);
		context.getSessionData().put("fault", fault);
		String cid = context.getSessionData().get("cid");
		List<Map<String,String>> parts = Recommends.getPartQtys(
				cid,
				fault,
				context.getSessionData().get("license"));
		Calendar orderDate = PartInventory.getOrderDate(parts, cid);
		if (orderDate != null) {
			SimpleDateFormat format = new SimpleDateFormat("MMM dd yyyy");
			System.out.println("Insufficient parts. Try again after " + format.format(orderDate.getTime()));
			context.setPage(new CustomerScheduleService());
			return;
		}
		context.setPage(new ScheduleRepairPage2());
	}

}
