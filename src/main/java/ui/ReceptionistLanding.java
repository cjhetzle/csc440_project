package main.java.ui;

public class ReceptionistLanding extends MenuPage {

	@Override
	public void run(Context context) {
		menu(context, new String[] {"Profile", "View Customer Profile", "Register Car", "Service History",
				"Schedule Service", "Reschedule Service", "Invoices", "Daily Task-Update Inventory",
				"Daily Task-Record Deliveries", "Logout"}, new Page[] {new EmployeeProfile(), new EmployeeViewCustomerProfile(),
						new ReceptionistRegisterCar(), new ReceptionistServiceHistory(), new ReceptionistScheduleService(),
						new ReceptionistRescheduleService(), new ReceptionistInvoices(), new DailyUpdateInventory(),
						new DailyRecordDeliveries(), new HomePage()});
	}

}
