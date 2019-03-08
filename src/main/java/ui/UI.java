package main.java.ui;

import java.sql.SQLException;

import main.java.backend.Utils;

public class UI {

	public static void main(String[] args) throws Exception {
		setVariables();
		System.out.println("Welcome to the Acme Car Service and Repair Center database!");
		Context context = new Context();
		while (context.getPage() != null) {
			context.getPage().run(context);
		}
	}

	private static void setVariables() throws SQLException {
		Utils.insert("SET sql_mode = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION';", new String[0]);
	}
}
