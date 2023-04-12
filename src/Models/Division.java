package Models;

import DAO.DBCountries;

/**
 * this class manages first class divisions data
 */
public class Division {

    private int divisionID;
    private String divisionName;
    private int countryID;

    /**
     * Constructor for Division class
     * @param divisionID ID number of division
     * @param divisionName Name of Division
     * @param countryID ID number of Division country
     */
    public Division(int divisionID, String divisionName, int countryID)
    {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }

    /**
     * gets division ID number
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
     * gets division name
     * @return name of division
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * sets division name
     * @param divisionName name of division
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * gets country ID number
     * @return ID number of country
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * sets country ID number
     * @param countryID ID number of country
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * gets name of country where division is located
     * @param countryID ID number of country
     * @return name of country
     */
    public String getCountryName(int countryID)
    {
        String countryName = "";

        try
        {
            Country c = DBCountries.getCountry(countryID);
            countryName = c.getCountryName();
        }

        catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        return countryName;
    }
}
