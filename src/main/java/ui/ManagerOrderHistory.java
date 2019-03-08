package main.java.ui;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import main.java.backend.Utils;

public class ManagerOrderHistory extends MenuPage {

	@Override
	public void run(Context context) throws SQLException {
		//getting the manager's cid to make sure he can only see his own centers orders
		String logInUserID = context.getSessionData().get("userId");
		String sqlSelect = "SELECT cid, name FROM Employee WHERE eid = ?";
		String[] selectParams = {logInUserID};
		List<Map<String,String>> queryResults = Utils.select(sqlSelect, selectParams);
		String cid = queryResults.get(0).get("cid");
		String managerName = queryResults.get(0).get("name");
		
		//querying from partDatabase and partOrders
		queryResults = Utils.select("SELECT * "
								  + "FROM PartDatabase, PartOrders "
								  + "WHERE PartDatabase.partID = PartOrders.partID && PartOrders.centerTo = ?", new String[] {cid});
		
		//print out all queried results in distinguishable format
		for (int i = 0; i < queryResults.size(); i++) {
			System.out.println("Order ID: " + queryResults.get(i).get("orderID"));
			System.out.println("\tDate: " + queryResults.get(i).get("dateOrdered"));
			System.out.println("\tPart Name: " + queryResults.get(i).get("name"));
			System.out.println("\tSupplier Name: " + queryResults.get(i).get("sourceFrom"));
			System.out.println("\tPurchaser Name: " + managerName);
			System.out.println("\tQuantity: " + queryResults.get(i).get("quantity"));
			System.out.println("\tUnit Price: " + queryResults.get(i).get("unitPrice"));
			System.out.println("\tTotal Cost: " + (Integer.parseInt(queryResults.get(i).get("quantity"))
												* Integer.parseInt(queryResults.get(i).get("unitPrice"))));
			System.out.println("\tOrder Status: " + queryResults.get(i).get("status"));
		}
		
		//Only option is to return back to the Orders page
		menu(context, new String[] {"Go Back"}, new Page[] {new OrdersPage()});
	}

}
