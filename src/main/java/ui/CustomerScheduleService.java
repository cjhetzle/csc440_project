package main.java.ui;

import java.sql.SQLException;
import java.util.function.Function;

import main.java.backend.Utils;
import main.java.entities.Customer;
import main.java.entities.Vehicle;

public class CustomerScheduleService extends MenuPage {

	@Override
	public void run(Context context) throws SQLException {
		String userID = context.getSessionData().get("userId");
		Function<String,Boolean> carExists = (license) -> {
			if (license == null || license.length() < 0) {
				System.out.println("License is required.");
				return false;
			}
			Boolean exists = false;
			try {
				Vehicle vehicle = new Vehicle(license);
				vehicle.load();
				exists = vehicle.getCustomerId().equals(userID);
			} catch (SQLException | IndexOutOfBoundsException e) {
				// Do nothing
			}
			if (!exists) {
				System.out.println("Customer does not have a car registered with that license plate.");
			}
			return exists;
		};
		Function<String,Boolean> inputInteger = (input) -> {
			if (input == null || input.length() < 0) {
				System.out.println("Mileage is required.");
				return false;
			}
			try {
				Integer.parseInt(input);
				return true;
			} catch (NumberFormatException e) {
				System.out.println("Mileage should be an integer.");
				return false;
			}
		};
		Customer customer = new Customer(null);
		customer.setcustomerID(userID);
		try {
			customer.load();
		} catch (SQLException e1) {
			// Shouldn't be possible to get here
		}
		Function<String,Boolean> validMechanic = (name) -> {
			if (name == null || name.length() == 0) {
				return true;
			} else {
				Boolean exists = false;
				try {
					exists = Utils.select("SELECT * from Employee WHERE role = ? AND name = ? AND cid = ?",
							new String[] {"mechanic", name, customer.getCid()}).size() > 0;
				} catch (SQLException e) {
					// Do nothing
				}
				if (!exists) {
					System.out.println("No mechanic with that name at your service center.");
					return false;
				}
				return exists;
			}
		};

		String license = promptUser(context, "License Plate: ", null);
		String mileage = promptUser(context, "Current Mileage: ", null);
		String mechanicName = promptUser(context, "Mechanic Name: ", null);
		
		menu(context, new String[] {"Schedule Maintenance", "Schedule Repair", "Go Back"},
				new Page[] {new ScheduleMaintenancePage1(), new ScheduleRepairPage1(), new CustomerService()});
		
		if (context.getPage() instanceof CustomerService) {
			return;
		}
		
		if (!carExists.apply(license) || !inputInteger.apply(mileage) || !validMechanic.apply(mechanicName)) {
			menu(context, new String[] {"Go Back", "Try Again"},
					new Page[] {new CustomerService(), new CustomerScheduleService()});
			return;
		}
		
		String mechanic = null;
		if (mechanicName != null && mechanicName.length() > 0) {
			mechanic = Utils.select("SELECT * from Employee WHERE role = ? AND name = ? AND cid = ?",
					new String[] {"mechanic", mechanicName, customer.getCid()}).get(0).get("eid");
		}
		context.getSessionData().put("license", license);
		context.getSessionData().put("mileage", mileage);
		context.getSessionData().put("mechanic", mechanic);
	}

}
