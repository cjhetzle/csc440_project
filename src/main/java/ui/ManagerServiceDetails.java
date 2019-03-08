package main.java.ui;

import main.java.backend.Utils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ManagerServiceDetails extends MenuPage {

		public void run(Context context) throws SQLException {
			//getting the manager's cid to make sure he can only see his own centers orders
			String logInUserID = context.getSessionData().get("userId");
			String sqlSelect = "SELECT cid, name FROM Employee WHERE eid = ?";
			String[] selectParams = {logInUserID};
			List<Map<String,String>> approvedCars, modelServices, basicServices;

			//querying from partDatabase and partOrders
			approvedCars = Utils.select("SELECT SUBSTRING(name, 12) as name FROM Service WHERE name LIKE '%Service A%';",
					new String[] {});

			System.out.println("\n========== ManagerServiceDetails ==========");
			// for loop starts here
			int i = 0, size = approvedCars.size();
			for (; i < size; ++i) {
				String makeModel = approvedCars.get(i).get("name");
				modelServices = Utils.select("SELECT serviceID, name FROM (SELECT * FROM Service WHERE name LIKE ?) as m WHERE name LIKE '%Service%'", new String[]{"%"+makeModel+"%"});

				for (Map<String, String> entry: modelServices){
					String id = entry.get("serviceID");
					System.out.println("\n" + entry.get("name") + "\n--------------------------");
					System.out.println("Miles: " + Utils.select("SELECT mileage FROM ByMileage WHERE serviceID = ?", new String[]{id}).get(0).get("mileage"));
					basicServices = Utils.select("SELECT name FROM Service WHERE name NOT LIKE '%Service%' AND serviceID IN (SELECT included FROM Includes Where includer = ?)", new String[]{id});
					for (Map<String, String> srv: basicServices) {
						System.out.println("\t" + srv.get("name"));
					}
				}
			}
			System.out.println("");
			menu(context, new String[] {"Go Back"}, new Page[] {new ManagerLanding()});
		}
}
