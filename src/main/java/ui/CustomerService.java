package main.java.ui;

public class CustomerService extends MenuPage {

	@Override
	public void run(Context context) {
		menu(context, new String[] {"View Service History", "Schedule Service", "Reschedule Service", "Go Back"},
				new Page[] {new CustomerServiceHistory(), new CustomerScheduleService(),
						new RescheduleServicePage1(), new CustomerLanding()});
	}

}
