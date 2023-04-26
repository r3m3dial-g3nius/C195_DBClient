package Models;

import DAO.DBAppointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * This class manages the User data
 */
public class User {

    private int userID;
    private String userName;
    private String password;

    /**
     * Constructor for new User object
     *
     * @param userId ID of user
     * @param userName Name of user
     * @param password User's password
     */
    public User(int userId, String userName, String password)
    {
        this.userID = userId;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Returns the user ID
     *
     * @return ID of user
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets user ID
     *
     * @param userID ID of user
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Returns user name
     *
     * @return name of user
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets username
     *
     * @param userName name of user
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }


    //   ------------------------------------   passwords....private?   ----------------------------------

    /**
     * Returns user password
     *
     * @return password of user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets user password
     *
     * @param password password of user
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * gets list of Appointment objects with matching user ID
     *
     * @return list of Appointment objects, (all of this user's appointments)
     */
    public ObservableList<Appointment> getUserAppointmentList()
    {
        ObservableList<Appointment> allAppointments = DBAppointments.getAllAppointments();
        ObservableList<Appointment> thisUserAppointments = FXCollections.observableArrayList();

        for (Appointment a : allAppointments)
        {
            if (a.getUserID() == this.userID)
            {
                thisUserAppointments.add(a);
            }
        }

        return thisUserAppointments;

    }

    /**
     * determines if this user has an appointment scheduled to start in the next 15 min from current time
     *
     * @return Appointment object with start time in 15 min or less from current time, null if next Appointment start
     * time is > 15 min away
     */
    public Appointment hasAppointmentSoon()
    {
        ObservableList<Appointment> thisUserAppointments = getUserAppointmentList();

        for (Appointment a : thisUserAppointments)
        {
            if (a.getAppointmentStart().isAfter(LocalDateTime.now()) && a.getAppointmentStart().isBefore(LocalDateTime.now().plusMinutes(15)))
            {
                return a;
            }
        }

        return null;
    }

}
