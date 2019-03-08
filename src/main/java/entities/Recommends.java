package main.java.entities;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import main.java.backend.Utils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Recommends implements Entity {

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

	public static List<Map<String, String>> getPartQtys(String cid, String fault, String license) throws SQLException {
		Calendar today = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String todayDate = format.format(today.getTime());
		return Utils.select("SELECT parts.partID, parts.qtyRequired, parts.qtyInStock, IFNULL(partsBooked.qtyBooked, 0) as qtyBooked " + 
				"FROM (SELECT pi.partID, SUM(c1.qty) as qtyRequired, pi.quantity as qtyInStock " + 
				"FROM Vehicle v " + 
				"LEFT JOIN Recommends r " + 
				"ON r.make = v.make " + 
				"AND r.model = v.model " + 
				"LEFT JOIN Consumes c1 " + 
				"ON c1.serviceID = r.serviceID " + 
				"LEFT JOIN PartInventory pi " + 
				"ON pi.partID = c1.partID " + 
				"WHERE v.license = ? " + 
				"AND r.fault = ? " + 
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
				new String[] {license, fault, cid, cid, todayDate});
	}

	public static List<Map<String, String>> getIncludedServices(String fault, String license) throws SQLException {
		return Utils.select(
				"SELECT bu.serviceID, (4 * timeReq) as slots " + 
						"FROM Vehicle v " + 
						"LEFT JOIN Recommends r " + 
						"ON v.make = r.make " + 
						"AND v.model = r.model " + 
						"LEFT JOIN BillableUnit bu " + 
						"ON bu.serviceID = r.serviceID " + 
						"WHERE r.fault = ? " + 
						"AND v.license = ?;", new String[] {fault, license});
	}
	
	public static long timeSlotsRequired(String fault, String license) throws NumberFormatException, SQLException {
		// How much time is required?
		double timeReq = Double.parseDouble(((Utils.select("SELECT SUM(timeReq) as time " + 
				"FROM Vehicle v " + 
				"LEFT JOIN Recommends r " + 
				"ON v.make = r.make " + 
				"AND v.model = r.model " + 
				"LEFT JOIN BillableUnit bu " + 
				"ON bu.serviceID = r.serviceID " + 
				"WHERE r.fault = ? " + 
				"AND v.license = ?;", new String[] {fault, license})).get(0)).get("time"));
		return Math.round(timeReq * 4);
	}

}
