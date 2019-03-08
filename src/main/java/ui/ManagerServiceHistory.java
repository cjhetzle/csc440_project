package main.java.ui;

import main.java.backend.ServiceHistory;
import main.java.backend.Utils;
import main.java.entities.Schedule;

import java.sql.SQLException;
import java.util.List;

public class ManagerServiceHistory extends MenuPage {

	@Override
	public void run(Context context) {
		try {
			String cid = Utils.select(
				"SELECT cid FROM Employee WHERE eid=?",
				new String[] {context.getSessionData().get("userId")}
			).get(0).get("cid");
			List<Schedule> history = ServiceHistory.getManagerServiceHistoryForServiceCenter(cid);
			CustomerServiceHistory.printServiceHistory(history);
		} catch (SQLException e) {
			System.out.println("Invalid service history");
			e.printStackTrace();
			context.setPage(new HomePage());
			return;
		}
		menu(context, new String[] {"Go Back"}, new Page[] {new ManagerLanding()});
	}
}
