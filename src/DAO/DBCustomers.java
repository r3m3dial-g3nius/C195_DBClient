package DAO;

import Helper.DBConnection;
import Models.Country;
import Models.Customer;
import Models.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * This class handles database interactions with customers.
 */
public class DBCustomers {

    /**
     * Returns list of all customers in database
     * @return list of Customer objects
     */
    public static ObservableList<Customer> getAllCustomers()
    {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try
        {
            String sql = "SELECT * FROM customers";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                int divisionID = rs.getInt("Division_ID");
                String customerPostalCode = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");

                Customer newCustomer = new Customer(customerID, customerName, customerAddress, divisionID, customerPostalCode, customerPhone);
                customerList.add(newCustomer);
            }

        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        return customerList;
    }


    public static void addNewCustomer(String customerName, String customerAddress, String postalCode, String phone, String divisionName) throws SQLException
    {
        ObservableList<Division> divisionList = DBDivisions.getAllDivisions();


        int divisionID = 0;

        for (Division d : divisionList)
        {
            if (d.getDivisionName().equals(divisionName))
            {
                divisionID = d.getDivisionID();
            }
        }

        String sql = "INSERT INTO customers VALUES (NULL, " + customerName + ", " + customerAddress + ", " + postalCode + ", " + phone + ", NOW(), TEST, NOW(), " + divisionID + ")";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        System.out.println(sql);        //  TEST PRINT

        ps.execute();

    }






}
