package DAO;

import Helper.DBConnection;
import Models.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public static void addNewAppointment() throws SQLException
    {
//        ObservableList<Division> divisionList = DBDivisions.getAllDivisions();
//
//        int divisionID = 0;
//
//        for (Division d : divisionList)
//        {
//            if (d.getDivisionName().equals(divisionName))
//            {
//                divisionID = d.getDivisionID();
//            }
//        }

//        String sql = "INSERT INTO customers VALUES (NULL, '" + customerName + "', '" + customerAddress + "', '" + postalCode + "', '" + phone + "', NOW(), 'TEST', NOW(), 'TEST', " + divisionID + ")";







//        String sql = "INSERT INTO customers VALUES (NULL, ?, ?, ?, ?, NOW(), ?, NOW(), ?, ?)";
//
//        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);






        //        ps.setString(1, customerName);
//        ps.setString(2, customerAddress);
//        ps.setString(3, postalCode);
//        ps.setString(4, phone);
//        ps.setString(5, LoginScreenController.authorizedUser.getUserName());
//        ps.setString(6, LoginScreenController.authorizedUser.getUserName());
//        ps.setInt(7, divisionID);








//        System.out.println(sql);        //  TEST PRINT
//
//        ps.execute();

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
