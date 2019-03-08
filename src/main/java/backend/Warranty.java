package main.java.backend;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Warranty {

	public static double getServiceCost(String svcid, String date, String license) throws SQLException {
		// Get the last time (if applicable) this car had this service performed
		List<Map<String,String>> last = Utils.select("SELECT b.chargeRate, b.timeReq, w.duration, " + 
				"(DATE_ADD(s.dateScheduled, INTERVAL w.duration MONTH) >= ?) as warrantied " + 
				"FROM Schedules s " + 
				"JOIN BillableUnit b " + 
				"ON b.serviceID = s.service " + 
				"LEFT JOIN Warranty w " + 
				"ON w.service = s.service " + 
				"WHERE s.license = ? " + 
				"AND s.dateScheduled < ? " + 
				"AND s.service = ? " + 
				"ORDER BY s.dateScheduled DESC " + 
				"LIMIT 1;", new String[] {date, license, date, svcid});
		if (last.size() == 0) {
			// Service has never been performed before. Labor is complimentary.
			return getPartsCost(svcid);
		} else {
			if (last.get(0).get("duration") != null && last.get(0).get("warrantied").equals("1")) {
				// under warranty
				return 0;
			} else {
				// parts + labor
				return getPartsCost(svcid) + Double.parseDouble(last.get(0).get("timeReq"))
				* Double.parseDouble(last.get(0).get("chargeRate"));
			}
		}
	}

	private static double getPartsCost(String svcid) throws SQLException {
		try {
			return Double.parseDouble(Utils.select("SELECT SUM(c.qty * pd.unitPrice) as partsCost  " + 
					"FROM Consumes c " + 
					"JOIN PartDatabase pd " + 
					"ON pd.partID = c.partID " + 
					"WHERE c.serviceID = ?;", new String[] {svcid}).get(0).get("partsCost"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}

}
