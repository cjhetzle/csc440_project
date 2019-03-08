package main.java.entities;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import main.java.backend.Utils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Schedules implements Entity {
	
	String license;
	String dateScheduled;
	String timeSlot;
	String mechanic;
	String service;

	public Schedules(String license, String dateScheduled, String timeSlot, String mechanic, String service) {
		super();
		this.license = license;
		this.dateScheduled = dateScheduled;
		this.timeSlot = timeSlot;
		this.mechanic = mechanic;
		this.service = service;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getDateScheduled() {
		return dateScheduled;
	}

	public void setDateScheduled(String dateScheduled) {
		this.dateScheduled = dateScheduled;
	}

	public String getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}

	public String getMechanic() {
		return mechanic;
	}

	public void setMechanic(String mechanic) {
		this.mechanic = mechanic;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	@Override
	public void load() throws SQLException {
        throw new NotImplementedException();
	}

	@Override
	public void save() throws SQLException {
        throw new NotImplementedException();
	}

	@Override
	public void insert() throws SQLException {
        Utils.insert("INSERT INTO Schedules VALUES (?,?,?,?,?);", new String[] {
        		this.license,
        		this.dateScheduled,
        		this.timeSlot,
        		this.mechanic,
        		this.service
        });
	}
	
	public static boolean canScheduleMaintenance(Calendar day, String cid, long slotsReq) throws SQLException {
		// first, will this day at this service center be over 59% booked
		// with maintenance requests if this service is booked
		Map<String,String> maintenanceBooked = Utils.select(
				"SELECT COUNT(eid) as mechanics, SUM(bookedSlots) as bookedMaintenance " + 
				"FROM (SELECT e.eid, (COUNT(1) - COUNT(sa.fault)) as bookedSlots " + 
				" FROM Employee e " + 
				" LEFT JOIN Schedules s " + 
				" ON s.mechanic = e.eid " + 
				" AND s.dateScheduled = ? " + 
				" LEFT JOIN ServiceAppointment sa " + 
				" ON sa.license = s.license " + 
				" AND sa.apptDate = s.dateScheduled " + 
				" WHERE e.cid = ? " + 
				" AND e.role = 'mechanic' " + 
				" GROUP BY e.eid) as x; ",  
				new String[] {
						Utils.formatDay(day),
						cid
				}).get(0);
		if (slotsReq + Integer.parseInt(maintenanceBooked.get("bookedMaintenance"))
		> 0.59 * 44 * Integer.parseInt(maintenanceBooked.get("mechanics"))) {
			return false;
		}
		return true;
	}

	private static List<Map<String, String>> getAvailableMechanics(Calendar day, String cid, String mechanic, String slots) throws SQLException {
		if (mechanic != null) {
			return Utils.select(
					"SELECT e.eid, COUNT(s.timeSlot) as bookedSlots " + 
					"FROM Employee e " + 
					"LEFT JOIN Schedules s " + 
					"ON s.mechanic = e.eid " + 
					"AND s.dateScheduled = ? " + 
					"WHERE e.eid = ? " + 
					"GROUP BY e.eid " + 
					"HAVING (44 - bookedSlots) >= ?;", new String[] {
							Utils.formatDay(day),
							mechanic,
							slots
					});
		} else {
			return Utils.select(
					"SELECT e.eid, COUNT(s.dateScheduled) as bookedSlots FROM Employee e " + 
					"LEFT JOIN Schedules s " + 
					"ON s.mechanic = e.eid " + 
					"AND s.dateScheduled = ? " + 
					"WHERE e.cid = ? " + 
					"AND e.role = 'mechanic' " + 
					"GROUP BY e.eid " + 
					"HAVING (44 - bookedSlots) >= ?;", 
					new String[] {
							Utils.formatDay(day),
							cid,
							slots
					});
		}
	}
	
	private static String mechanicFree(List<Map<String,String>> availabileMechanics) {
		for (Map<String,String> availableMechanic : availabileMechanics) {
			if (Integer.parseInt(availableMechanic.get("bookedSlots")) == 0) {
				return availableMechanic.get("eid");
			}
		}
		return null;
	}
	
	private static List<Schedules> scheduleServices(String license, String date, String startingTime, String mechanic, List<Map<String,String>> services) throws ParseException, SQLException {
		List<Schedules> schedulesList = new ArrayList<Schedules>();
		Calendar time = Utils.parseTime(startingTime);
		for (Map<String,String> service : services) {
			long slots = Math.round(Double.parseDouble(service.get("slots")));
			String serviceID = service.get("serviceID");
			for (int i = 0; i < slots; i++) {
				String timeSlot = Utils.formatTime(time);
				Schedules schedules = new Schedules(license, date, timeSlot, mechanic, serviceID);
				schedulesList.add(schedules);
				time.add(Calendar.MINUTE, 15);
			}
		}
		return schedulesList;
	}

	private static List<Schedules> rescheduleServices(List<Schedules> schedulesList, String day,
			String startingTime, String scheduledMechanic) throws ParseException {
		Calendar time = Utils.parseTime(startingTime);
		for (Schedules schedules : schedulesList) {
			String timeSlot = Utils.formatTime(time);
			schedules.setDateScheduled(day);
			schedules.setMechanic(scheduledMechanic);
			schedules.setTimeSlot(timeSlot);
			time.add(Calendar.MINUTE, 15);
		}
		return schedulesList;
	}

	private static List<Schedules> generateMaintenanceSchedules(String license, String date, String startingTime, String mechanic, String byMileage) throws ParseException, SQLException {
		List<Map<String,String>> services = ByMileage.getIncludedServices(byMileage);
		return scheduleServices(license, date, startingTime, mechanic, services);
	}
	
	private static List<Schedules> generateRepairSchedules(String license, String date, String startingTime, String mechanic, String fault) throws ParseException, SQLException {
		List<Map<String,String>> services = Recommends.getIncludedServices(fault, license);
		return scheduleServices(license, date, startingTime, mechanic, services);
	}
	
	private static String getStartingTime(Map<String,String> availableMechanic, Calendar day, long slotsReq) throws SQLException {
		// let's get their schedule
		List<Map<String,String>> schedule = Utils.select(
				"SELECT * " + 
				"FROM Schedules " + 
				"WHERE mechanic = ? " + 
				"AND dateScheduled = ? " + 
				"ORDER BY timeSlot;", new String[] {
						availableMechanic.get("eid"),
						Utils.formatDay(day)
				});
		Map<String,Boolean> booked = new HashMap<String,Boolean>();
		for (Map<String,String> timeSlot : schedule) {
			booked.put(timeSlot.get("timeSlot"), true);
		}
		int consecutiveAvailable = 0;
		day.set(Calendar.HOUR_OF_DAY, 8);
		day.set(Calendar.MINUTE, 0);
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		String timeSlot = timeFormat.format(day.getTime());
		String startingTime = timeSlot;
		while (!timeSlot.equals("19:00")) {
			if (booked.containsKey(timeSlot)) {
				consecutiveAvailable = 0;
			} else if (++consecutiveAvailable >= slotsReq) {
				break;
			}
			day.add(Calendar.MINUTE, 15);
			timeSlot = timeFormat.format(day.getTime());
			if (consecutiveAvailable == 0) {
				startingTime = timeSlot;
			}
		}
		if (consecutiveAvailable >= slotsReq) {
			return startingTime;
		}
		return null;
	}
	
	public static List<Schedules> scheduleMaintenance(
			Calendar day, 
			String cid, 
			String serviceID, 
			String mechanic,
			String license
			) throws SQLException, ParseException {
		long slotsReq = ByMileage.timeSlotsRequired(serviceID);
		while (true) {
			do {
				day.add(Calendar.DAY_OF_MONTH, 1);
			} while (day.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || 
					day.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
			if (!canScheduleMaintenance(day, cid, slotsReq) || alreadyScheduled(day, license)) {
				continue;
			}
			List<Map<String,String>> availability = getAvailableMechanics(day, cid, mechanic, Long.toString(slotsReq));
			
			// first, check if the mechanic is completely free on that day
			String scheduledMechanic = mechanicFree(availability);
			if (scheduledMechanic != null) {
				return generateMaintenanceSchedules(license, Utils.formatDay(day), "08:00", scheduledMechanic, serviceID);
			}
			// else this mechanic is available, but not fully available.
			// need to check if they have enough *consecutive* timeslots available.
			for (Map<String,String> availableMechanic : availability) {
				String startingTime = getStartingTime(availableMechanic, day, slotsReq);
				if (startingTime != null) {
					scheduledMechanic = availableMechanic.get("eid");
					return generateMaintenanceSchedules(license, Utils.formatDay(day), startingTime, scheduledMechanic, serviceID);
				}
			}
		}
	}
	
	private static List<Schedules> timeSlotsBooked(String license, String apptDate) throws NumberFormatException, SQLException {
		List<Map<String,String>> tuples = Utils.select("SELECT * "
				+ "FROM Schedules "
				+ "WHERE license = ? "
				+ "AND dateScheduled = ?;", new String[] {
						license, apptDate
				});
		List<Schedules> slots = new ArrayList<Schedules>();
		Gson gson = new Gson();
		for (Map<String,String> tuple : tuples) {
			slots.add(gson.fromJson(gson.toJsonTree(tuple), Schedules.class));
		}
		return slots;
	}

	public static List<Schedules> scheduleRepair(
			Calendar day, 
			String cid, 
			String fault, 
			String mechanic,
			String license
			) throws SQLException, ParseException {
		long slotsReq = Recommends.timeSlotsRequired(fault, license);
		while (true) {
			do {
				day.add(Calendar.DAY_OF_MONTH, 1);
			} while (day.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || 
					day.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
			if (alreadyScheduled(day, license)) {
				continue;
			}
			List<Map<String,String>> availability = getAvailableMechanics(day, cid, mechanic, Long.toString(slotsReq));
			
			// first, check if the mechanic is completely free on that day
			String scheduledMechanic = mechanicFree(availability);
			if (scheduledMechanic != null) {
				return generateRepairSchedules(license, Utils.formatDay(day), "08:00", scheduledMechanic, fault);
			}
			// else this mechanic is available, but not fully available.
			// need to check if they have enough *consecutive* timeslots available.
			for (Map<String,String> availableMechanic : availability) {
				String startingTime = getStartingTime(availableMechanic, day, slotsReq);
				if (startingTime != null) {
					scheduledMechanic = availableMechanic.get("eid");
					return generateRepairSchedules(license, Utils.formatDay(day), startingTime, scheduledMechanic, fault);
				}
			}
		}
		
	}

	private static boolean alreadyScheduled(Calendar day, String license) throws SQLException {
		return ((Utils.select("SELECT * FROM ServiceAppointment WHERE apptDate = ? AND license = ?", 
				new String[] {Utils.formatDay(day), license})).size()) > 0;
	}

	public static String formatOption(List<Schedules> option) throws SQLException {
		String date = option.get(0).getDateScheduled();
		String time = option.get(0).getTimeSlot();
		Employee mechanic = new Employee(option.get(0).getMechanic());
		mechanic.load();
		String mechanicName = mechanic.getName();
		
		return ("Schedule appointment with " + mechanicName + " at " + time + " on " + date);
	}

	public static List<Schedules> rescheduleAppointment(ServiceAppointment appt, Calendar day, String cid, boolean maintenance) throws ParseException, NumberFormatException, SQLException {
		List<Schedules> slotsBooked = timeSlotsBooked(appt.getLicense(), appt.getApptDate());
		long slotsReq = slotsBooked.size();
		while (true) {
			do {
				day.add(Calendar.DAY_OF_MONTH, 1);
			} while (day.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || 
					day.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
			if ((maintenance && !canScheduleMaintenance(day, cid, slotsReq)) || alreadyScheduled(day, appt.getLicense())) {
				continue;
			}
			List<Map<String,String>> availability = getAvailableMechanics(day, cid, appt.getPreferredMechanic(), 
					Long.toString(slotsReq));
			
			// first, check if the mechanic is completely free on that day
			String scheduledMechanic = mechanicFree(availability);
			if (scheduledMechanic != null) {
				return rescheduleServices(slotsBooked, Utils.formatDay(day), "08:00", scheduledMechanic);
			}
			// else this mechanic is available, but not fully available.
			// need to check if they have enough *consecutive* timeslots available.
			for (Map<String,String> availableMechanic : availability) {
				String startingTime = getStartingTime(availableMechanic, day, slotsReq);
				if (startingTime != null) {
					scheduledMechanic = availableMechanic.get("eid");
					return rescheduleServices(slotsBooked, Utils.formatDay(day), startingTime, scheduledMechanic);
				}
			}
		}
	}


}
