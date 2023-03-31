package Models;

/**
 * This class manages the Country data
 */
public class Country {

    private int countryID;
    private String countryName;

    /**
     * Constructor for new Country object
     *
     * @param id ID of country (int)
     * @param name Name of country (String)
     */
    public Country(int id, String name)
    {
        this.countryID = id;
        this.countryName = name;
    }

    /**
     * Returns ID of country
     *
     * @return ID of country
     */
    public int getCountryID()
    {
        return countryID;
    }

    /**
     * Sets country ID
     *
     * @param countryID ID of country
     */
    public void setCountryID(int countryID)
    {
        this.countryID = countryID;
    }

    /**
     * Returns country's name
     *
     * @return name of country
     */
    public String getCountryName()
    {
        return countryName;
    }

    /**
     * Sets country's name
     * @param countryName Name of country
     */
    public void setCountryName(String countryName)
    {
        this.countryName = countryName;
    }

    /**
     * Returns the name of country as string for use in GUI
     *
     * @return name of country
     */
    public String toString()
    {
        return countryName;
    }
}
