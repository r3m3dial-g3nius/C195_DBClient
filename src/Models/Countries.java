package Models;

public class Countries {

    private int countryID;
    private String countryName;

    public Countries(int id, String name)
    {
        this.countryID = id;
        this.countryName = name;
    }

    public int getCountryID()
    {
        return countryID;
    }

    public void setCountryID(int countryID)
    {
        this.countryID = countryID;
    }

    public String getCountryName()
    {
        return countryName;
    }

    public void setCountryName(String countryName)
    {
        this.countryName = countryName;
    }

    public String toString()
    {
        return countryName;
    }
}
