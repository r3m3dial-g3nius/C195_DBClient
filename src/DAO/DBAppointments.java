package DAO;

import Controller.LoginScreenController;
import Helper.DBConnection;
import Models.Appointment;
import Models.ReportByDivision;
import Models.ReportByMonthType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.Calendar;

public class DBAppointments {

    /**
     * Returns list of all appointments in database
     *
     * @return list of Appointment objects
     */
    public static ObservableList<Appointment> getAllAppointments()
    {
        ObservableList<Appointment> appointmentsList = FXCollections.observableArrayList();

        try
        {
            String sql = "SELECT * FROM appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int apptID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");

                //   --------------------------------   type LocalDateTime?   ----------------------------------------
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();

                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");


                Appointment newAppointment = new Appointment(apptID, title, description, location, type, start, end, customerID, userID, contactID);

                appointmentsList.add(newAppointment);
            }
        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        return appointmentsList;
    }


    /**
     * Queries database for number of customers by division, creates ReportsByDivision object and adds to Observable list of same type.
     * @return list of ReportsByDivision objects
     * @throws SQLException
     */
    public static ObservableList<ReportByDivision> getCustomersByDivision() throws SQLException {
        ObservableList<ReportByDivision> customersByDivision = FXCollections.observableArrayList();

        String sql = "SELECT first_level_divisions.Division, count(*) AS Number_of_Customers FROM customers " +
                "INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID " +
                "WHERE  customers.Division_ID = first_level_divisions.Division_ID " +
                "GROUP BY first_level_divisions.Division_ID ORDER BY first_level_divisions.Division";

        try
        {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                String division = rs.getString(1);
                int customerCount = rs.getInt(2);

                ReportByDivision rd = new ReportByDivision(division, customerCount);

                customersByDivision.add(rd);
            }

        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        return customersByDivision;

    }

    public static ObservableList<ReportByMonthType> getReportsByMonthType()
    {
        ObservableList<ReportByMonthType> numAppointmentsMonthType = FXCollections.observableArrayList();

        String sql = "SELECT MONTHNAME(Start) AS Month_Name, Type, count(*) AS Type_Count, month(Start) AS Month " +
                "FROM appointments GROUP BY Month, Month_Name, Type ORDER BY Month";

        try
        {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                String month = rs.getString(1);
                String type = rs.getString(2);
                int count = rs.getInt(3);
                int monthNum = rs.getInt(4);

                ReportByMonthType r = new ReportByMonthType(month, type, count);

                numAppointmentsMonthType.add(r);

            }
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        return numAppointmentsMonthType;
    }


    /**
     * filters appointments in tableview based on user input drop down values for time and contact
     *
     * @param time time specification - All, Current Week, or Current Month
     * @param contact name of contact
     * @return Observable list of filtered appointments based on user input from drop down boxes
     * @throws NullPointerException
     */
    public static ObservableList<Appointment> getFilteredAppointments(String time, String contact) throws NullPointerException
    {
        ObservableList<Appointment> allAppointments = DBAppointments.getAllAppointments();
        ObservableList<Appointment> tempAppointments = FXCollections.observableArrayList();
        ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();

        //   ----->   find current week number of current year   <-----
        Month thisMonth = LocalDateTime.now().getMonth();

        LocalDateTime currentDateTime = LocalDateTime.now();
        int currentWeekOfYear = currentDateTime.get(ChronoField.ALIGNED_WEEK_OF_YEAR);

//        System.out.println(currentWeekOfYear);    //  ******  TEST

        //   ----->   establish current month start/end   <-----
        LocalDateTime startCurrentMonth = LocalDateTime.of(LocalDateTime.now().getYear(), thisMonth, 1, 0, 0);
        LocalDateTime endCurrentMonth = LocalDateTime.of(LocalDate.now().getYear(), thisMonth, thisMonth.maxLength(), 23, 59);

//        LocalDateTime startCurrentWeek = LocalDateTime.now(ZoneId.systemDefault()).with(DayOfWeek.SUNDAY);
//        LocalDateTime endCurrentWeek = LocalDateTime.now(ZoneId.systemDefault()).with(DayOfWeek.SATURDAY);

//        LocalDateTime startCurrentMonth = LocalDateTime.now().minusMonths(1);
//        LocalDateTime endCurrentMonth = LocalDateTime.now().plusMonths(1);

//        LocalDateTime startCurrentWeek = LocalDateTime.now().minusWeeks(1);
//        LocalDateTime endCurrentWeek = LocalDateTime.now().plusWeeks(1);

//        System.out.println(time);           // -----   test
//        System.out.println(contact);

        //   ----->   no filters selected   <-----
        if (time == null && contact == null)
        {
            return allAppointments;
        }

        //   ----->   only contact filter selected   <-----
        else if (time == null && contact != null)
        {
            for (Appointment a : allAppointments)
            {
                if (a.getContactName(a.getContactID()).equals(contact))
                {
                    filteredAppointments.add(a);
                }
            }

            return filteredAppointments;
        }

        //   ----->   only time filter selected   <-----
        else if (time != null && contact == null)
        {

//            if (time.equals("Current Week"))
//            {
//                for (Appointment a : allAppointments)
//                {
//                    if (a.getAppointmentStart().isAfter(startCurrentWeek) && a.getAppointmentStart().isBefore(endCurrentWeek)) {
//                        filteredAppointments.add(a);
//                    }
//                }
//
//                return filteredAppointments;
//            }


            if (time.equals("Current Week"))
            {
                for (Appointment a : allAppointments)
                {
                    //   ********************************************
                    if (a.getAppointmentStart().get(ChronoField.ALIGNED_WEEK_OF_YEAR) == currentWeekOfYear)
                    {
                        filteredAppointments.add(a);
                    }
                }

                return filteredAppointments;
            }

            else if (time.equals("Current Month"))
            {
                for (Appointment a : allAppointments)
                {
                    if (a.getAppointmentStart().isAfter(startCurrentMonth) && a.getAppointmentStart().isBefore(endCurrentMonth))
                    {
                        filteredAppointments.add(a);
                    }
                }
                return filteredAppointments;
            }

            else
            {
                return allAppointments;
            }
        }

        //   ----->   if both filters selected   <-----
        else if (time != null && contact != null)
        {
            tempAppointments.clear();           //    -----   test

            // -----   add appointments with contact name to tempAppointments   -----
            for (Appointment a : allAppointments)
            {
                if (a.getContactName(a.getContactID()).equals(contact))
                {
                    tempAppointments.add(a);
                }
            }

            System.out.println("tempAppointments size = " + tempAppointments.size());       //   ----  test

            //  -----   filter tempAppointments for current week   -----
            if (time.equals("Current Week"))
            {
                for (Appointment a : tempAppointments)
                {
                    if (a.getAppointmentStart().get(ChronoField.ALIGNED_WEEK_OF_YEAR) == currentWeekOfYear)
                    {
                        filteredAppointments.add(a);
                    }
                }

                return filteredAppointments;
            }

            //  -----   filter tempAppointments for current month   -----
            else if (time.equals("Current Month"))
            {
                for (Appointment a : tempAppointments)
                {
                    if (a.getAppointmentStart().isAfter(startCurrentMonth) && a.getAppointmentStart().isBefore(endCurrentMonth))
                    {
                        filteredAppointments.add(a);
                    }
                }

                return filteredAppointments;
            }

        }

        return filteredAppointments;        //  errors if not present
    }


    /**
     * inserts new appointment into appointments table in database
     *
     * @param title title of appointment
     * @param description description of appointment
     * @param location location of appointment
     * @param type type of appointment
     * @param start starting time of appointment
     * @param end ending time of appointment
     * @param customer_ID customer ID number
     * @param user_ID user ID number
     * @param contact_ID contact ID number
     *
     * @throws SQLException
     */
    public static void addNewAppointment(String title, String description, String location,
                                         String type, Timestamp start, Timestamp end, int customer_ID, int user_ID,
                                         int contact_ID) throws SQLException
    {

        // Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID
        String sql = "INSERT INTO appointments VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        // 1 Appointment_ID - NULL
        // 2 Title
        // 3 Description
        // 4 Location
        // 5 Type
        // 6 Start
        // 7 End
        // 8 Create_Date
        // 9 Created_By
        // 10 Last_Update
        // 11 Last_Updated_By
        // 12 Customer_ID
        // 13 User_ID
        // 14 Contact_ID

        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, start);
        ps.setTimestamp(6, end);
        ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(8, LoginScreenController.authorizedUser.getUserName());
        ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
        ps.setString(10, LoginScreenController.authorizedUser.getUserName());
        ps.setInt(11, customer_ID);
        ps.setInt(12, user_ID);
        ps.setInt(13, contact_ID);

//        System.out.println(sql);        //  TEST PRINT

        ps.execute();

    }

    /**
     * Modifies existing Appointment in appointments table per user input
     *
     * @param title title of appointment
     * @param description description of appointment
     * @param location location of appointment
     * @param type type of appointment
     * @param startTS start date/time of appointment
     * @param endTS end date/time of appointment
     * @param customerID customer ID number
     * @param userID user ID number
     * @param contactID contact ID number
     * @param appointmentID appointment ID number (unique)
     * @throws SQLException
     */
    public static void modifyAppointment(String title, String description, String location, String type, Timestamp startTS, Timestamp endTS, int customerID, int userID, int contactID, int appointmentID) throws SQLException
    {
        //  SQL Cols for reference VVV
        //  Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID

        String sql = ("UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = NOW(), Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?");

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, startTS);
        ps.setTimestamp(6, endTS);
        ps.setString(7, LoginScreenController.authorizedUser.getUserName());
        ps.setInt(8, customerID);
        ps.setInt(9, userID);
        ps.setInt(10, contactID);
        ps.setInt(11, appointmentID);

        System.out.println(sql);        //  TEST PRINT

        ps.execute();

    }

    /**
     * Deletes Appointment specified by appointment ID number from appointments table in database
     * @param appointmentID unique ID number of appointment
     * @throws SQLException
     */
    public static void deleteAppointment(int appointmentID) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ps.setInt(1, appointmentID);

        ps.execute();
    }

    public static void deleteCustomerAppointments(int customerID) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Customer_ID = ?";

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ps.setInt(1, customerID);

        ps.execute();
    }



}
