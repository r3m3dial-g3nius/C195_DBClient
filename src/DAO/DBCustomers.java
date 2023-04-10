package DAO;

import Helper.DBConnection;
import Models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                int customerPostalCode = rs.getInt("Postal_Code");
                String customerPhone = rs.getString("Phone");

                Customer newCustomer = new Customer(customerID, customerName, customerAddress, divisionID, customerPostalCode, customerPhone);
                customerList.add(newCustomer);





                //   -------------------   TEST   --------------------------
                System.out.println(newCustomer.getCustomerName());

                //   ^^^   THE PROBLEM IS HERE   ^^^  - only 1 customer in list



            }

        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        return customerList;
    }
}
