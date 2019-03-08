package main.java.ui;

import main.java.entities.Payrolls;
import main.java.entities.Employee;

import java.sql.SQLException;
import java.text.ParseException;

public class PayrollPage extends MenuPage {

	@Override
	public void run(Context context) {
        String userId = context.getSessionData().get("userId");
        Payrolls payrollHistory;
        String input;
        int number;
        context.setPage(new ManagerLanding());
        String cid = context.getSessionData().get("cid");

        while (true) {
            input = promptUser(context, "Please enter EID or 1 to exit: ", null);
            int i;
            try {
                i = Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println("Enter Valid EID");
                continue;
            }

            if (i == 1)
                return;

            Employee employee = new Employee(input);
            try {
                employee.load();
            } catch (SQLException | IndexOutOfBoundsException e) {
                System.out.println("Employee with that eid does not exist. Please try again");
                continue;
            }
            if (employee.getCid().equals(cid)) {
                payrollHistory = new Payrolls(input);
                try {
                    payrollHistory.load();
                    payrollHistory.generate();
                    payrollHistory.save();
                    break;
                } catch (SQLException | ParseException e) {
                    System.out.println("Please enter a valid eid");
                }
            } else {
                System.out.println("Please enter an employee from your center");
            }

        }

        int count = payrollHistory.size() + 1;
        int i = 0;
        for ( ; i < count; ++i)
            System.out.print(payrollHistory.toStringList().get(i));

        //System.out.println(payrollHistory.toStringList());
        System.out.println("\n1: Go Back");
        input = context.getScanner().nextLine();

        try {
            number = Integer.parseInt(input);
            if (number != 1) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.println();
        }
	}

}
