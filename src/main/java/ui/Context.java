package main.java.ui;

import java.util.HashMap;
import java.util.Scanner;

public class Context {
	private Page page;
    /**
     * "userId" ->
     */
	private HashMap<String,String> sessionData;
	private Scanner scanner;
	
	public Context() {
		this.page = new HomePage();
		this.sessionData = new HashMap<String,String>();
		this.scanner = new Scanner(System.in);
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public HashMap<String, String> getSessionData() {
		return sessionData;
	}

	public Scanner getScanner() {
		return scanner;
	}

	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}
}
