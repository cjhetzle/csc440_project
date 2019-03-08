package main.java.ui;

import main.java.backend.Utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class NewCarModel extends MenuPage {
    String EXIT = "0";
	@Override
	public void run(Context context) {
	    // The only page to return to is the manager landing
        context.setPage(new ManagerLanding());

	    String input;
	    String [] makeModel = new String[2];
	    int year;
	    Function<String, Boolean> inputInt = (String str) -> {
	        try {
	            Integer.valueOf(str);
	            return true;
            } catch (NumberFormatException e) {
	            return false;
            }
        };

	    Function<String, Boolean> inputDouble = (String str) -> {
	        try {
	            double temp = Double.parseDouble(str);
	            if (temp > 9.99)
	                throw new NumberFormatException();
	            return true;
            } catch (NumberFormatException e) {
	            return false;
            }
        };

        System.out.println("==============Add New Car Model===============");
        System.out.println("Please enter the following information\n" +
                "to exit any time and return without saving type '0'");

        while (true) {
            while (true) {

                // Add car
                // Go back
                input = promptUser(context, "Maker: ", null);
                if (exit(input))
                    return;
                makeModel[0] = input;

                input = promptUser(context, "Model: ", null);
                if (exit(input))
                    return;
                makeModel[1] = input;


                input = promptUser(context, "Year: ", inputInt);
                if (exit(input))
                    return;


                input = promptUser(context, "Is this information correct?" +
                                "\nMake: " + makeModel[0] +
                                "\nModel: " + makeModel[1] +
                                "\nYear: " + input +
                                "\n\nCONFIRM(y/n)",
                        val -> (val.equals("y") || val.equals("n") || val.equals("0")));
                if (exit(input))
                    return;
                else if (input.equals("y"))
                    break;
            }

            // this can be an array of list<array>
            // but I'm working on other stuff
            ArrayList<ArrayList<ArrayList<String>>> serviceInformation;
            serviceInformation = new ArrayList<>();
            char service = 'A';

            while (service < 'D') {
                // Number of services related
                // to the main service
                int serviceCount = 0;
                // Initialization
                serviceInformation.add(new ArrayList<>());
                serviceInformation.get(service - 'A').add(new ArrayList<>());
                //................
                System.out.println("Information regarding Service " + service + "\n");
                // CAR MILES
                input = promptUser(context, "Miles: ", inputInt);
                if (exit(input))
                    return;
                serviceInformation.get(service - 'A').get(serviceCount).add(input);

                System.out.println("\nPlease enter the service name\n" +
                        "you will then be prompted for a Part ID\n" +
                        "and then a quantity for each.");


                // start entering services
                // serviceID
                // price
                // hours
                // partID
                while (true) {
                    serviceCount++;
                    serviceInformation.get(service - 'A').add(new ArrayList<>());

                    // SERVICE NAME
                    input = promptUser(context,
                            "Enter Service Name (do not include Make Model): ",
                            null);
                    if (exit(input))
                        return;

                    // should check if this has been used already or if it even exists
                    // but im not going to yet
                    serviceInformation.get(service - 'A')
                            .get(serviceCount).add(input);

                    // PRICE

                    input = promptUser(context,
                            "Enter price as whole INT: ",
                            inputInt);
                    if (exit(input))
                        return;

                    serviceInformation.get(service - 'A')
                            .get(serviceCount).add(input);


                    // HOURS
                    input = promptUser(context,
                            "Enter hours associated with service(<9.99): ",
                            inputDouble);
                    if (exit(input))
                        return;

                    serviceInformation.get(service - 'A')
                            .get(serviceCount).add(input);

                    // PART ID

                    input = promptUser(context,
                            "Enter the part ID needed: ",
                            val -> (val.equals("0") || NewCarModel.isPartID(val)));
                    if (exit(input))
                        return;

                    serviceInformation.get(service - 'A')
                            .get(serviceCount).add(input);


                    // PART QUANTITY
                    String partID = input;
                    input = promptUser(context, "Enter the quantity of " + partID + ": ", inputInt);
                    if (exit(input))
                        return;
                    serviceInformation.get(service - 'A')
                            .get(serviceCount).add(input);

                    // MONTH WARRANTY
                    input = promptUser(context, "Service Months Warranty: ", inputInt);
                    if (exit(input))
                        return;
                    serviceInformation.get(service - 'A')
                            .get(serviceCount).add(input);

                    // Print this once
                    ArrayList<String> temp = serviceInformation.get(service - 'A')
                            .get(serviceCount);
                    System.out.println("Is this information correct?\n" +
                            "Service Name: " + temp.get(0) +
                            "\nCustomer Cost: " + temp.get(1) +
                            "\nHours Required: " + temp.get(2) +
                            "\nPartID Required: " + temp.get(3) +
                            "\nPart Quantity: " + temp.get(4) +
                            "\nService Warranty: " + temp.get(5));


                    input = promptUser(context, "\nCONFIRM(y/n): ",
                            val -> val.equals("y") ||
                                    val.equals("n") ||
                                    val.equals("0"));
                    if (exit(input))
                        return;
                    else if (input.equals("n")) {
                        serviceCount--;
                        continue;
                    }


                    input = promptUser(context, "Add another service?(y/n): ",
                            val -> val.equals("y") ||
                                    val.equals("n") ||
                                    val.equals("0"));
                    if (exit(input))
                        return;
                    else if (input.equals("n"))
                        break;
                }
                service++;
            }

            // added all information for new car

            // prompt user if this was it
            int result = menu(context, new String[]{"Add Car", "Go Back"}, new Page[]{null, new ManagerLanding()});
            if (result == 1) {
            	try {
            		insertIntoDB(serviceInformation, makeModel);
            		System.out.println("The new model should be added to the db, thank you.\n");
            		context.setPage(new ManagerLanding());
            	} catch (SQLException e) {
            		System.out.println("Sorry, there was an error entering the information provided");
            		context.setPage(new ManagerLanding());
            		//e.printStackTrace();
            		return;
            	}
            }
            if (result == 2)
                return;
        }
	}

	private static boolean isPartID(String partID) {
	    try {
            return Utils.select("SELECT 1 FROM PartDatabase WHERE partID = ?;",
                    new String[]{partID}).size() == 1;
        } catch (SQLException e) {
	        System.out.println("The partID is invalid, please enter a valid partID.");
        }
        return false;
    }

	private boolean exit(String input) {
	    if (input.toLowerCase().equals(EXIT))
	        return true;
	    return false;
    }

	private void insertIntoDB(ArrayList<ArrayList<ArrayList<String>>> serviceInformation, String[] makeModel) throws SQLException {
	    int i =0, serviceCount;
        int[] mainService = new int[3];
	    List<Map<String,String>> output = Utils.select("SELECT COUNT(*) FROM Service;",
                new String[]{});
	    serviceCount = Integer.valueOf(output.get(0).get("COUNT(*)"));

        for (; i<3; ++i) {
            for (ArrayList<String> si : serviceInformation.get(i)) {
                if (si.size() < 5) {
                    serviceCount++;
                    mainService[i] = serviceCount;
                    // SERVICE
                    Utils.insert("INSERT INTO Service (serviceID, name) VALUES (?, ?);",
                            new String[]{String.valueOf(serviceCount), "Service " + ((i==0)?"A":(i==1)?"B":"C") +
                                    ", " + makeModel[0] +
                                    " " + makeModel[1]});
                    // BYMILEAGE
                    Utils.insert("INSERT INTO ByMileage (serviceID, mileage, make, model) VALUES (?, ?, ?, ?);",
                            new String[]{ String.valueOf(serviceCount), si.get(0), makeModel[0], makeModel[1]});
                }
            }
        }

        i=0;
	    for (; i<3; ++i) {
            for (ArrayList<String> si : serviceInformation.get(i)) {
                if (si.size() > 2) {
                    serviceCount++;
                    // SERVICE
                    Utils.insert("INSERT INTO Service (serviceID, name) VALUES (?, ?);",
                            new String[]{String.valueOf(serviceCount), si.get(0) + ", " + makeModel[0] + " " + makeModel[1]});

                    // INCLUDES==============================================================
                    Utils.insert("INSERT INTO Includes (includer, included) VALUES (?, ?);",
                            new String[]{String.valueOf(mainService[i]), String.valueOf(serviceCount)});
                    if (i<=1)
                        Utils.insert("INSERT INTO Includes (includer, included) VALUES (?, ?);",
                                new String[]{String.valueOf(mainService[i+1]), String.valueOf(serviceCount)});
                    if (i==0)
                        Utils.insert("INSERT INTO Includes (includer, included) VALUES (?, ?);",
                                new String[]{String.valueOf(mainService[i+2]), String.valueOf(serviceCount)});
                    // ======================================================================

                    // BILLABLEUNIT
                    Utils.insert("INSERT INTO BillableUnit (serviceID, chargeRate, timeReq) VALUES (?, ?, ?);",
                            new String[] {String.valueOf(serviceCount), si.get(1), si.get(2)});

                    // CONSUMES
                    Utils.insert("INSERT INTO Consumes (serviceID, partID, qty) VALUES (?, ?, ?);",
                            new String[]{ String.valueOf(serviceCount), si.get(3), si.get(4)});

                    // WARRANTY
                    Utils.insert("INSERT INTO Warranty (service, duration) VALUES (?, ?);",
                            new String[]{String.valueOf(serviceCount), si.get(5)});
                }
            }
        }
    }
}
