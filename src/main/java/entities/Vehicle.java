package main.java.entities;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import main.java.backend.Utils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Vehicle implements Entity {
    private String license;
    private String make;
    private String model;
    private String year;
    private String datePurchased;
    private String customerId;

    public Vehicle(String license) {
		super();
		this.license = license;
	}

	public Vehicle(String license, String make, String model, String year, String datePurchased, String customerId) {
		super();
		this.license = license;
		this.make = make;
		this.model = model;
		this.year = year;
		this.datePurchased = datePurchased;
		this.customerId = customerId;
	}

    public static boolean hasValidMakeModel (String make, String model) {
        try {
            return Utils.select(
                "SELECT 1 FROM ByMileage WHERE make = ? AND model = ?",
                new String[] {make, model}
            ).size() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public String getLicense () {
        return license;
    }

    public void setLicense (String license) {
        this.license = license;
    }

    public String getMake () {
        return make;
    }

    public void setMake (String make) {
        this.make = make;
    }

    public String getModel () {
        return model;
    }

    public void setModel (String model) {
        this.model = model;
    }

    public String getYear () {
        return year;
    }

    public void setYear (String year) {
        this.year = year;
    }

    public String getDatePurchased () {
        return datePurchased;
    }

    public void setDatePurchased (String datePurchased) {
        this.datePurchased = datePurchased;
    }

    public String getCustomerId () {
        return customerId;
    }

    public void setCustomerId (String customerId) {
        this.customerId = customerId;
    }

    @Override
    public void save() throws SQLException {
		Utils.insert("INSERT INTO Vehicle VALUES (?,?,?,?,?,?)",
				new String[] {license, make, model, year,
						datePurchased, customerId});
	}

	@Override
	public void load() throws SQLException {
		List<Map<String,String>> vehicles = Utils.select(
            "SELECT * FROM Vehicle WHERE license = ?;",
            new String[] {license}
        );
		Map<String,String> row = vehicles.get(0);
		make = row.get("make");
		model = row.get("model");
		year = row.get("year");
		datePurchased = row.get("datePurchased");
		customerId = row.get("customerID");
	}

	@Override
	public void insert() {
        throw new NotImplementedException();
    }
}
