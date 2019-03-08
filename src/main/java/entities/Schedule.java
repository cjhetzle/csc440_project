package main.java.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Schedule {
    private String license;
    private String dateScheduled;
    private String timeSlot;
    private String mechanic;
    private String mechanicName;
    private String cid;
    private String serviceID;
    private String serviceName;


    public String getServiceID () {
        return serviceID;
    }

    public void setServiceID (String serviceID) {
        this.serviceID = serviceID;
    }

    public String getLicense () {
        return license;
    }

    public void setLicense (String license) {
        this.license = license;
    }

    public String getDateScheduled () {
        return dateScheduled;
    }

    public void setDateScheduled (String dateScheduled) {
        this.dateScheduled = dateScheduled;
    }

    public String getTimeSlot () {
        return timeSlot;
    }

    public void setTimeSlot (String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getMechanic () {
        return mechanic;
    }

    public void setMechanic (String mechanic) {
        this.mechanic = mechanic;
    }

    public String getMechanicName () {
        return mechanicName;
    }

    public void setMechanicName (String mechanicName) {
        this.mechanicName = mechanicName;
    }

    public String getCid () {
        return cid;
    }

    public void setCid (String cid) {
        this.cid = cid;
    }

    public String getServiceName () {
        return serviceName;
    }

    public void setServiceName (String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * Return the time 15 minutes after the specified timeSlot.
     * Used to show when a service will end
     * @return timeSlot + 15 min
     */
    public String getNextTimeSlot () {
        int hour = Integer.parseInt(timeSlot.split(":")[0]);
        int minute = Integer.parseInt(timeSlot.split(":")[1]);
        if (minute + 15 >= 60) {
            hour = (hour + 1) % 24;
        }
        return String.format("%02d:%02d", hour, (minute + 15) % 60);
    }

    /**
     * Returns the status of the service scheduled for this time slot
     * @param startTime If the service spans multiple timeSlots (format = yyyy-MM-dd H:mm)
     * @return Pending || Ongoing || Complete
     */
    public String getStatus (String startTime, String endTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:mm");
        Date now = new Date();
        Date start;
        Date end;
        try {
            start = format.parse(startTime);
            end = format.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return "INVALID";
        }
        if (now.after(end))
            return "Complete";
        if (now.after(start) && now.before(end))
            return "Ongoing";
        if (now.before(start))
            return "Pending";
        return "INVALID";
    }
}
