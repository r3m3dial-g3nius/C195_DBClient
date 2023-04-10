package DAO;

import Helper.DBConnection;
import Models.Country;
import Models.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;


/**
 * This class handles database interactions with Division.
 */
public class DBDivisions {

    /**
     * Returns list of all divisions in database.
     *
     * @return list of all divisions
     */
    public static ObservableList<Division> getAllDivisions()
    {
        ObservableList<Division> divisionList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM first_level_divisions";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");

                Division d = new Division(divisionID, divisionName, countryId);
                divisionList.add(d);
            }
        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        return divisionList;
    }


    /**
     * Returns Division object from argument specifying Division ID (int)
     *
     * @param divisionID ID of division
     * @return Division object
     */
    public static Division getDivision(int divisionID)
    {
        try
        {
            System.out.println("Getting Division...");

            String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = " + divisionID;
            String divisionName = "";
            int countryID = 0;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                divisionName = rs.getString("Division");
                countryID = rs.getInt("Country_ID");

            }

            Division d = new Division(divisionID, divisionName, countryID);

            return d;
        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        return null;
    }

//  ----------------------------------   IS THIS NEEDED?   ------------------------------------
//    /**
//     * This method outputs date country was created in database
//     */
//    public static void checkDateConversions()
//    {
//        System.out.println("Countries Date test\n");
//
//        String sql = "SELECT Create_Date FROM countries";
//
//        try
//        {
//            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next())
//            {
//                Timestamp ts = rs.getTimestamp("Create_Date");
//                System.out.println("CD: " + ts.toLocalDateTime().toString());
//            }
//            System.out.println();
//        }
//
//        catch (SQLException throwables)
//        {
//            throwables.printStackTrace();
//        }
//    }
//  ---------------------------------------------------------------------------------------------






}
