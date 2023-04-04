package DAO;

import Helper.DBConnection;
import Models.Country;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;


/**
 * This class handles database interactions with users.
 */
public class DBUsers {

    /**
     * Returns list of all users in database
     *
     * @return list of all users in database
     */
    public static ObservableList<User> getAllUsers()
    {
        ObservableList<User> userList = FXCollections.observableArrayList();    // here is where you left off

        try {
            String sql = "SELECT * FROM users";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");

                User x = new User(userId, userName, password);
                userList.add(x);
            }
        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        return userList;
    }


    /**
     * This method returns User object from users table in database specified by user ID.
     * @param username name of user to return
     * @param userPassword password of user to return
     * @return User object
     */
    public static User getUser(String username, String userPassword)
    {
        try
        {
            System.out.println("Getting User...");

            String sql = "SELECT * FROM users WHERE User_Name = " + username + "and Password = " + userPassword;       // FIX ME filter by username and userpw
            int userID = 0;
            String userName = "";
            String password = "";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                userID = rs.getInt("User_ID");
                userName = rs.getString("User_Name");
                password = rs.getString("Password");
            }

            User x = new User(userID, userName, password);

            return x;
        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        return null;
    }


    /**
     * This method prints the date the user was created in database.
     */
    public static void checkDateConversions()
    {
        System.out.println("User Create Date test");

        String sql = "SELECT Create_Date FROM users";

        try
        {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                Timestamp ts = rs.getTimestamp("Create_Date");
                System.out.println("CD: " + ts.toLocalDateTime().toString());
            }
        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }
}
