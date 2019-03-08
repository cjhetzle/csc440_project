package main.java.ui;

public class EmployeeProfile extends MenuPage {

	@Override
	public void run(Context context) {
		Page landingPage;
		if (context.getSessionData().get("role").equals("receptionist")) {
			landingPage = new ReceptionistLanding();
		} else {
			landingPage = new ManagerLanding();
		}
		menu(context, new String[] {"View Profile", "Update Profile", "Go Back"},
				new Page[] {new EmployeeViewProfile(), new EmployeeUpdateProfile(), landingPage});
	}

}
