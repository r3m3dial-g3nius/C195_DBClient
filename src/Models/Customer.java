package Models;

public class Customer {

    private int customerID;
    private String customerName;
    private String customerAddress;
    private int divisionID;
    //private int customerCountry;
    private int customerPostalCode;
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
    public Customer(int customerID, String customerName, String customerAddress, int divisionID, int customerPostalCode, String customerPhone)
    {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.divisionID = divisionID;
        this.customerPostalCode = customerPostalCode;
        this.customerPhone = customerPhone;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public int getCustomerPostalCode() {
        return customerPostalCode;
    }

    public void setCustomerPostalCode(int customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

}
