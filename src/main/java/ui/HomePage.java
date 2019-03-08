package main.java.ui;

import java.util.Scanner;

public class HomePage extends MenuPage {

	@Override
	public void run(Context context) {
		this.menu(context, new String[] {"Log In", "Sign Up", "Exit"},
				new Page[] {new LoginPage(), new SignupPage(), null});
	}
}
