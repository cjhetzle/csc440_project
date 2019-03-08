package main.java.ui;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import main.java.backend.Utils;
import main.java.backend.Warranty;

public class ManagerInvoices extends CustomerInvoices {

	@Override
	public void run(Context context) throws SQLException {
		//Query Database for all customers who's cid matches that of the logged in manager
		String managerID = context.getSessionData().get("userId");
		List<Map<String,String>> queryResults = 
			Utils.select("SELECT b.serviceID, c.name as cname, s.dateScheduled, " + 
					"MIN(s.timeSlot) as timeSlot, s.license, sv.name as sname, " + 
					"e.name as mname, b.timeReq, b.chargeRate " + 
					"FROM Customer c " + 
					"JOIN Schedules s " + 
					"ON s.license IN (SELECT v.license " + 
					"FROM Vehicle v " + 
					"WHERE v.customerID = c.customerID) " + 
					"JOIN Employee e " + 
					"ON e.eid = s.mechanic " + 
					"JOIN BillableUnit b " + 
					"ON b.serviceID = s.service " + 
					"JOIN Service sv " + 
					"ON sv.serviceID = s.service " + 
					"WHERE c.cid IN (SELECT cid " + 
					"FROM Employee " + 
					"WHERE eid = ?) " + 
					"AND s.dateScheduled <= CURDATE() " + 
					"GROUP BY c.customerID, s.license, s.dateScheduled, s.service " + 
					"ORDER BY c.customerID, s.license, s.dateScheduled DESC, timeSlot ASC;",
					new String[] {managerID});
				
		//for each license plate number, loop through and print out all required servicing information
		String lastDate = "";
		int size = queryResults.size();
		for (int k = 0; k < size; k++) {
			Map<String,String> queryTuple = queryResults.get(k);
			if (!queryTuple.get("dateScheduled").equals(lastDate)) {
				// Every time the value for date changes, we are on a new service appointment
				// That means we need to check for diagnostic fee
				Map<String,String> fault = Utils.select("SELECT f.* FROM ServiceAppointment sa " + 
						"LEFT JOIN Fault f " + 
						"ON f.name = sa.fault " + 
						"WHERE sa.license = ? " + 
						"AND sa.apptDate = ?;", new String[] {
								queryTuple.get("license"),
								queryTuple.get("dateScheduled")
						}).get(0);
				if (fault.get("name") != null) {
					// Need to invoice a diagnostic fee
					System.out.println("Service ID: -");
					System.out.println("\tCustomer Name: " + queryTuple.get("cname"));
					System.out.println("\tService Date: " + queryTuple.get("dateScheduled"));
					System.out.println("\tLicense Plate: " + queryTuple.get("license"));
					System.out.println("\tService Type: Diagnostic (" + fault.get("name") + ")");
					System.out.println("\tTotal Service Cost: $" + fault.get("diagnosticFee") + ".00");
				}
			}
			System.out.println("Service ID: " + queryTuple.get("serviceID"));
			System.out.println("\tCustomer Name: " + queryTuple.get("cname"));
			System.out.println("\tService Start Date/Time: " + queryTuple.get("dateScheduled") 
							 + " " + queryTuple.get("timeSlot"));
			//converting time string to total minutes, adding the time requirement minutes(given in fractions of hours)
			//and converting back to a time string
			String[] timeParts = queryTuple.get("timeSlot").split(":");
			int totalMinutes = Integer.parseInt(timeParts[0]) * 60 + Integer.parseInt(timeParts[1]);
			double timeReqMinutes = Double.parseDouble(queryTuple.get("timeReq")) * 60;
			totalMinutes += (int) timeReqMinutes;
			timeParts[0] = Integer.toString(totalMinutes / 60);
			timeParts[1] = totalMinutes % 60 == 0 ? "00" : Integer.toString(totalMinutes % 60);
			StringBuffer time = new StringBuffer();
			if (totalMinutes < 600)
				time.append("0");
			time.append(timeParts[0]);
			time.append(":");
			time.append(timeParts[1]);
			System.out.println("\tService End Date/Time: " + queryTuple.get("dateScheduled") + " " + time);
			System.out.println("\tLicense Plate: " + queryTuple.get("license"));
			System.out.println("\tService Type: " + queryTuple.get("sname"));
			System.out.println("\tMechanic Name: " + queryTuple.get("mname"));
			double serviceCost = Warranty.getServiceCost(
					queryTuple.get("serviceID"),
					queryTuple.get("dateScheduled"),
					queryTuple.get("license"));
			System.out.println("\tTotal Service Cost: $" + serviceCost);
		}
		menu(context, new String[] {"Go Back"}, new Page[] {new ManagerLanding()});

	}

}
