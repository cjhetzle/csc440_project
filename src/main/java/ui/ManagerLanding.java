package main.java.ui;

public class ManagerLanding extends MenuPage {

	@Override
	public void run(Context context) {
		menu(context, new String[] {"Profile", "View Customer Profile", "Add New Employees", "Payroll", "Inventory",
				"Orders", "Notifications", "New Car Model", "Car Service Details", "Service History", "Invoices",
				"Logout"}, new Page[] {new EmployeeProfile(), new EmployeeViewCustomerProfile(), new AddNewEmployees(),
						new PayrollPage(), new InventoryPage(), new OrdersPage(), new NotificationsPage(),
						new NewCarModel(), new ManagerServiceDetails(), new ManagerServiceHistory(),
						new ManagerInvoices(), new HomePage()});
	}

}
