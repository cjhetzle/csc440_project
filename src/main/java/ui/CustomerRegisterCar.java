package main.java.ui;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.function.Function;

import main.java.entities.ServiceAppointment;
import main.java.backend.Utils;
import main.java.entities.Vehicle;

public class CustomerRegisterCar extends MenuPage {

	@Override
	public void run(Context context) throws SQLException {
		String license;
		String mileage;
		String dateServiced;
		try {
			Function<String,Boolean> dateValid = (String input) -> {
				try {
					Utils.parseDay(input);
					return true;
				} catch (ParseException e) {
					return false;
				}
			};
			Function<String,Boolean> isInteger = (String input) -> {
				try {
					return Integer.parseInt(input) >= 0;
				} catch (NumberFormatException e) {
					return false;
				}
			};

			license = promptUser(context, "License plate: ", val -> val.length() > 0 && val.length() <= 8);
			String datePurchased = promptUser(context, "Purchase date (yyyy-mm-dd): ", dateValid);
			// Only allow registering vehicles with support makes and models
			String make = "";
			String model = "";
			boolean validMakeModel = false;
			while (!validMakeModel) {
				make = promptUser(context, "Make: ", null);
				model = promptUser(context, "Model: ", null);
				validMakeModel = Vehicle.hasValidMakeModel(make, model);
				if (!validMakeModel) {
					System.out.println("That make and model is unsupported. Please try again.");
				}
			}
			String year = promptUser(context, "Year: ", isInteger);
			mileage = promptUser(context, "Current mileage: ", isInteger);
			dateServiced = promptUser(
				context, "Last service date (yyyy-mm-dd) (optional): ", val -> val.length() == 0 || dateValid.apply(val)
			);

			menu(context, new String[] {"Register", "Cancel"}, new Page[] {null, new CustomerLanding()});

			if (context.getPage() instanceof CustomerLanding) {
				return;
			}
			Vehicle vehicle = new Vehicle(license, make, model, year, datePurchased,
					context.getSessionData().get("userId"));
			vehicle.save();
		} catch (SQLException | NumberFormatException e) {
			System.out.println("Invalid input: " + e.getMessage());
			run(context);
			return;
		}

		try {
			if (dateServiced.length() == 0) {
				dateServiced = Utils.formatDay(Calendar.getInstance());
			}
			ServiceAppointment lastAppt = new ServiceAppointment(license, dateServiced);
			lastAppt.setMileage(mileage);
			lastAppt.insert();
		} catch (SQLException | NumberFormatException e) {
			System.out.println("Invalid input: " + e.getMessage());
			Utils.insert("DELETE FROM Vehicle WHERE license = ?;", new String[] {license});
			run(context);
			return;
		}
		context.setPage(new CustomerLanding());
	}

}
