package main.java.ui;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.lang.StringBuffer;

import main.java.backend.Utils;
import main.java.backend.Warranty;

public class CustomerInvoices extends MenuPage {

	@Override
	public void run(Context context) throws SQLException {
		//get users customerID
		String customerID = context.getSessionData().get("userId");
		
		//query Vehicle and Customer to get the license Numbers that the user is associated with
		List<Map<String,String>> licenseQuery = Utils.select("SELECT license FROM Vehicle WHERE customerID = ?", 
															 new String[] {customerID});
		//loop through each license number 
		int isize = licenseQuery.size();
		for (int i = 0; i < isize; i++) {
			Map<String,String> licenseTuple = licenseQuery.get(i);
			//query Service, Schedules, and BillableUnit for all row information where the serviceID's are the same
			//and the license number is one that belongs to the user
			List<Map<String,String>> queryResults = 
				Utils.select("SELECT s.dateScheduled, sv.serviceID, s.timeSlot, b.timeReq, sv.name, e.name as mechanic"
						+ " FROM Service sv, Schedules s, BillableUnit b, Employee e "
						   + "WHERE sv.serviceID = s.service "
						   + "AND e.eid = s.mechanic "
					       + "AND sv.serviceID = b.serviceID "
					       + "AND s.dateScheduled <= CURDATE() "
					       + "AND s.license = ?"
					       + "ORDER BY s.dateScheduled DESC, s.timeSlot ASC;",
					       new String[] {licenseTuple.get("license")});
			
			//for each license plate number, loop through and print out all required servicing information
			String lastDate = "";
			int jsize = queryResults.size();
			for (int j = 0; j < jsize; j++) {
				Map<String,String> queryTuple = queryResults.get(j);
				if (!queryTuple.get("dateScheduled").equals(lastDate)) {
					// Every time the value for date changes, we are on a new service appointment
					// That means we need to check for diagnostic fee
					Map<String,String> fault = Utils.select("SELECT f.* FROM ServiceAppointment sa " + 
							"LEFT JOIN Fault f " + 
							"ON f.name = sa.fault " + 
							"WHERE sa.license = ? " + 
							"AND sa.apptDate = ?;", new String[] {
									licenseTuple.get("license"),
									queryTuple.get("dateScheduled")
							}).get(0);
					if (fault.get("name") != null) {
						// Need to invoice a diagnostic fee
						System.out.println("Service ID: -");
						System.out.println("\tService Date: " + queryTuple.get("dateScheduled"));
						System.out.println("\tService Type: Diagnostic (" + fault.get("name") + ")");
						System.out.println("\tTotal Service Cost: $" + fault.get("diagnosticFee") + ".00");
					}
				}
				System.out.println("Service ID: " + queryTuple.get("serviceID"));
				System.out.println("\tService Start Date/Time: " + queryTuple.get("dateScheduled") 
								 + " " + queryTuple.get("timeSlot"));
				//converting time string to total minutes, adding the time requirement minutes(given in fractions of hours)
				//and converting back to a time string
				String[] timeParts = queryTuple.get("timeSlot").split(":");
				int totalMinutes = Integer.parseInt(timeParts[0]) * 60 + Integer.parseInt(timeParts[1]);
				double timeReqMinutes = Double.parseDouble(queryTuple.get("timeReq")) * 60;
				totalMinutes += (int) timeReqMinutes;
				timeParts[0] = Integer.toString(totalMinutes / 60);
				timeParts[1] = (totalMinutes % 60 == 0) ? "00" : Integer.toString(totalMinutes % 60);
				StringBuffer time = new StringBuffer();
				if (totalMinutes < 600)
					time.append("0");
				time.append(timeParts[0]);
				time.append(":");
				time.append(timeParts[1]);
				System.out.println("\tService End Date/Time: " + queryTuple.get("dateScheduled") + " " + time);
				System.out.println("\tLicense Plate: " + licenseTuple.get("license"));
				System.out.println("\tService Type: " + queryTuple.get("name"));
				System.out.println("\tMechanic Name: " + queryTuple.get("mechanic"));
				double serviceCost = Warranty.getServiceCost(
						queryTuple.get("serviceID"),
						queryTuple.get("dateScheduled"),
						licenseTuple.get("license"));
				System.out.println("\tTotal Service Cost: $" + serviceCost);
				// Skip over all repeats of this same service
				int k = j + 1;
				for ( ; k < queryResults.size() 
						&& queryResults.get(k).get("serviceID").equals(queryTuple.get("serviceID"))
						&& queryResults.get(k).get("dateScheduled").equals(queryTuple.get("dateScheduled")); k++);
				j = k - 1;
			}
		}
		menu(context, new String[] {"Go Back"}, new Page[] {new CustomerLanding()});
		
	}

}
