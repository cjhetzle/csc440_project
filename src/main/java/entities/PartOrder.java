package main.java.entities;

import main.java.backend.Utils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class PartOrder implements Entity {

	String orderID;
	String dateOrdered;
	String dateExpected;
	String dateArrived;
	String partID;
	String quantity;
	String sourceFrom;
	String centerTo;
	String status;
	
    public PartOrder() {
		super();
	}

	public PartOrder(String orderID) {
		super();
		this.orderID = orderID;
	}

	public PartOrder(String orderID, String dateOrdered, String dateExpected, String dateArrived, String partID,
			String quantity, String sourceFrom, String centerTo, String status) {
		super();
		this.orderID = orderID;
		this.dateOrdered = dateOrdered;
		this.dateExpected = dateExpected;
		this.dateArrived = dateArrived;
		this.partID = partID;
		this.quantity = quantity;
		this.sourceFrom = sourceFrom;
		this.centerTo = centerTo;
		this.status = status;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getDateOrdered() {
		return dateOrdered;
	}

	public void setDateOrdered(String dateOrdered) {
		this.dateOrdered = dateOrdered;
	}

	public String getDateExpected() {
		return dateExpected;
	}

	public void setDateExpected(String dateExpected) {
		this.dateExpected = dateExpected;
	}

	public String getDateArrived() {
		return dateArrived;
	}

	public void setDateArrived(String dateArrived) {
		this.dateArrived = dateArrived;
	}

	public String getPartID() {
		return partID;
	}

	public void setPartID(String partID) {
		this.partID = partID;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getSourceFrom() {
		return sourceFrom;
	}

	public void setSourceFrom(String sourceFrom) {
		this.sourceFrom = sourceFrom;
	}

	public String getCenterTo() {
		return centerTo;
	}

	public void setCenterTo(String centerTo) {
		this.centerTo = centerTo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static boolean isPendingOrder(String orderID, String cid) {
        try {
            List<Map<String,String>> result = Utils.select(
                "SELECT 1 FROM PartOrders WHERE orderID = ? AND status='pending' AND centerTo = ?",
                new String[] {orderID,cid}
            );
            return result.size() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void updateStatuses(String orderIDs, String newStatus) throws SQLException {
        Utils.insert(
            // Technically, since I'm already validating orderIDs this shouldn't be vulnerable to SQL injection :)
            "UPDATE PartOrders SET status=?, dateArrived=CURDATE() WHERE orderID IN (" + orderIDs + ")",
            new String[] {newStatus}
        );
        // Then a trigger runs to update the inventory
    }

    /**
     * Check if any pending orders are past due and if so, generate notifications
     */
    public static void checkForDelays (String cid) throws SQLException {
        Utils.insert(
            "UPDATE PartOrders SET status='delayed' "
            + "WHERE status='pending' AND CURDATE() >= dateExpected AND centerTo = ?",
            new String[] {cid}
        );
        // Then a trigger runs to generate a notification
    }

    @Override
    public void load () throws SQLException {
    	List<Map<String,String>> orders = Utils.select(
                "SELECT * FROM PartOrders WHERE orderID = ?;",
                new String[] {this.orderID}
            );
    	Map<String,String> order = orders.get(0);
    	this.dateOrdered = order.get("dateOrdered");
    	this.dateExpected = order.get("dateExpected");
    	this.dateArrived = order.get("dateArrived");
    	this.partID = order.get("partID");
    	this.quantity = order.get("quantity");
    	this.sourceFrom = order.get("sourceFrom");
    	this.centerTo = order.get("centerTo");
    	this.status = order.get("status");
    }

    @Override
    public void save () throws SQLException {
        throw new NotImplementedException();
    }

    @Override
    public void insert () throws SQLException {
        Utils.insert("INSERT INTO PartOrders VALUES (?,?,?,?,?,?,?,?,?);", 
        		new String[] {this.orderID, this.dateOrdered, this.dateExpected,
        				this.dateArrived, this.partID, this.quantity, this.sourceFrom,
        				this.centerTo, this.status});
    }
}
