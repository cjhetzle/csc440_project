package main.java.entities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import main.java.backend.Utils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ServiceAppointment implements Entity {

	private String license;
	private String apptDate;
	private String fault;
	private String preferredMechanic;
	private String mileage;

	public ServiceAppointment(String license, String apptDate) {
		super();
		this.license = license;
		this.apptDate = apptDate;
	}

	public ServiceAppointment(String license, String apptDate, String fault, String preferredMechanic, String mileage) {
		super();
		this.license = license;
		this.apptDate = apptDate;
		this.fault = fault;
		this.preferredMechanic = preferredMechanic;
		this.mileage = mileage;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getApptDate() {
		return apptDate;
	}

	public void setApptDate(String apptDate) {
		this.apptDate = apptDate;
	}

	public String getFault() {
		return fault;
	}

	public void setFault(String fault) {
		this.fault = fault;
	}

	public String getPreferredMechanic() {
		return preferredMechanic;
	}

	public void setPreferredMechanic(String preferredMechanic) {
		this.preferredMechanic = preferredMechanic;
	}

	public String getMileage() {
		return mileage;
	}

	public void setMileage(String mileage) {
		this.mileage = mileage;
	}

	@Override
	public void load() throws SQLException {
        Map<String,String> row = Utils.select("SELECT * "
        		+ "FROM ServiceAppointment "
        		+ "WHERE license = ? "
        		+ "AND apptDate = ?;", new String[] {
        			this.license, this.apptDate
        		}).get(0);
        this.fault = row.get("fault");
        this.preferredMechanic = row.get("preferredMechanic");
        this.mileage = row.get("mileage");
    }

	@Override
	public void insert() throws SQLException {
		Utils.insert("INSERT INTO ServiceAppointment VALUES (?,?,?,?,?);",
				new String[] {license, apptDate, fault, preferredMechanic, mileage});
	}

	@Override
	public void save() {
        throw new NotImplementedException();
    }

	public static List<ServiceAppointment> getUpcomingForCustomer(String customerID) throws SQLException {
		List<ServiceAppointment> upcoming = new ArrayList<ServiceAppointment>();
		String today = Utils.formatDay(Calendar.getInstance());
		List<Map<String,String>> tuples = Utils.select("SELECT sa.* " + 
				"FROM Vehicle v " + 
				"LEFT JOIN ServiceAppointment sa " + 
				"ON sa.license = v.license " + 
				"WHERE v.customerID = ? " + 
				"AND sa.apptDate > ?;", new String[] {
						customerID, today
				});
		for (Map<String,String> tuple : tuples) {
			upcoming.add(new ServiceAppointment(
					tuple.get("license"), 
					tuple.get("apptDate"), 
					tuple.get("fault"), 
					tuple.get("preferredMechanic"), 
					tuple.get("mileage")));
		}
		return upcoming;
	}

	public String getType() {
		return this.fault == null ? "Maintenance" : "Repair";
	}

	public String getDetails() throws SQLException {
		if (this.fault != null) {
			return this.fault;
		}
		
		List<Map<String,String>> details = Utils.select(
				"SELECT DISTINCT (svc.name), MAX(b.mileage) " + 
				"FROM ServiceAppointment sa " + 
				"LEFT JOIN Schedules s " + 
				"ON s.license = sa.license " + 
				"AND s.dateScheduled = sa.apptDate " + 
				"LEFT JOIN Includes i " + 
				"ON s.service = i.included " + 
				"LEFT JOIN Service svc " + 
				"ON svc.serviceID = i.includer " + 
				"LEFT JOIN ByMileage b " + 
				"ON b.serviceID = svc.serviceID " + 
				"WHERE sa.apptDate = ? " + 
				"AND sa.license = ? " + 
				"ORDER BY b.mileage DESC " + 
				"LIMIT 1;", new String[] {this.apptDate, this.license});
		
		String service = details.get(0).get("name");
		String svcClass = service.split(",")[0];
		return svcClass;
	}

	public void delete() throws SQLException {
		Utils.insert("DELETE "
				+ "FROM Schedules "
				+ "WHERE license = ? "
				+ "AND dateScheduled = ?;",
				new String[] {license, apptDate});
		Utils.insert("DELETE "
				+ "FROM ServiceAppointment "
				+ "WHERE license = ? "
				+ "AND apptDate = ?;",
				new String[] {license, apptDate});
	}

}
