package main.java.entities;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import main.java.backend.Utils;

@SuppressWarnings("FieldCanBeLocal")
public class Payrolls implements Entity {
    // Information for a new payroll
    // NOT BEING USED AT THE MOMENT
    private String datePaid;
    private String payPeriod;
    private String eid;
    private String name;
    private int rate;
    private String frequency;
    private int payableTime;
    private int earning;
    private int ytdEarning;
    // end of info

    // Information for the list of payrolls
    private short count;
    private List<Payroll> payrolls;
    private boolean dirtyBit;
    // end of info

    // Formatting information
    @SuppressWarnings("FieldCanBeLocal")
    private short colWidth1 = 10, // set here
    colWidth2 = 12, // set here
    colWidth3 = 10, // set here
    colWidth4 = 9, // set here
    colWidth5 = 8, // set at load once
    colWidth6 = 13, // set at load once
    colWidth7 = 12, // set at load
    colWidth8 = 8, // set at load
    colWidth9 = 12; // set at load
    // end of info

    /**
     * Construct the Payrolls class which manages multiple payrolls for specific
     * employees and can add or update the existing list. This means it is not
     * necessary for other classes to interact directly with individual payrolls
     * except when inserting a new one.
     * @param eid The employee that this list will be linked to
     */
    public Payrolls(String eid) { this.eid = eid; payrolls = new ArrayList<>(); dirtyBit = true; }

    public String getEid () { return eid; }

    public short size () { return count; }

    public List<String> toStringList() {
        List<String> output = new ArrayList<>();
        output.add(Utils.padRight("Date Paid", colWidth1) + " | " +
                Utils.padRight("Period Start", colWidth2) + " | " +
                Utils.padRight("Period End", colWidth3) + " | " +
                Utils.padRight("EID", colWidth4) + " | " +
                Utils.padRight("Pay Rate", colWidth5) + " | " +
                Utils.padRight("Pay Frequency", colWidth6) + " | " +
                Utils.padLeft("Payable Time", colWidth7) + " | " +
                Utils.padLeft("Earnings", colWidth8) + " | " +
                Utils.padLeft("YTD Earnings", colWidth9) + " | " +
                name + "\n");

        String[] array;
        short i = 0;
        for ( ; i < count; ++i ) {
            array = payrolls.get(i).toStringArray();
            output.add(Utils.padRight(array[0], colWidth1) + " | " +
                    Utils.padRight(array[1], colWidth2) + " | " +
                    Utils.padRight(array[2], colWidth3) + " | " +
                    Utils.padRight(array[3], colWidth4) + " | " +
                    Utils.padRight(array[4], colWidth5) + " | " +
                    Utils.padRight(frequency, colWidth6) + " | " +
                    Utils.padLeft(array[5], colWidth7) + " | " +
                    Utils.padLeft(array[6], colWidth8) + " | " +
                    Utils.padLeft(array[7], colWidth9) + "\n");
        }
        return output;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        short i = 0;
        for ( ; i < count; ++i ) {
            sb.append(payrolls.get(i));
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Should be implemented in a later branch
     */
    public void setFields() {


        dirtyBit = false;
    }

    /**
     * This method should be called after load, to check if there are any missing paychecks
     * (e.g. if a new pay period has ended since the last paycheck).
     *
     * Here, we assume that pay periods end on the 14th and last of a month, and paychecks
     * are paid on the following day (the 15th and the 1st).
     * @throws SQLException
     * @throws ParseException
     */
    public void generate() throws SQLException, ParseException {
		Employee e = new Employee(this.eid);
		e.load();

    	String lastPaid;
    	if (payrolls.size() > 0) {
    		lastPaid = payrolls.get(payrolls.size() - 1).getDatePaid();
    	} else {
    		lastPaid = e.getStartDate();
    	}
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dateLastPaid = format.parse(lastPaid);
        Calendar dayLastPaid = Calendar.getInstance();
        dayLastPaid.setTime(dateLastPaid);
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);

        dayLastPaid = nextPayday(dayLastPaid);
        while (dayLastPaid.compareTo(today) <= 0) {
        	String datePaid = format.format(dayLastPaid.getTime());

            Calendar periodEndDay = Calendar.getInstance();
        	periodEndDay.setTime(dayLastPaid.getTime());
        	periodEndDay.add(Calendar.DATE, -1);
        	String periodEnd = format.format(periodEndDay.getTime());

            Calendar periodStartDay = Calendar.getInstance();
            periodStartDay.setTime(periodEndDay.getTime());
        	if (periodEndDay.get(Calendar.DAY_OF_MONTH) == 14) {
        		periodStartDay.set(Calendar.DAY_OF_MONTH, 1);
        	} else {
        		periodStartDay.set(Calendar.DAY_OF_MONTH, 15);
        	}
        	String periodStart = format.format(periodStartDay.getTime());

        	String eid = e.getEid();

        	String rate = e.getPay();

        	double payableUnits = getPayableTime(e, periodStartDay, periodEndDay);
        	String payableTime = Double.toString(payableUnits);

        	String earning;
        	if (e.getCompensationFrequency().equals("monthly")) {
        		earning = Double.toString(Double.parseDouble(rate) / 2.0);
        	} else {
        		earning = Double.toString(Double.parseDouble(rate) * payableUnits);
        	}

        	String ytdEarning = getYtdEarning(earning, dayLastPaid);

        	Payroll payroll = new Payroll ( datePaid, periodStart, periodEnd, eid,
                    rate, payableTime,
                    earning, ytdEarning);

        	payrolls.add(payroll);
        	dirtyBit = false;
        	this.count++;

        	dayLastPaid = nextPayday(dayLastPaid);
        }
    }

    private String getYtdEarning(String earning, Calendar day) throws ParseException {
    	if (payrolls.size() == 0) {
    		return earning;
    	}

    	Payroll lastPaycheck = payrolls.get(payrolls.size() - 1);
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar lastPaid = Calendar.getInstance();
    	lastPaid.setTime(format.parse(lastPaycheck.getDatePaid()));
    	if (lastPaid.get(Calendar.YEAR) != day.get(Calendar.YEAR)) {
    		return earning;
    	}

    	return Double.toString(Double.parseDouble(earning) + Double.parseDouble(lastPaycheck.getYtdEarning()));
    }

    private double getPayableTime(Employee e, Calendar start, Calendar end) throws SQLException {
		if (e.getCompensationFrequency().equals("monthly")) {
			return end.get(Calendar.DAY_OF_MONTH) - start.get(Calendar.DAY_OF_MONTH) + 1;
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		List<Map<String,String>> result = Utils.select("SELECT COUNT(*)/4 as hours FROM Schedules "
				+ "WHERE mechanic = ? AND dateScheduled >= ? AND dateScheduled <= ?",
				new String[] {e.getEid(), format.format(start.getTime()), format.format(end.getTime())});

		return (result.size() > 0) ? Double.parseDouble(result.get(0).get("hours")) : 0.0;
    }

    private Calendar nextPayday(Calendar date) {
    	if (date.get(Calendar.DAY_OF_MONTH) < 15) {
    		date.set(Calendar.DAY_OF_MONTH, 15);
    	} else {
    		date.set(Calendar.DAY_OF_MONTH, 1);
    		date.add(Calendar.MONTH, 1);
    	}
    	return date;
    }

    /**
     * This is a necessary function to call after initializing the function
     * to load the entries of a specific employee into the list here.
     * @throws SQLException I don't know
     */
    @Override
    public void load() throws SQLException {
        // PayrollHistory is a list of MySQL row entries
        List<Map<String,String>> payrollHistory =
                Utils.select("SELECT * FROM Paycheck WHERE eid = ? ORDER BY datePaid;", new String[] {eid});

        Employee employee = new Employee(eid);
        employee.load();
        name = employee.getName();
        frequency = employee.getCompensationFrequency();

        // formatting information
        /*short temp1,
                temp2 = (short)payrollHistory.get(0).get("rate").length(),
                temp3 = (short)employee.getCompensationFrequency().length();
        if (temp2 > colWidth5)
            colWidth5 = temp2;
        if (temp3 > colWidth6)
            colWidth6 = temp3;*/
        // end of formatting information

        // initialize vars for loop
        Map<String,String> row;
        short i = 0;
        count = (short)payrollHistory.size();

        for ( ; i < count ; ++i ) {
            row = payrollHistory.get(i);

            // Formatting information
            /*temp1 = (short)row.get("payableTime").length();
            temp2 = (short)row.get("earning").length();
            temp3 = (short)row.get("ytdEarning").length();
            if (temp1 > colWidth7)
                colWidth7 = temp1;
            if (temp2 > colWidth8)
                colWidth8 = temp2;
            if (temp3 > colWidth9)
                colWidth9 = temp3;*/
            // end of formatting info

            payrolls.add(new Payroll(row.get("datePaid"),
                    row.get("periodStart"),
                    row.get("periodEnd"),
                    row.get("eid"),
                    row.get("rate"),
                    row.get("payableTime"),
                    row.get("earning"),
                    row.get("ytdEarning")));
        }
    }

    /**
     * Delete and re-save this employee's paychecks;
     * @throws SQLException I also don't know
     */
    @Override
    public void save() throws SQLException {
        if (count == 0 || dirtyBit)
            return;
        short i = 0;

        Utils.insert("DELETE FROM Paycheck WHERE eid = ?;", new String[] {this.eid});

        for ( ; i < count ; ++i ) {
            Utils.insert(
                    "INSERT INTO Paycheck VALUES (?,?,?,?,?,?,?,?)",
                    payrolls.get(i).toStringArray()
            );
        }
    }

    @Override
    public void insert() throws SQLException {
        if (dirtyBit)
            throw new IllegalArgumentException("Please first input valid data for the payroll");

        // Does this have a valid date ?
        for (Payroll p: payrolls) {
            if (p.getDatePaid().equals(datePaid))
                // Has this payroll already been entered once ?
                if (p.getEarning().equals(String.valueOf(earning)))
                    throw new IllegalArgumentException("Please provide a payroll that hasn't been input already");
                else
                    throw new IllegalArgumentException("Please enter a date on the payroll that hasn't been used");
        }

        payrolls.add(new Payroll(datePaid, payPeriod, eid, name, String.valueOf(rate),
                String.valueOf(payableTime), String.valueOf(earning), String.valueOf(ytdEarning)));


        Utils.insert(
                "INSERT INTO paycheck VALUES ( ? , ? , ? , ? , ? , ? , ? )",
                payrolls.get(count).toStringArray()
        );
        dirtyBit = true;
        count++;
    }
}

class Payroll {
    private String datePaid;
    private String periodStart;
    private String periodEnd;
    private String eid;
    private String rate;
    private String payableTime;
    private String earning;
    private String ytdEarning;

    Payroll ( String datePaid, String periodStart, String periodEnd, String eid,
                     String rate, String payableTime,
                     String earning, String ytdEarning) {
        this.datePaid = datePaid;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.eid = eid;
        this.rate = rate;
        this.payableTime = payableTime;
        this.earning = earning;
        this.ytdEarning = ytdEarning;
    }

    String getEid () { return eid; }

    void setEid (String eid) { this.eid = eid; }

    String getDatePaid () { return datePaid; }

    String getEarning () { return earning; }



    String getPeriodStart () { return periodStart; }

    String getPeriodEnd () { return periodEnd; }

    String getRate () { return rate; }

    String getPayableTime () { return payableTime; }

    String getYtdEarning () { return ytdEarning; }

    @Override
    public String toString() {
        return datePaid + " | " +
                periodStart + " | " +
                periodEnd + " | " +
                eid + " | " +
                rate + " | " +
                payableTime + " | " +
                earning + " | " +
                ytdEarning;
    }

/**
 * This is to be parsable by the Util class and easily inserted into a table
 * @return String[] of all attributes in the format seen in the table PAYCHECK
 */
    String[] toStringArray() {
        return new String[]{datePaid,
                periodStart,
                periodEnd,
                eid,
                rate,
                payableTime,
                earning,
                ytdEarning};
    }
}
