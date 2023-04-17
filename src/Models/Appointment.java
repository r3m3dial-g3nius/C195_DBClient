package Models;

import DAO.DBContacts;
import DAO.DBCustomers;
import DAO.DBUsers;
import javafx.collections.ObservableList;

/**
 * This class manages Appointment data
 */
public class Appointment {

    private int appointmentID;
    private String appointmentTitle;
    private String appointmentDescription;
    private String appointmentLocation;
    private String appointmentType;
    private String appointmentStart;
    private String appointmentEnd;
    private int customerID;
    private int userID;
    private int contactID;
    private String customerName;
    private String userName;
    private String contactName;

    /**
     * Contructor for Appointment class
     *
     * @param appointmentID Appointment ID number
     * @param appointmentTitle Title of appointment
     * @param appointmentDescription Description of appointment
     * @param appointmentLocation Location of appointment
     * @param appointmentType Type of appointment
     * @param appointmentStart Start time of appointment
     * @param appointmentEnd End time of appointment
     * @param customerID Customer ID number
     * @param userID User ID number
     * @param contactID Contact ID number
     */
    public Appointment(int appointmentID, String appointmentTitle, String appointmentDescription, String appointmentLocation, String appointmentType, String appointmentStart, String appointmentEnd, int customerID, int userID, int contactID)
    {
        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.appointmentStart = appointmentStart;
        this.appointmentEnd = appointmentEnd;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;

        this.customerName = getCustomerName(customerID);
        this.userName = getUserName(userID);
        this.contactName = getContactName(contactID);
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }

    public String getAppointmentLocation() {
        return appointmentLocation;
    }

    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getAppointmentStart() {
        return appointmentStart;
    }

    public void setAppointmentStart(String appointmentStart) {
        this.appointmentStart = appointmentStart;
    }

    public String getAppointmentEnd() {
        return appointmentEnd;
    }

    public void setAppointmentEnd(String appointmentEnd) {
        this.appointmentEnd = appointmentEnd;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }


    public String getCustomerName(int customerID)
    {
        ObservableList<Customer> allCustomers = DBCustomers.getAllCustomers();
        String customerName = "";

        try
        {
            for (Customer c : allCustomers)
            {
                if (c.getCustomerID() == customerID)
                {
                    customerName = c.getCustomerName();
                }
            }
        }

        catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        return customerName;
    }


    public String getUserName(int userID)
    {
        ObservableList<User> allUsers = DBUsers.getAllUsers();
        String user_Name = "";

        try
        {
            for (User u : allUsers)
            {
                if (u.getUserID() == userID)
                {
                    user_Name = u.getUserName();
                }
            }
        }

        catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        return user_Name;
    }


    public String getContactName(int contactID)
    {
        ObservableList<Contact> allContacts = DBContacts.getAllContacts();
        String contact_Name = "";

        for (Contact c : allContacts)
        {
            if (c.getContactID() == contactID)
            {
                contact_Name = c.getContactName();
            }
        }

        return contact_Name;
    }

}
