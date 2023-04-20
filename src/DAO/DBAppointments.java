package DAO;

import Controller.LoginScreenController;
import Helper.DBConnection;
import Models.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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

//        ps.setString(1, String.valueOf(appointment_ID));
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

        System.out.println(sql);        //  TEST PRINT

        ps.execute();


    }


    public static void modifyAppointment() throws SQLException
    {
//        ObservableList<Division> divisionList = DBDivisions.getAllDivisions();
//
//        String sql = ("UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = NOW(), Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?");
//
//        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
//
//        ps.setString(1, name);
//        ps.setString(2, address);
//        ps.setString(3, postalCode);
//        ps.setString(4, phone);
//        ps.setString(5, LoginScreenController.authorizedUser.getUserName());
//        ps.setInt(6, divisionID);
//        ps.setInt(7, customerID);
//
//        System.out.println(sql);        //  TEST PRINT
//
//        ps.execute();

    }




    //      ------------------------------------------------------------------
    public static void deleteAppointment()
    {
        // code
    }




}
