package main.java.ui;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Calendar;

import main.java.backend.Utils;

public class NewOrderPage extends MenuPage {

	public void run(Context context) throws SQLException {
		//getting centerID from the currently logged in manager
		String logInUserID = context.getSessionData().get("userId");
		List<Map<String,String>> queryResults = Utils.select("SELECT cid FROM Employee WHERE eid = ?", new String[] {logInUserID});
		String cid = queryResults.get(0).get("cid");
		
		//ensure that the partID entered, is one that exists in the Part Database
		String partID = "";
		boolean validPartID = false;
		while (validPartID == false) {
			partID = promptUser(context, "Part ID: ", null);
			queryResults = Utils.select("SELECT * FROM PartDatabase WHERE partID = ?", new String[] {partID});
			if (queryResults.size() > 0)
				validPartID = true;
			else
				System.out.println("Part ID does not exist. Please enter a valid part ID.");
		}
		//ensure that the quantity is at least the minimum order quantity for this part
		String quantity = "";
		boolean validQuantity = false;
		while (validQuantity == false){
			quantity = promptUser(context, "Quantity: ", null);
			queryResults = Utils.select("SELECT * FROM PartInventory WHERE partID = ? && cid = ?", new String[] {partID, cid});
			try {
				if (Integer.parseInt(quantity) >= Integer.parseInt(queryResults.get(0).get("minOrderQnty")))
					validQuantity = true;
				else
					System.out.println("The minimum order quantity for this part is "
									   + Integer.parseInt(queryResults.get(0).get("minOrderQnty")) + ".");
			}
			catch (NumberFormatException e) {
				System.out.println("Please enter a valid integer quantity.");
			}
		}
		
		//menu gives the option to place the order or discard it and go back to manager landing page
		int choice = menu(context, new String[] {"Place Order", "Go Back"}, new Page[] {new OrdersPage(), new OrdersPage()});
		
		//insert the order into the PartOrders table only if the user has selected "Place Order"
		if (choice == 1) {
			
			String dateArrived = null;
			String status = "pending";
			//orderID is one more than the ID of the last order placed
			queryResults = Utils.select("SELECT * FROM PartOrders", new String[] {});
			String orderID = Integer.toString((queryResults.size() + 1));
			//query PartDatabase in order to obtain distID and distDelay
			queryResults = Utils.select("SELECT distID, distDelay FROM PartDatabase WHERE partID = ?", new String[] {partID});
			String sourceFrom = queryResults.get(0).get("distID");
			int distDelay = Integer.parseInt(queryResults.get(0).get("distDelay"));
			//use the calendar to get the todays date for the order, and add the distributors shipping time to get the expected 
			//delivery date
			Calendar cal = Calendar.getInstance();
			String dateOrdered = (Integer.toString(cal.get(Calendar.YEAR)) + "/" + Integer.toString(cal.get(Calendar.MONTH) + 1) + "/"
								+ Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));
			cal = Utils.addBusinessDays(cal, distDelay);
			String dateExpected = (Integer.toString(cal.get(Calendar.YEAR)) + "/" + Integer.toString(cal.get(Calendar.MONTH) + 1) + "/"
					+ Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));
			
			Utils.insert("INSERT INTO PartOrders VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?)",
						 new String[] {dateOrdered, dateExpected, dateArrived, partID, quantity, sourceFrom, cid, status});
			System.out.println("Order ID " + orderID + " has been placed. The expected fulfillment date is " + dateExpected + ".\n");
		}
	}
}
