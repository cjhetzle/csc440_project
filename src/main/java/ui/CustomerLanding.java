package main.java.ui;

public class CustomerLanding extends MenuPage {

	@Override
	public void run(Context context) {
		menu(context, new String[] {"Profile", "Register Car", "Service", "Invoices", "Log Out"},
				new Page[] {new CustomerProfile(), new CustomerRegisterCar(),
						new CustomerService(), new CustomerInvoices(), new HomePage()});
	}

}
