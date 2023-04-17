package DAO;

import Helper.DBConnection;
import Models.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBContacts {

    public static ObservableList<Contact> getAllContacts()
    {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();

        try
        {
            String sql = "SELECT * FROM contacts";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int iD = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                Contact newContact = new Contact(iD, name, email);
                allContacts.add(newContact);
            }

        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        return allContacts;
    }

}
