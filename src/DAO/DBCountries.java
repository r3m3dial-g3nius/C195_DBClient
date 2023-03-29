package DAO;

import Database.DBConnection;
import Models.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class DBCountries {

    public static ObservableList<Countries> getAllCountries()
    {
        ObservableList<Countries> countryList = FXCollections.observableArrayList();    // here is where you left off

        try {
            String sql = "SELECT * FROM Countries";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryID = rs.getInt("CountryID");
                String countryName = rs.getString("Country");

                Countries c = new Countries(countryID, countryName);
                countryList.add(c);
            }
        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        return countryList;
    }


    public static Countries getCountry(int countryID)
    {
        try
        {
            System.out.println("Getting Country...");

            String sql = "SELECT * FROM countries WHERE Country_ID = " + countryID;
            String countryName = "";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                countryName = rs.getString("Country");
            }

            Countries c = new Countries(countryID, countryName);
            return c;
        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        return null;
    }
}
