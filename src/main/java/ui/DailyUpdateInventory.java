package main.java.ui;

import java.sql.SQLException;

import main.java.backend.Utils;
import main.java.entities.PartInventory;

public class DailyUpdateInventory extends MenuPage {

	@Override
	public void run(Context context) throws SQLException {
		String cid = context.getSessionData().get("cid");
		PartInventory.updateParts(cid);
		PartInventory.checkParts(cid);
		System.out.println("Task ran successfully.");
		menu(context, new String[] {"Go Back"}, new Page[] {new ReceptionistLanding()});
	}
}
