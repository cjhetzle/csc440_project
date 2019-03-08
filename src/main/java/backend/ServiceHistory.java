package main.java.backend;

import com.google.gson.Gson;
import main.java.entities.Schedule;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceHistory {
    /**
     * Selects rows from the Schedules table and returns Schedule objects
     * @param whereClause the where clause of the query
     * @param params the params in the query
     * @return List<Schedule>
     * @throws SQLException if the query fails
     */
    private static List<Schedule> getServiceHistory(String whereClause, String[] params) throws SQLException {
        List<Map<String,String>> rows = Utils.select(
            "SELECT Sch.*, E.name AS mechanicName, E.cid, Svc.serviceID, Svc.name AS serviceName FROM Schedules Sch "
            + "INNER JOIN Service Svc ON Sch.service = Svc.serviceID "
            + "INNER JOIN Employee E ON Sch.mechanic = E.eid "
            + whereClause + " ORDER BY Sch.dateScheduled, Sch.timeSlot, Svc.serviceID",
            params
        );
        Gson gson = new Gson();
        List<Schedule> schedules = new ArrayList<>();
        for (Map<String,String> row : rows) {
            schedules.add(gson.fromJson(gson.toJsonTree(row), Schedule.class));
        }
        return schedules;
    }

    /**
     * Display Schedules for a car
     * @param license the license of the car
     * @return List<Schedule>
     * @throws SQLException if the query fails
     */
    public static List<Schedule> getServiceHistoryForCar(String license) throws SQLException{
        return getServiceHistory(
            "WHERE Sch.license = ?",
            new String[] {license}
        );
    }

    /**
     * Display Schedules for a customer's cars that are at a given cid
     * @param customerID the customer id
     * @param cid the cid
     * @return List<Schedule>
     * @throws SQLException if the query fails
     */
    public static List<Schedule> getServiceHistoryForCustomer(String customerID, String cid) throws SQLException {
        return getServiceHistory(
            "WHERE Sch.license IN "
            + "(SELECT DISTINCT V.license FROM Vehicle V "
                + "INNER JOIN Schedules S ON V.license=S.license "
                + "INNER JOIN Employee E1 ON S.mechanic=E1.eid "
                + "WHERE V.customerID=? AND E1.cid=?)",
            new String[] {customerID, cid}
        );
    }


    public static boolean isCustomersCar(String customerID, String license) {
        try {
            return Utils.select(
                "SELECT 1 FROM Vehicle WHERE customerID = ? AND license = ?",
                new String[] {customerID, license}
            ).size() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Display Schedules for all cars that were serviced at
     * the manager's service center
     * @param cid the id of the manager's ServiceCenter
     * @return List<Schedule>
     * @throws SQLException if the query fails
     */
    public static List<Schedule> getManagerServiceHistoryForServiceCenter(String cid) throws SQLException{
        return getServiceHistory(
            "WHERE E.cid=?",
            new String[] {cid}
        );
    }
}
