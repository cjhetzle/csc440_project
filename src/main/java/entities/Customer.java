package main.java.entities;

import main.java.backend.Utils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Customer implements Entity {
    private String        customerID;
    private String        cid;
    private String        name;
    private String        street;
    private String        city;
    private String        state;
    private String        zip;
    private String        emailAddress;
    private String        phone;
    private String        password;
    private List<Vehicle> vehicles;

    public Customer(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Customer(String customerID, String cid, String name, String street, String city, String state, String zip,
            String emailAddress, String phone, String password) {
        super();
        this.customerID = customerID;
        this.cid = cid;
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.emailAddress = emailAddress;
        this.phone = phone;
        this.password = password;
    }

    public Customer() {
		super();
	}

	public String getCid () {
        return cid;
    }

    public void setCid (String cid) {
        this.cid = cid;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public String getCustomerID () {
        return customerID;
    }

    public void setcustomerID (String customerID) {
        this.customerID = customerID;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getStreet () {
        return street;
    }

    public void setStreet (String street) {
        this.street = street;
    }

    public String getCity () {
        return city;
    }

    public void setCity (String city) {
        this.city = city;
    }

    public String getState () {
        return state;
    }

    public void setState (String state) {
        this.state = state;
    }

    public String getZip () {
        return zip;
    }

    public void setZip (String zip) {
        this.zip = zip;
    }

    public String getPhone () {
        return phone;
    }

    public void setPhone (String phone) {
        this.phone = phone;
    }

    public List<Vehicle> getVehicles () {
        return vehicles;
    }

    public void setVehicles (List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public String getEmailAddress () {
        return emailAddress;
    }

    public void setEmailAddress (String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public void load() throws SQLException {
        List<Map<String,String>> customers = emailAddress == null ?
                Utils.select("SELECT * FROM Customer WHERE customerID = ?;", new String[] {customerID}) :
                Utils.select("SELECT * FROM Customer WHERE emailAddress = ?;", new String[] {emailAddress});
        Map<String,String> row = customers.get(0);
        customerID = row.get("customerID");
        cid = row.get("cid");
        name = row.get("name");
        street = row.get("street");
        city = row.get("city");
        state = row.get("state");
        zip = row.get("zip");
        emailAddress = row.get("emailAddress");
        phone = row.get("phone");
        password = row.get("password");
    }

    @Override
    public void save() throws SQLException {
        Utils.insert(
                "UPDATE Customer SET cid=?, name=?, street=?, city=?,"
                        + "state=?, zip=?, emailAddress=?, phone=?, password=? "
                        + "WHERE customerID=?",
                new String[]{
                        cid, name, street, city,
                        state, zip, emailAddress, phone, password,
                        customerID
                }
        );
    }

    @Override public void insert () throws SQLException {
        Utils.insert("INSERT INTO Customer VALUES (default,?,?,?,?,?,?,?,?,?);", 
        		new String[] {this.cid, this.name, this.street, this.city, this.state,
        				this.zip, this.emailAddress, this.phone, this.password});
    }
}
