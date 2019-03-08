package main.java.ui;

public class OrdersPage extends MenuPage {

	@Override
	public void run(Context context){
		menu(context, new String[] {"Order History", "New Order", "Go Back"},
				new Page[] {new ManagerOrderHistory(), new NewOrderPage(), new ManagerLanding()});
	}
}
