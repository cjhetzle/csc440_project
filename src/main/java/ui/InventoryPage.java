package main.java.ui;

import java.sql.SQLException;
import main.java.backend.Utils;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class InventoryPage extends MenuPage {

	@Override
	public void run(Context context) throws SQLException {
		//getting the manager's cid to make sure he can only see his own inventory
		String logInUserID = context.getSessionData().get("userId");
		String sqlSelect = "SELECT cid FROM Employee WHERE eid = ?";
		String[] selectParams = {logInUserID};
		List<Map<String,String>> queryResults = Utils.select(sqlSelect, selectParams);
		String cid = queryResults.get(0).get("cid");
		
		//querying from partDatabase and partInventory to get partID, part name, quantity, unit price, minimum quantity threshold
		queryResults = Utils.select("SELECT *"
								  + "FROM PartDatabase, PartInventory "
								  + "WHERE PartInventory.cid = ? && PartDatabase.partID = PartInventory.partID", new String[] {cid});
		//print out all queried results in distinguishable format
		for (int i = 0; i < queryResults.size(); i++) {
			if (Integer.parseInt(queryResults.get(0).get("quantity")) > 0) {
				System.out.println("Part ID: " + queryResults.get(i).get("partID"));
				System.out.println("\tPart Name: " + queryResults.get(i).get("name"));
				System.out.println("\tQuantity: " + queryResults.get(i).get("quantity"));
				System.out.println("\tUnit Price: " + queryResults.get(i).get("unitPrice"));
				System.out.println("\tMinimum Quantity Threshold: " + queryResults.get(i).get("minQnty"));
				System.out.println("\tMinimum Order Threshold: " + queryResults.get(i).get("minOrderQnty") + "\n");
			}
		}
		//Only option is to return back to the ManagerLanding page
		menu(context, new String[] {"Go Back"}, new Page[] {new ManagerLanding()});
	}

}
