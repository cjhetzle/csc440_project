package main.java.entities;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import main.java.backend.Utils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class PartInventory implements Entity {

	String partID;
	String cid;
	String quantity;
	String minQnty;
	String minOrderQnty;
	
	public PartInventory(String partID, String cid) {
		super();
		this.partID = partID;
		this.cid = cid;
	}

	public PartInventory(String partID, String cid, String quantity, String minQnty, String minOrderQnty) {
		super();
		this.partID = partID;
		this.cid = cid;
		this.quantity = quantity;
		this.minQnty = minQnty;
		this.minOrderQnty = minOrderQnty;
	}

	public String getPartID() {
		return partID;
	}

	public void setPartID(String partID) {
		this.partID = partID;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getMinQnty() {
		return minQnty;
	}

	public void setMinQnty(String minQnty) {
		this.minQnty = minQnty;
	}

	public String getMinOrderQnty() {
		return minOrderQnty;
	}

	public void setMinOrderQnty(String minOrderQnty) {
		this.minOrderQnty = minOrderQnty;
	}

	@Override
	public void load() throws SQLException {
        Map<String,String> partInventory = Utils.select("SELECT * FROM PartInventory WHERE cid = ? AND partID = ?;", 
        		new String[] {this.cid, this.partID}).get(0);
        this.setMinOrderQnty(partInventory.get("minOrderQnty"));
        this.setMinQnty(partInventory.get("minQnty"));
        this.setQuantity(partInventory.get("quantity"));
	}

	@Override
	public void save() throws SQLException {
        throw new NotImplementedException();
	}

	@Override
	public void insert() throws SQLException {
        throw new NotImplementedException();
	}
	
	// For a given part, determines how many of that part have already been ordered
	// If enough have already been ordered, returns the date they will arrive by in dateBy
	public static int getQtyOnOrder(String cid, Map<String,String> part, int qtyDesired, PartOrder dateBy) throws SQLException {
		List<Map<String,String>> orders = Utils.select("SELECT quantity, dateExpected, status FROM PartOrders " + 
				"WHERE centerTo = ? " + 
				"AND partID = ? " + 
				"AND status <> 'complete' " + 
				"ORDER BY dateExpected;", 
				new String[] {cid, part.get("partID")});
		int qtyOnOrder = 0;
		boolean qtySufficient = false;
		for (Map<String,String> order : orders) {
			qtyOnOrder += Integer.parseInt(order.get("quantity"));
			if (qtyOnOrder >= qtyDesired && !qtySufficient) {
				// case 2: enough parts have already been ordered
				if (order.get("status").equals("pending")) {
					dateBy.setDateExpected(order.get("dateExpected"));
				} else {
					Calendar day = Calendar.getInstance();
					do {
						day.add(Calendar.DAY_OF_MONTH, 1);
					} while (day.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
							day.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
					dateBy.setDateExpected(Utils.formatDay(day));
				}
				qtySufficient = true;
			}
		}
		return qtyOnOrder;
	}
	
	public static Calendar placeOrder(String cid, Map<String,String> part, int qtyNeeded) throws SQLException {
		PartInventory partInventory = new PartInventory(part.get("partID"), cid);
		partInventory.load();
		int minOrderQnty = Integer.parseInt(partInventory.getMinOrderQnty());
		if (minOrderQnty > qtyNeeded) {
			qtyNeeded = minOrderQnty;
		}
		// 2. Check if any of the other Acme centers has the part in sufficient quantity such that
		// ordering the part from that center does not cause its inventory to fall below the minimum threshold.
		Calendar day = Calendar.getInstance();
		String todayDate = Utils.formatDay(day);
		List<Map<String,String>> centers = Utils.select(
				"SELECT partID, cid, (qtyInStock - minQnty - SUM(qtyConsumed)) as available " + 
				"	FROM (SELECT pi.partID, pi.cid, pi.minQnty, pi.quantity as qtyInStock, c2.qty as qtyConsumed " + 
				"		FROM PartInventory pi " + 
				"		LEFT JOIN Consumes c2 " + 
				"		ON c2.partID = pi.partID " + 
				"		LEFT JOIN Schedules s " + 
				"		ON s.service = c2.serviceID " + 
				"        LEFT JOIN Employee e" + 
				"        ON e.eid = s.mechanic" + 
				"		WHERE pi.partID = ? " + 
				"		AND pi.cid <> ? " + 
				"        AND e.cid = pi.cid" + 
				"		AND s.dateScheduled IS NOT NULL " + 
				"		AND s.dateScheduled > ?" + 
				"		GROUP BY s.dateScheduled, s.service) as x " + 
				"	GROUP BY partID, cid" + 
				"    HAVING available >= ?" + 
				"	UNION " + 
				"	SELECT pi.partID, pi.cid, (pi.quantity - pi.minQnty) as available " + 
				"	FROM PartInventory pi " + 
				"	WHERE pi.partID = ? " + 
				"	AND pi.cid <> ? " + 
				"    AND pi.quantity - ? >= minQnty " +
				"	AND NOT EXISTS (SELECT * FROM Schedules s " + 
				"	LEFT JOIN Consumes c2 " + 
				"	ON s.service = c2.serviceID " + 
				"    LEFT JOIN Employee e " + 
				"    ON e.eid = s.mechanic " + 
				"	WHERE c2.partID = pi.partID " + 
				"    AND e.cid = pi.cid " + 
				"	AND s.dateScheduled >= ?) " +
				"   ORDER BY available DESC;", 
			new String[] {part.get("partID"), cid, todayDate, Integer.toString(qtyNeeded),
					part.get("partID"), cid, Integer.toString(qtyNeeded), todayDate});
		// 3. If one or more such centers are found, an order should be placed to obtain the part from
		// the center with the largest available quantity for that part. 
		PartDatabase partDatabase = new PartDatabase(part.get("partID"));
		partDatabase.load();
		PartOrder newOrder = new PartOrder();
		newOrder.setCenterTo(cid);
		newOrder.setQuantity(Integer.toString(qtyNeeded));
		newOrder.setDateOrdered(todayDate);
		newOrder.setPartID(part.get("partID"));
		newOrder.setStatus("pending");
		if (centers.size() > 0) {
			newOrder.setSourceFrom(centers.get(0).get("cid"));
			do {
				day.add(Calendar.DAY_OF_MONTH, 1);
			} while (day.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
					day.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
			newOrder.setDateExpected(Utils.formatDay(day));
		} else {
			// 4. If no such center is found, the part should be ordered from its corresponding distributor.
			newOrder.setSourceFrom(partDatabase.getDistID());
			for (int i = 0; i < Integer.parseInt(partDatabase.getDistDelay()); i++) {
				do {
					day.add(Calendar.DAY_OF_MONTH, 1);
				} while (day.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
						day.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
			}
			newOrder.setDateExpected(Utils.formatDay(day));
		}
		newOrder.insert();
		return day;
	}

	public static Calendar getOrderDate(List<Map<String, String>> parts, String cid) throws SQLException {
		Calendar orderDate = null;
		for (Map<String,String> part : parts) {
			Calendar partAvailabilityDate = null;
			int qtyRequired = Integer.parseInt(part.get("qtyRequired"));
			int qtyInStock = Integer.parseInt(part.get("qtyInStock"));
			int qtyBooked = Integer.parseInt(part.get("qtyBooked"));
			if (qtyInStock - qtyBooked >= qtyRequired) {
				//case 1: part is in inventory in sufficient quantity
				continue;
			}
			PartOrder existingOrder = new PartOrder();
			int qtyOnOrder = getQtyOnOrder(cid, part, qtyRequired + qtyBooked - qtyInStock, existingOrder);
			if (existingOrder.getDateExpected() == null) {
				// case 3: order still must be placed
				partAvailabilityDate = placeOrder(cid, part, qtyRequired - qtyInStock + qtyBooked - qtyOnOrder);
			} else {
				try {
					partAvailabilityDate = Utils.parseDay(existingOrder.getDateExpected());
				} catch (ParseException e) { e.printStackTrace(); }
			}
			if (orderDate == null || orderDate.compareTo(partAvailabilityDate) < 0) {
				orderDate = partAvailabilityDate;
			}
		}
		return orderDate;
	}

	// Checks parts to make sure all are above minimum quantity
	public static void checkParts(String cid) throws SQLException {
		List<Map<String,String>> partsToOrder = Utils.select("SELECT * FROM PartInventory " + 
				"WHERE cid = ? " + 
				"AND quantity < minQnty", new String[] {cid});
		for (Map<String,String> part : partsToOrder) {
			int qtyNeeded = Integer.parseInt(part.get("minQnty")) - Integer.parseInt(part.get("quantity"));
			int qtyOnOrder = getQtyOnOrder(cid, part, qtyNeeded, new PartOrder());
			if (qtyOnOrder < qtyNeeded) {
				placeOrder(cid, part, qtyNeeded - qtyOnOrder);
			}
		}
	}
	
	public static void updateParts(String cid) throws SQLException {
		Utils.insert("UPDATE PartInventory pi " + 
				"INNER JOIN (SELECT partID, SUM(qty) as qtyConsumed " + 
				"FROM (SELECT c.partID, c.qty, s.service " + 
				"FROM Schedules s " + 
				"LEFT JOIN Consumes c " + 
				"ON c.serviceID = s.service " + 
				"LEFT JOIN Employee e " + 
				"ON e.eid = s.mechanic " + 
				"WHERE s.dateScheduled = CURDATE() " + 
				"AND e.cid = ? " + 
				"GROUP BY s.license, s.service) as x " + 
				"GROUP BY partID) as y " + 
				"ON pi.partID = y.partID " + 
				"SET pi.quantity = (pi.quantity - y.qtyConsumed) " + 
				"WHERE pi.cid = ?;", 
				new String[] { cid,	cid	});
	}

}
