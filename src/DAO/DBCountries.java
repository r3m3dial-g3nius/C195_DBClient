package DAO;

import Database.DBConnection;
import Models.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class DBCountries {

    public static ObservableList<Country> getAllCountries()
    {
        ObservableList<Country> countryList = FXCollections.observableArrayList();    // here is where you left off

        try {
            String sql = "SELECT * FROM countries";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");

                Country c = new Country(countryID, countryName);
                countryList.add(c);
            }
        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        return countryList;
    }


    public static Country getCountry(int countryID)
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

            Country c = new Country(countryID, countryName);
            return c;
        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        return null;
    }
}
