package main.java.ui;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import main.java.backend.Utils;

public class NotificationsPage extends MenuPage {

	@Override
	public void run(Context context) throws SQLException {
		// Retrieves list of notifications
		List<Map<String,String>> notifications = Utils.select(
				"SELECT n.notificationID, n.dateGenerated, n.orderID, po.sourceFrom, sc.name, " + 
				"po.dateExpected, po.dateArrived " + 
				"FROM Notifications n " + 
				"INNER JOIN PartOrders po " + 
				"ON n.orderID = po.orderID " + 
				"LEFT JOIN ServiceCenter sc " + 
				"ON sc.cid = po.sourceFrom " + 
				"WHERE po.centerTo = ? " + 
				"ORDER BY n.dateGenerated DESC, n.notificationID;", new String[] {
						context.getSessionData().get("cid")
				});
		
		if (notifications.size() == 0) {
			System.out.println("No notifications to display");
			menu(context, new String[] {"Go back"}, new Page[] {new ManagerLanding()});
		} else {
			System.out.format(
					"|%-17s|%-19s|%-10s|%-20s|%-24s|%-19s|\n",
					"Notification ID", "Notification Date", "Order ID",
					"Supplier Name", "Expected Delivery Date", "Delayed by (days)"
				);
			List<String> orderIDs = new ArrayList<String>();
			for(Map<String,String> tuple : notifications) {
				String source = tuple.get("name") != null ? tuple.get("name") : tuple.get("sourceFrom");
				try {
					Calendar dateExpected = Utils.parseDay(tuple.get("dateExpected"));
					Calendar dateArrived = (tuple.get("dateArrived") != null) ?
							Utils.parseDay(tuple.get("dateArrived")) :
							Utils.addBusinessDay(Calendar.getInstance());
					String daysDelay = Integer.toString(Utils.getDifferenceInBusinessDays(dateExpected, dateArrived));
					System.out.format(
							"|%-17s|%-19s|%-10s|%-20s|%-24s|%-19s|\n",
							tuple.get("notificationID"), tuple.get("dateGenerated"), tuple.get("orderID"),
							source, tuple.get("dateExpected"), daysDelay
						);
					orderIDs.add(tuple.get("orderID"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			menu(context, new String[] {"Order ID", "Go back"}, new Page[] {new NotificationDetails(), new ManagerLanding()});
			if (context.getPage() instanceof NotificationDetails) {
				Function<String,Boolean> validOrderChoice = (String input) -> {return orderIDs.contains(input);};
				context.getSessionData().put("orderID", promptUser(context, "Enter Order ID: ", validOrderChoice));
			}
		}
	}

}
