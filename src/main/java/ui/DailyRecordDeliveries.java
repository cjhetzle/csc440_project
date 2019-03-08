package main.java.ui;

import main.java.entities.PartOrder;

import java.sql.SQLException;
import java.util.function.Function;

public class DailyRecordDeliveries extends MenuPage {

	@Override
	public void run(Context context) {
		int choice = menu(context,
			new String[] {"Enter Order IDs (CSV)", "Go Back"},
			new Page[] {null, new ReceptionistLanding()});

		if (choice == 1) {
			String cid = context.getSessionData().get("cid");
			Function<String,Boolean> checkIfPending = (String input) -> {
				if (input.length() == 0) {
					return true;
				}
				String[] splitInput = input.split(",");
				for (String orderId : splitInput) {
					if (!PartOrder.isPendingOrder(orderId, cid)) {
						return false;
					}
				}
				return true;
			};
			String orderIDs = promptUser(context,
				"Enter a comma-separated list of orderIDs to mark as delivered:\n", checkIfPending);
			try {
				if (orderIDs.length() > 0) {
					PartOrder.updateStatuses(orderIDs, "complete");
				}
				PartOrder.checkForDelays(cid);
				System.out.println("Successfully updated statuses and inventory!");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Failed to update status or inventory.");
			}
		}
		context.setPage(new ReceptionistLanding());
	}
}
