package main.java.entities;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import main.java.backend.Utils;
import main.java.ui.Context;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ByMileage implements Entity {

	String serviceID;
	String mileage;
	String make;
	String model;
	List<Map<String,String>> partQtys;
	
	public ByMileage(String serviceID, String mileage, String make, String model) {
		super();
		this.serviceID = serviceID;
		this.mileage = mileage;
		this.make = make;
		this.model = model;
	}

	public String getServiceID() {
		return serviceID;
	}

	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}

	public String getMileage() {
		return mileage;
	}

	public void setMileage(String mileage) {
		this.mileage = mileage;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Override
	public void load() throws SQLException {
        throw new NotImplementedException();

	}

	@Override
	public void save() throws SQLException {
        throw new NotImplementedException();

	}

	@Override
	public void insert() throws SQLException {
        throw new NotImplementedException();

	}

	public static List<Map<String, String>> getIncludedServices(String byMileage) throws SQLException {
		return Utils.select(
				"SELECT bu.serviceID, (4 * timeReq) as slots " + 
				"FROM ByMileage bm " + 
				"LEFT JOIN Includes i1 " + 
				"ON i1.includer = bm.serviceID " + 
				"LEFT JOIN Includes i2 " + 
				"ON i2.includer = i1.included " + 
				"LEFT JOIN Includes i3 " + 
				"ON i3.includer = i2.included " + 
				"LEFT JOIN BillableUnit bu " + 
				"ON bu.serviceID = i3.included " + 
				"OR bu.serviceID = i2.included " + 
				"OR bu.serviceID = i1.included " + 
				"WHERE bm.serviceID = ?;", new String[] {byMileage});
	}

	public static List<Map<String, String>> getPartQtys(String cid, String serviceID) throws SQLException {
		Calendar today = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String todayDate = format.format(today.getTime());
		return Utils.select("SELECT parts.partID, parts.qtyRequired, parts.qtyInStock, IFNULL(partsBooked.qtyBooked, 0) as qtyBooked " + 
				"FROM (SELECT pi.partID, SUM(c1.qty) as qtyRequired, pi.quantity as qtyInStock " + 
				"FROM ByMileage b " + 
				"LEFT JOIN Includes i1 " + 
				"ON i1.includer = b.serviceID " + 
				"LEFT JOIN Includes i2 " + 
				"ON i2.includer = i1.included " + 
				"LEFT JOIN Includes i3 " + 
				"ON i3.includer = i2.included " + 
				"LEFT JOIN Consumes c1 " + 
				"ON c1.serviceID = i3.included " + 
				"OR c1.serviceID = i2.included " + 
				"OR c1.serviceID = i1.included " + 
				"LEFT JOIN PartInventory pi " + 
				"ON pi.partID = c1.partID " + 
				"WHERE b.serviceID = ? " + 
				"AND pi.cid = ? " + 
				"GROUP BY pi.partID) as parts " + 
				"LEFT JOIN (SELECT partID, SUM(qtyBooked) as qtyBooked " + 
				"FROM (SELECT c.partID, c.qty as qtyBooked " + 
				"FROM Schedules s " + 
				"LEFT JOIN Consumes c " + 
				"ON c.serviceID = s.service " + 
				"LEFT JOIN Employee e " + 
				"ON e.eid = s.mechanic " + 
				"WHERE e.cid = ? " + 
				"AND s.dateScheduled >= ? " + 
				"GROUP BY s.license, s.dateScheduled, s.service) as services " + 
				"GROUP BY partID " + 
				") as partsBooked " + 
				"ON parts.partID = partsBooked.partID;",
				new String[] {serviceID, cid, cid, todayDate});
	}

	public static ByMileage getScheduledService(String license, String mileage) {
		try {
			List<Map<String,String>> results = Utils.select("SELECT sa.mileage as lastMileage, b.* " + 
					"FROM Vehicle v " + 
					"LEFT JOIN ServiceAppointment sa " + 
					"ON sa.license = v.license " + 
					"LEFT JOIN ByMileage b " + 
					"ON b.make = v.make " + 
					"AND b.model = v.model " + 
					"AND b.mileage <= (? - sa.mileage) " + 
					"WHERE v.license = ? " + 
					"ORDER BY lastMileage DESC, b.mileage DESC " + 
					"LIMIT 1;",
					new String[] {
							mileage,
							license
					});
			
			Map<String,String> byMileage = results.get(0);
			
			if (byMileage.get("serviceID") == null) {
				return null;
			}
			
			return new ByMileage(
					byMileage.get("serviceID"),
					byMileage.get("mileage"),
					byMileage.get("make"),
					byMileage.get("model"));
		} catch (SQLException | IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public static long timeSlotsRequired(String serviceID) throws NumberFormatException, SQLException {
		// How much time is required?
		double timeReq = Double.parseDouble(((Utils.select("SELECT SUM(timeReq) as time " + 
				"FROM ByMileage bm " + 
				"LEFT JOIN Includes i1 " + 
				"ON i1.includer = bm.serviceID " + 
				"LEFT JOIN Includes i2 " + 
				"ON i2.includer = i1.included " + 
				"LEFT JOIN Includes i3 " + 
				"ON i3.includer = i2.included " + 
				"LEFT JOIN BillableUnit bu " + 
				"ON bu.serviceID = i3.included " + 
				"OR bu.serviceID = i2.included " + 
				"OR bu.serviceID = i1.included " + 
				"WHERE bm.serviceID = ?;", new String[] {serviceID})).get(0)).get("time"));
		return Math.round(timeReq * 4);
	}

}
