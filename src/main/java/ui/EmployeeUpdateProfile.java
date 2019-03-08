package main.java.ui;

import main.java.entities.Employee;

import java.sql.SQLException;

public class EmployeeUpdateProfile extends MenuPage {

	@Override
	public void run(Context context) {
		Employee employee = new Employee(context.getSessionData().get("userId"));
	    try {
	        employee.load();
        } catch (SQLException e) {
	        e.printStackTrace();
	        context.setPage(new EmployeeProfile());
	        return;
        }
		int selection = menu(context,
			new String[]{"Name", "Address", "Email Address", "Phone Number", "Password", "Go Back"},
			new Page[]{null, null, null, null, null, new EmployeeProfile()});

        switch (selection) {
            case 1: {
                String newValue = promptUser(context, "New Name: ", val -> val.length() <= 50);
                employee.setName(newValue);
                break;
            }
            case 2: {
                String newStreet = promptUser(context, "New Street: ", val -> val.length() <= 50);
                String newCity = promptUser(context, "New City: ", val -> val.length() <= 25);
                String newState = promptUser(context, "New State: ", val -> val.length() == 2);
                String newZip = promptUser(context, "New Zip: ", val -> val.length() <= 5);
                employee.setStreet(newStreet);
                employee.setCity(newCity);
                employee.setState(newState);
                employee.setZip(newZip);
                break;
            }
			case 3: {
				String newValue = promptUser(context, "New Email: ", val -> val.length() <= 50);
                employee.setEmailAddress(newValue);
                break;
			}
            case 4: {
                String newValue = promptUser(context, "New Phone Number: ", val -> val.length() <= 10);
                employee.setPhone(newValue);
                break;
            }
            case 5: {
                String newValue = promptUser(context, "New Password: ", val -> val.length() <= 100);
                employee.setPassword(newValue);
                break;
            }
            default: {
                // They picked "Go Back", so do nothing
                return;
            }
        }
        try {
            employee.save();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        context.setPage(new EmployeeProfile());
	}

}
