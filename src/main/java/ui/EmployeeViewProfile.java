package main.java.ui;

import main.java.entities.Employee;
import main.java.backend.Profile;

import java.sql.SQLException;

public class EmployeeViewProfile extends MenuPage {

	@Override
	public void run(Context context) {
		String eid = context.getSessionData().get("userId");
		Employee employee;
        try {
            employee = Profile.getEmployeeProfile(eid);
        } catch (SQLException e) {
            e.printStackTrace();
            context.setPage(new HomePage());
            return;
        }
        System.out.println("Employee ID: " + employee.getEid());
        System.out.println("Name: " + employee.getName());
        System.out.println("Address: "+
                String.join(" ", employee.getStreet(), employee.getCity(), employee.getState(), employee.getZip()));
        System.out.println("Email Address: " + employee.getEmailAddress());
        System.out.println("Phone Number: " + employee.getPhone());
        System.out.println("Role: " + employee.getRole());
        System.out.println("Start Date: " + employee.getStartDate());
        System.out.println("Compensation: " + employee.getPay());
        System.out.println("Compensation Frequency: " + employee.getCompensationFrequency());

        menu(context, new String[] {"Go Back"}, new Page[] {new EmployeeProfile()});
	}

}
