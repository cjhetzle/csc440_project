package main.java.ui;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import main.java.backend.Utils;
import main.java.entities.Employee;
import main.java.entities.PartDatabase;
import main.java.entities.PartOrder;

public class NotificationDetails extends MenuPage {

	@Override
	public void run(Context context) throws SQLException {
		PartOrder orderDetails = new PartOrder(context.getSessionData().get("orderID"));
		orderDetails.load();
		System.out.println("Order ID: " + orderDetails.getOrderID());
		System.out.println("Date: " + orderDetails.getDateOrdered());
		PartDatabase partDetails = new PartDatabase(orderDetails.getPartID());
		partDetails.load();
		System.out.println("Part Name: " + partDetails.getName());
		List<Map<String,String>> supplierNames = Utils.select("SELECT name FROM ServiceCenter WHERE cid = ?;", 
				new String[] {orderDetails.getSourceFrom()});
		String supplierName = supplierNames.size() > 0 ? supplierNames.get(0).get("name") : orderDetails.getSourceFrom();
		System.out.println("Supplier Name: " + supplierName);
		Employee manager = new Employee(context.getSessionData().get("userId"));
		manager.load();
		System.out.println("Purchaser Name: " + manager.getName());
		System.out.println("Quantity: " + orderDetails.getQuantity());
		System.out.println("Unit Price: " + partDetails.getUnitPrice());
		System.out.println("Total Cost: " + Integer.toString(
				Integer.parseInt(orderDetails.getQuantity()) * Integer.parseInt(partDetails.getUnitPrice())));
		System.out.println("Order Status: " + orderDetails.getStatus());

		menu(context, new String[] {"Go Back"}, new Page[] {new NotificationsPage()});
	}

}
