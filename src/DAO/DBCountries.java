package DAO;

import Helper.DBConnection;
import Models.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;


/**
 * This class handles database interactions with countries.
 */
public class DBCountries {

    /**
     * Returns list of all countries in database.
     *
     * @return list of all countries
     */
    public static ObservableList<Country> getAllCountries()
    {
        ObservableList<Country> countryList = FXCollections.observableArrayList();    // here is where you left off

        try {
            String sql = "SELECT * FROM countries";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");

                Country c = new Country(countryId, countryName);
                countryList.add(c);
            }
        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        return countryList;
    }


    /**
     * Returns country object from argument specifying country ID (int)
     *
     * @param countryID ID of country
     * @return country object
     */
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


    /**
     * This method outputs date country was created in database
     */
    public static void checkDateConversions()
    {
        System.out.println("Countries Date test\n");

        String sql = "SELECT Create_Date FROM countries";

        try
        {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                Timestamp ts = rs.getTimestamp("Create_Date");
                System.out.println("CD: " + ts.toLocalDateTime().toString());
            }
            System.out.println();
        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }
}
