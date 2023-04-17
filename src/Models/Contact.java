package Models;

import javafx.collections.ObservableList;

/**
 * This class manages Contact data
 */
public class Contact {

    private int contactID;
    private String contactName;
    private String contactEmail;

    /**
     * Constructor for new Contact object
     *
     * @param contactID ID number of contact
     * @param contactName name of contact
     * @param contactEmail email of contact
     */
    public Contact(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * Returns ID number of contact
     *
     * @return ID number of contact
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Sets ID number of contact
     *
     * @param contactID contact ID number
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * returns name of contact
     *
     * @return name of contact
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * sets name of contact
     *
     * @param contactName name of contact
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * gets contact email address
     * @return email address of contact
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * sets email address of contact
     *
     * @param contactEmail email address of contact
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
