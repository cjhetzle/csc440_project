package main.java.backend;

import com.google.gson.Gson;
import main.java.entities.Customer;
import main.java.entities.Employee;
import main.java.entities.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Helper methods that deal with viewing and updating customer and employee profiles
 */
public class Profile {
    public static Customer getCustomerProfile(String customerID) throws SQLException {
        List<Map<String,String>> customers = Utils.select(
            "SELECT customerID,name,street,city,state,zip,emailAddress,phone"
            + " FROM Customer WHERE customerID = ?",
            new String[] {customerID}
        );
        if (customers.size() != 1) {
            throw new SQLException("Found " + customers.size() + " customers with id " + customerID);
        }

        Map<String,String> row = customers.get(0);
        Gson gson = new Gson();
        Customer customer = gson.fromJson(gson.toJsonTree(row), Customer.class);

        // Get all the customer's vehicles
        List<Map<String,String>> vehicles = Utils.select(
            "SELECT license,make,model,year,datePurchased"
            + " FROM Vehicle WHERE customerID = ?",
            new String[] {customer.getCustomerID()}
        );
        customer.setVehicles(new ArrayList<>());
        for (Map<String,String> vRow : vehicles) {
            Vehicle vehicle = gson.fromJson(gson.toJsonTree(vRow), Vehicle.class);
            customer.getVehicles().add(vehicle);
        }
        return customer;
    }

    public static Employee getEmployeeProfile (String eid) throws SQLException {
        List<Map<String,String>> employees = Utils.select(
            "SELECT eid,name,street,city,state,zip,emailAddress,phone,role,startDate,pay"
            + " FROM Employee WHERE eid = ?",
            new String[] {eid}
        );
        if (employees.size() != 1) {
            throw new SQLException("Found " + employees.size() + " employees with that eid");
        }
        Map<String,String> row = employees.get(0);
        Gson gson = new Gson();
        return gson.fromJson(gson.toJsonTree(row), Employee.class);
    }
}
