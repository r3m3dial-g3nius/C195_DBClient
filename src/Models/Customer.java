package Models;

import DAO.DBAppointments;
import DAO.DBCountries;
import DAO.DBDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class manages Customer data
 */
public class Customer {

    private int customerID;
    private String customerName;
    private String customerAddress;
    private int divisionID;
    private String divisionName;
    private String customerCountry;
    private String customerPostalCode;
    private String customerPhone;

    /**
     * Constructor for Customer class
     * @param customerID ID of customer
     * @param customerName Name of customer
     * @param customerAddress Address of customer
     * @param divisionID Customer 1st Division ID
     * @param customerPostalCode Postal code of customer
     * @param customerPhone Phone number of customer
     */
    public Customer(int customerID, String customerName, String customerAddress, int divisionID, String customerPostalCode, String customerPhone)
    {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.divisionID = divisionID;
        this.divisionName = getDivisionName();
        this.customerCountry = getCountryName(divisionID);
        this.customerPostalCode = customerPostalCode;
        this.customerPhone = customerPhone;
    }

    /**
     * gets ID number of customer
     * @return customer ID number
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * sets value of customerID
     * @param customerID ID number of customer
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * gets name of customer
     * @return name of customer
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * sets customerName
     * @param customerName name of customer
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * gets customer address
     * @return address of customer
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * sets customer address
     * @param customerAddress address of customer
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     * gets the division ID number
     * @return ID number of division
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * sets division ID number
     * @param divisionID ID number of division
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * gets country name
     * @return name of customer country
     */
    public String getCustomerCountry() {
        return customerCountry;
    }

    /**
     * sets customer country name
     * @param customerCountry name of customer country
     */
    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

    /**
     * gets customer postal code
     * @return postal code of customer
     */
    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    /**
     * sets customer postal code
     * @param customerPostalCode postal code of customer
     */
    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    /**
     * gets customer phone number
     * @return phone number of customer
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     * sets customer phone number
     * @param customerPhone phone number of customer
     */
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    /**
     * gets name of customer country
     * @param divisionID division ID number
     * @return name of customer country
     */
    private String getCountryName(int divisionID)
    {
        String countryName = "";

        try
        {
            Division d = DBDivisions.getDivision(divisionID);
            int countryID = d.getCountryID();
            Country c = DBCountries.getCountry(countryID);
            countryName = c.getCountryName();
        }

        catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        return countryName;
    }


    /**
     * gets name of customer division
     * @return name of division
     */
    public String getDivisionName()
    {
        String divisionName = "";

        try
        {
            Division d = DBDivisions.getDivision(divisionID);
            divisionName = d.getDivisionName();
        }

        catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        return divisionName;
    }






    //---------------------------------------    FOR DELETE APPOINTMENT on CUSTOMER SCREEN?   ---------------------------------
    /**
     * checks all appointments, returns true if this customer ID is listed in appointments table under Customer_ID
     * @returns true if customer ID number is found, false if customer ID is not found in appointments table
     */
    public boolean hasAppointments()
    {
        ObservableList<Appointment> allAppointments = DBAppointments.getAllAppointments();
        for (Appointment a : allAppointments)
        {
            if (this.getCustomerID() == a.getCustomerID())
            {
                return true;
            }
        }
        return false;
    }

    public ObservableList<Appointment> getCustomerAppointmentList()
    {
        ObservableList<Appointment> allAppointments = DBAppointments.getAllAppointments();
        ObservableList<Appointment> thisCustomersAppointments = FXCollections.observableArrayList();

        for (Appointment a : allAppointments)
        {
            if (a.getCustomerID() == this.customerID)
            {
                thisCustomersAppointments.add(a);
            }
        }
        return thisCustomersAppointments;
    }


}
