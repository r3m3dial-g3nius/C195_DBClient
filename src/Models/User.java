package Models;

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
     * Sets user name
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

}
