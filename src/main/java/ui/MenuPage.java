package main.java.ui;

import java.util.function.Function;

public abstract class MenuPage extends Page {

	protected int menu(Context context, String[] labels, Page[] pages) {
		if (pages.length == 0 || labels.length != pages.length) {
			throw new IllegalArgumentException();  // No more pages or bad args, quit the program
		}
		System.out.println("========== " + this.getClass().getSimpleName() + " ==========");
		for (int i = 0; i < labels.length; i++) {
			System.out.println(Integer.toString(i+1) + ": " + labels[i]);
		}
		String input = context.getScanner().nextLine();

		int number;
		try {
			number = Integer.parseInt(input);
			if(number < 1 || number > labels.length) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
		    System.out.println("Please enter a number between 1 and " + labels.length);
			return menu(context, labels, pages);
		}
		context.setPage(pages[number - 1]);
		return number;
	}

	protected String promptUser(Context context, String prompt, Function<String,Boolean> validator) {
		String input;
		System.out.print(prompt);
		input = context.getScanner().nextLine();
		while (validator != null && !validator.apply(input)) {
            System.out.println("Please enter a valid value");
			System.out.print(prompt);
			input = context.getScanner().nextLine();
		}
		return input;
	}
}
