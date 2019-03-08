package main.java.entities;

import main.java.backend.Utils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Employee implements Entity {
    private String eid;
    private String cid;
    private String name;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String emailAddress;
    private String phone;
    private String role;
    private String startDate;
    private String pay;
    private String password;

    public Employee(String eid) {
		this.eid = eid;
	}

    /**
     * Return whether or not the employee is paid monthly or hourly
     * Should just be set up in the constructor as a boolean variable
     * @return true - monthly; false - hourly;
     */
    public String getCompensationFrequency () {
        switch (role) {
            case "manager":
                return "monthly";
            case "receptionist":
                return "monthly";
            case "mechanic":
                return "hourly";
            default:
                throw new IllegalArgumentException("Unrecognized role:" + role);
        }
    }

    public String getEid () {
        return eid;
    }

    public void setEid (String eid) {
        this.eid = eid;
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

    public String getEmailAddress () {
        return emailAddress;
    }

    public void setEmailAddress (String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhone () {
        return phone;
    }

    public void setPhone (String phone) {
        this.phone = phone;
    }

    public String getRole () {
        return role;
    }

    public void setRole (String role) {
        this.role = role;
    }

    public String getStartDate () {
        return startDate;
    }

    public void setStartDate (String startDate) {
        this.startDate = startDate;
    }

    public String getPay () {
        return pay;
    }

    public void setPay (String pay) {
        this.pay = pay;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public void setCid (String cid) {
        this.cid = cid;
    }

    public String getCid () {
        return cid;
    }

    @Override public void load () throws SQLException {
        List<Map<String,String>> employees =
				Utils.select("SELECT * FROM Employee WHERE eid = ?;", new String[] {eid});
		Map<String,String> row = employees.get(0);
		eid = row.get("eid");
		cid = row.get("cid");
		name = row.get("name");
		street = row.get("street");
		city = row.get("city");
		state = row.get("state");
		zip = row.get("zip");
		emailAddress = row.get("emailAddress");
		phone = row.get("phone");
		role = row.get("role");
		startDate = row.get("startDate");
		pay = row.get("pay");
		password = row.get("password");
    }

    @Override public void save () throws SQLException {
        Utils.insert(
            "UPDATE Employee SET cid=?, name=?, street=?, city=?, "
            + "state=?, zip=?, emailAddress=?, phone=?, role=?, "
            + "startDate=?, pay=?, password=? "
            + "WHERE eid=?",
            new String[]{
                cid, name, street, city,
                state, zip, emailAddress, phone, role,
                startDate, pay, password,
                eid
            }
        );
    }

    @Override public void insert () {
        throw new NotImplementedException();
    }

}
