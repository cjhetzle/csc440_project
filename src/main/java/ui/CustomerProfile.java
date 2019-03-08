package main.java.ui;

public class CustomerProfile extends MenuPage {

	@Override
	public void run(Context context) {
		menu(context, new String[] {"View Profile", "Update Profile", "Go Back"},
				new Page[] {new CustomerViewProfile(), new CustomerUpdateProfile(), new CustomerLanding()});
	}

}
